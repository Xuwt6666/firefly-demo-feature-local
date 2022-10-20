package com.eastcom_sw.demo.mapper;

import com.eastcom_sw.demo.domain.SysTenant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysTenantMapper {

    /**
     * 查询用户人员列表
     * @param sysTenant
     * @return
     */
    public List<SysTenant> selectSysTenantList(SysTenant sysTenant);

    /**
     * 检查系统的用户名是否唯一
     * @param name
     * @return
     */
    SysTenant checkSysTenantNamedUnique(String name);

    /**
     * 插入数据 systenant
     * @param sysTenant
     * @return
     */
    int xwtInsertSysTenant(SysTenant sysTenant);


    /**
     * 更新用户信息
     * @param sysTenant
     * @return
     */
    int updateSysTenant(SysTenant sysTenant);

    /**
     * 根绝 sysTenantID 来查询sysTenant
     * @param sysTenantID
     * @return
     */
    SysTenant selectSysTenantByID(String sysTenantID);

    /**
     * 根绝ID删除
     * @param sysTenantID
     * @return
     */
    public int deleteSysTenantByID(String sysTenantID);
}
