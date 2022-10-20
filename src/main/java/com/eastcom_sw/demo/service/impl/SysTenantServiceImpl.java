package com.eastcom_sw.demo.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.eastcom_sw.demo.domain.SysTenant;
import com.eastcom_sw.demo.mapper.SysTenantMapper;
import com.eastcom_sw.demo.service.ISysTenantService;
import com.eastcom_sw.firefly_cloud.common.core.constant.UserConstants;
import com.eastcom_sw.firefly_cloud.common.core.utils.IdUtils;
import com.eastcom_sw.firefly_cloud.common.core.utils.StringUtils;
import com.eastcom_sw.firefly_cloud.common.redis.service.RedisService;
import com.eastcom_sw.firefly_cloud.common.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SysTenantServiceImpl implements ISysTenantService {

    private static final Logger log = LoggerFactory.getLogger(SysTenantServiceImpl.class);

    @Autowired
    private SysTenantMapper sysTenantMapper;

    @Autowired
    private RedisService redisService;


    /**
     * 查询系统用户列表
     * @param sysTenant
     * @return
     */
    @Override
    public List<SysTenant> selectSysTenantList(SysTenant sysTenant) {
        return sysTenantMapper.selectSysTenantList(sysTenant);
    }


    /**
     * 检查用户名是否已经存在
     * @param sysTenant
     * @return
     */
    @Override
    public String checkNameUnique(SysTenant sysTenant) {

        String sysTenantId = StringUtils.isNull(sysTenant.getTenantId())? "0": sysTenant.getTenantId();

        SysTenant info = sysTenantMapper.checkSysTenantNamedUnique(sysTenant.getName());

        if (StringUtils.isNotNull(info) && !info.getTenantId().equals(sysTenantId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 插入新的systenant
     * @param sysTenant
     * @return
     */
    @Override
    public int xwtInsertSysTenant(SysTenant sysTenant) {

        //MongoDB数据ID算法成id
        sysTenant.setTenantId(IdUtil.objectId());

        //MongoDB数据ID算法成id
        sysTenant.setTenantID(IdUtil.objectId());

        //获取当前的时间字符串，格式为yyyy-MM-dd HH:mm::ss
        sysTenant.setCreateTime(DateUtil.now());

        // 设置 该sysTenant的创建者名字
        sysTenant.setCreator(SecurityUtils.getUsername());

        log.info("sysTenant = {}", sysTenant);

        int row = sysTenantMapper.xwtInsertSysTenant(sysTenant);

        return row;
    }


    /**
     * 更新用户信息
     * @param sysTenant
     * @return
     */
    @Override
    public int updateSysTenant(SysTenant sysTenant) {
        return sysTenantMapper.updateSysTenant(sysTenant);
    }


    /**
     * 删除sysTenant[]
     * @param sysTenantIDs
     */
    @Override
    public void deleteSysTenantByIDs(String[] sysTenantIDs) {
        log.info("调用SysTenantServiceImpl.deleteSysTenantByIDs");
        for (String sysTenantID : sysTenantIDs)
        {
            log.info("当前sysTenantID为{}", sysTenantID);
            SysTenant sysTenant = selectSysTenantByID(sysTenantID);

            if (StringUtils.equals("1", sysTenant.getStatus()))
            {
                throw new SecurityException(String.format("状态为1，不可删除"));
            }
            log.info("调用sysTenantMapper.deleteSysTenantByID");
            sysTenantMapper.deleteSysTenantByID(sysTenantID);
        }
    }

    /**
     * 根绝id 来查询 返回 sysTenant
     * @param sysTenantID
     * @return
     */
    private SysTenant selectSysTenantByID(String sysTenantID)
    {
        log.info("调用 SysTenantServiceImpl.selectSysTenantByID");
        SysTenant sysTenant = sysTenantMapper.selectSysTenantByID(sysTenantID);
        if (sysTenant != null){
            return sysTenant;
        }else {
            return null;
        }
    }
}
