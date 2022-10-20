package com.eastcom_sw.demo.service;


import com.eastcom_sw.demo.domain.SysTenant;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ISysTenantService {



    /**
     * 查询给用户列表
     * @param sysTenant
     * @return
     */
    List<SysTenant> selectSysTenantList(SysTenant sysTenant);



    /**
     * 检车是当前name是否已经存在
     * @param sysTenant
     * @return
     */
    String checkNameUnique(SysTenant sysTenant);



    /**
     * 插入sysTenant
     * @param sysTenant
     * @return
     */
    int xwtInsertSysTenant(SysTenant sysTenant);


    /**
     * 修改用户信息
     * @param sysTenant
     * @return
     */
    int updateSysTenant(SysTenant sysTenant);


    /**
     * 删除
     * @param sysTenantID
     */
    void deleteSysTenantByIDs(String[] sysTenantID);
}
