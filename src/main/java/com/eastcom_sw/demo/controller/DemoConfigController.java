package com.eastcom_sw.demo.controller;

import java.util.List;
import java.util.zip.DeflaterOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.eastcom_sw.demo.domain.DemoConfig;
import com.eastcom_sw.demo.service.IDemoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eastcom_sw.firefly_cloud.common.core.constant.UserConstants;
import com.eastcom_sw.firefly_cloud.common.core.utils.poi.ExcelUtil;
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

/**
 * 参数配置 信息操作处理
 *
 */
@Api("参数配置管理")
@RestController
@RequestMapping("/demo/config")
public class DemoConfigController extends BaseController
{
    private static final Logger logger = LoggerFactory.getLogger(DemoConfigController.class);

    @Autowired
    private IDemoConfigService configService;


    /**
     * 获取参数配置列表
     */
    //@RequiresPermissions("demo:config:list")
    @Log(title = "参数列表查询", businessType = BusinessType.QUERY,isSaveResponseData=false)
    @GetMapping("/list")
    public TableDataInfo list(DemoConfig config)
    {
        logger.info("参数列表查询");
        startPage();
        List<DemoConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("demo:config:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DemoConfig config)
    {
        List<DemoConfig> list = configService.selectConfigList(config);
        ExcelUtil<DemoConfig> util = new ExcelUtil<DemoConfig>(DemoConfig.class);
        util.exportExcel(response, list, "参数数据");
    }

    /**
     * xwt 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Log(title = "分页查询", businessType = BusinessType.QUERY, isSaveResponseData = false)
    @GetMapping("/xwt/selectPages")
    public TableDataInfo selectPages(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize){
        logger.info("分页查询");
        startPage(); //不懂什么意思
        List<DemoConfig> list = configService.selectPages(pageNum, pageSize);
        return getDataTable(list);
    }

    /**
     * 根据参数编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable String configId)
    {
        return AjaxResult.success(configService.selectConfigById(configId));
    }


    /**
     * xwt添加的功能，根据configId查询
     * @param configId
     * @return
     */
    @GetMapping(value = "/xwtById/{configId}")
    public DemoConfig xwtGetInfoById(@PathVariable String configId)
    {
        DemoConfig demoConfig = configService.xwtSelectConfigById(configId);
        return demoConfig;
    }


    /**
     * xwt添加的功能，根据configId查询
     * @param configName
     * @return
     */
    @GetMapping(value = "/xwtByName/{configName}")
    public List<DemoConfig> xwtGetInfoByName(@PathVariable String configName)
    {
        List<DemoConfig> list = configService.xwtSelectConfigByName(configName);
        return list;
    }


    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable String configKey)
    {
        return AjaxResult.success(configService.selectConfigByKey(configKey));
    }


    /**
     * 新增参数配置
     */
    @RequiresPermissions("demo:config:add")
    @ApiOperation("参数配置新增")
    @ApiImplicitParam(name = "config", value = "配置信息", dataType = "DemoConfig")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DemoConfig config)
    {
        logger.info("参数配置新增");

        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(SecurityUtils.getUsername());
        return toAjax(configService.insertConfig(config));
    }


    @ApiOperation("xwt版 新增")
    @RequiresPermissions("demo:config:xwtAdd")
    @ApiImplicitParam(name = "config", value = "配置信息", dataType = "DemoConfig")
    @Log(title = "xwt参数管理", businessType = BusinessType.INSERT)
    @PostMapping("/xwtAdd")
    public AjaxResult xwtAdd(@Validated @RequestBody DemoConfig config)
    {
        logger.info("xwt版本 新增");
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("该数据参数键名已经存在！");
        }
        //设置 该config的创建者名字
        config.setCreateBy(SecurityUtils.getUsername());
        return toAjax(configService.xwtInsertConfig(config));
    }


    /**
     * 修改参数配置
     */
    @RequiresPermissions("demo:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DemoConfig config)
    {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }

        return toAjax(configService.updateConfig(config));
    }


    /**
     * 删除参数配置
     */
    @RequiresPermissions("demo:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable String[] configIds)
    {
        configService.deleteConfigByIds(configIds);
        return success();
    }
    

    /**
     * 刷新参数缓存
     */
    //@RequiresPermissions("demo:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        configService.resetConfigCache();
        return AjaxResult.success();
    }

}
