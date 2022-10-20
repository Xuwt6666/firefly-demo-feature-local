package com.eastcom_sw.demo.service;

import java.util.List;

import com.eastcom_sw.demo.domain.DemoConfig;

/**
 * 参数配置 服务层
 *
 */
public interface IDemoConfigService
{
    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    public DemoConfig selectConfigById(String configId);

    /**
     * 根绝configId来查询信息
     * @param configId
     * @return
     */
    public DemoConfig xwtSelectConfigById(String configId);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<DemoConfig> selectConfigList(DemoConfig config);

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(DemoConfig config);

    /**
     * 修改参数配置
     *
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(DemoConfig config);

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    public void deleteConfigByIds(String[] configIds);

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数信息
     * @return 结果
     */
    public String checkConfigKeyUnique(DemoConfig config);

    /**
     * 根绝配置名字进行查询
     * @param configName
     * @return
     */
    public List<DemoConfig> xwtSelectConfigByName(String configName);

    /**
     * 插入新的config数据
     * @param config
     * @return
     */
    public int xwtInsertConfig(DemoConfig config);

    List<DemoConfig> selectPages(int pageNum, int pageSize);

}
