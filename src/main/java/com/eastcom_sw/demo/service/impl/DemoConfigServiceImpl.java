package com.eastcom_sw.demo.service.impl;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eastcom_sw.demo.domain.DemoConfig;
import com.eastcom_sw.demo.mapper.DemoConfigMapper;
import com.eastcom_sw.demo.service.IDemoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eastcom_sw.firefly_cloud.common.core.constant.Constants;
import com.eastcom_sw.firefly_cloud.common.core.constant.UserConstants;
import com.eastcom_sw.firefly_cloud.common.core.exception.ServiceException;
import com.eastcom_sw.firefly_cloud.common.core.text.Convert;
import com.eastcom_sw.firefly_cloud.common.core.utils.StringUtils;
import com.eastcom_sw.firefly_cloud.common.redis.service.RedisService;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.date.DateUtil;

/**
 * 参数配置 服务层实现
 *
 */
@Service
public class DemoConfigServiceImpl implements IDemoConfigService
{
    private static final Logger log = LoggerFactory.getLogger(DemoConfigServiceImpl.class);

    @Autowired
    private DemoConfigMapper configMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init()
    {
        log.info("初始化参数到缓存");
        loadingConfigCache();
    }

    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public DemoConfig selectConfigById(String configId)
    {
        DemoConfig config = new DemoConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根绝configId来查询配置信息
     * @param configId
     * @return
     */
    @Override
    public DemoConfig xwtSelectConfigById(String configId)
    {
        DemoConfig demoConfig = configMapper.xwtSelectConfigById(configId);
        if (demoConfig!= null){
            return demoConfig;
        }else {
            return null;
        }
    }

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        //从redis中查询
        String configValue = Convert.toStr(redisService.getCacheObject(getCacheKey(configKey)));
        //如果redis中 有这个数据
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        //new 一个 config，设置当前的configkey
        DemoConfig config = new DemoConfig();
        config.setConfigKey(configKey);
        //查询数据库中当前的config
        DemoConfig retConfig = configMapper.selectConfig(config);
        //如果有，就存到redis中，然后返回
        if (StringUtils.isNotNull(retConfig))
        {
            redisService.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        //否则返回空值
        return StringUtils.EMPTY;
    }

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<DemoConfig> selectConfigList(DemoConfig config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(DemoConfig config)
    {     
        //MongoDB数据ID算法生成的id,适合id为字符串（24位长度）
        config.setConfigId(IdUtil.objectId());  

        //获取系统当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        config.setCreateTime(DateUtil.now());


        int row = configMapper.insertConfig(config);
        if (row > 0)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(DemoConfig config)
    {
        int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    @Override
    public void deleteConfigByIds(String[] configIds)
    {
        for (String configId : configIds)
        {
            DemoConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            redisService.deleteObject(getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache()
    {
        List<DemoConfig> configsList = configMapper.selectConfigList(new DemoConfig());

        for (DemoConfig config : configsList)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache()
    {
        Collection<String> keys = redisService.keys(Constants.SYS_CONFIG_KEY + "*");
        redisService.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache()
    {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(DemoConfig config)
    {
        // 如果当前configId是空，就设置为0并获取，否则直接获取
        String configId = StringUtils.isNull(config.getConfigId()) ? "0" : config.getConfigId();
        //检查当前
        DemoConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && !info.getConfigId().equals(configId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 根据配置的名字进行查询
     * @param configName
     * @return
     */
    @Override
    public List<DemoConfig> xwtSelectConfigByName(String configName) {
        List<DemoConfig> list = configMapper.xwtSelectConfigByName(configName);
        if (StringUtils.isNotEmpty(list))
        {
            return list;
        }else {
            return null;
        }
    }


    /**
     * xwt 插入新的数据
     * @param config
     * @return
     */
    @Override
    public int xwtInsertConfig(DemoConfig config)
    {
        //MongoDB数据ID算法生成的id,适合id为字符串（24位长度）
        config.setConfigId(IdUtil.objectId());

        //获取系统当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        config.setCreateTime(DateUtil.now());

        int row = configMapper.xwtInsertConfig(config);
        // 如果插入成功，就存到redis中
        if (row > 0)
        {
            redisService.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<DemoConfig> selectPages(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<DemoConfig> list = configMapper.xwtSelectAll();
        PageInfo<DemoConfig> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }


    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
