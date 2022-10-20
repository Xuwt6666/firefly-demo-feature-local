package com.eastcom_sw.demo.domain;


import com.eastcom_sw.firefly_cloud.common.core.annotation.Excel;
import com.eastcom_sw.firefly_cloud.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * sys_tenant
 */
@ApiModel("配置参数实体")
public class SysTenant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("参数主键")
    @Excel(name = "参数主键")
    private String tenantID;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @Excel(name = "参数状态", readConverterExp = "1=是, 0=否")
    private String status;


    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tenantId", getTenantId())
                .append("name", getName())
                .append("createTime", getCreateTime())
                .append("creator", getCreator())
                .append("beginTime", getBeginTime())
                .append("endTime", getEndTime())
                .append("status", getStatus())
                .toString();
    }

}
