package com.eastcom_sw.demo.controller;


import com.eastcom_sw.demo.domain.DemoConfig;
import com.eastcom_sw.demo.domain.SysTenant;
import com.eastcom_sw.demo.service.ISysTenantService;
import com.eastcom_sw.demo.service.impl.SysTenantServiceImpl;
import com.eastcom_sw.firefly_cloud.common.core.constant.UserConstants;
import com.eastcom_sw.firefly_cloud.common.core.web.controller.BaseController;
import com.eastcom_sw.firefly_cloud.common.core.web.domain.AjaxResult;
import com.eastcom_sw.firefly_cloud.common.core.web.page.TableDataInfo;
import com.eastcom_sw.firefly_cloud.common.log.annotation.Log;
import com.eastcom_sw.firefly_cloud.common.log.enums.BusinessType;
import com.eastcom_sw.firefly_cloud.common.security.annotation.RequiresPermissions;
import com.eastcom_sw.firefly_cloud.common.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("系统用户管理")
@RestController
@RequestMapping("/sys/tenant")
public class SysTenantController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysTenantController.class);

    @Autowired
    private ISysTenantService sysTenantService;

    /**
     * 系统用户列表查询
     * @param sysTenant
     * @return
     */
    @Log(title = "系统用户列表查询", businessType = BusinessType.QUERY, isSaveResponseData = false)
    @GetMapping("/list")
    public TableDataInfo list(SysTenant sysTenant)
    {
        logger.info("系统用户查询");
        startPage();
        List<SysTenant> list = sysTenantService.selectSysTenantList(sysTenant);
        return getDataTable(list);
    }


    @ApiOperation("xwt版 新增用户")
    @RequiresPermissions("sys:tenant:xwtAdd")
    @ApiImplicitParam(name = "sysTenant", value = "系统用户", dataType = "SysTenant")
    @Log(title = "xwt系统用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult xwtAdd(@Validated @RequestBody SysTenant sysTenant)
    {
        logger.info("xwt 新增");
        logger.info("sysTenant ={}", sysTenant);
        return toAjax(sysTenantService.xwtInsertSysTenant(sysTenant));
    }

    /**
     * 修改信息
     * @param sysTenant
     * @return
     */
    @PutMapping
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated @RequestBody SysTenant sysTenant)
    {
        return toAjax(sysTenantService.updateSysTenant(sysTenant));

    }

    /**
     * 删除
     * @param sysTenantIDs
     * @return
     */
    @RequiresPermissions("sys:tenant:remove")
    @Log(title="系统用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sysTenantIDs}")
    public AjaxResult remove(@PathVariable String[] sysTenantIDs)
    {
        logger.info("调用SysTenantController.remove");
        sysTenantService.deleteSysTenantByIDs(sysTenantIDs);
        return success();
    }




}
