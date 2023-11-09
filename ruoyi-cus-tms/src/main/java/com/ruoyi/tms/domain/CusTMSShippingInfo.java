package com.ruoyi.tms.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 运单详细信息对象 cus_tms_shipping_info
 *
 * @author shawn
 * @date 2023-11-06
 */
public class CusTMSShippingInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增序列
     */
    private Long id;

    /**
     * 运单编号
     */
    @Excel(name = "运单编号")
    private String shippingOrder;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String shippingType;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNumber;

    /**
     * 车号
     */
    @Excel(name = "车号")
    private String tractorNumber;

    /**
     * 发货仓库
     */
    @Excel(name = "发货仓库")
    private String shippingFrom;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String shippingFromName;

    /**
     * 卸货仓库
     */
    @Excel(name = "卸货仓库")
    private String shippingTo;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    private String shippingToName;

    /**
     * 重量
     */
    @Excel(name = "重量")
    private BigDecimal weight;

    /**
     * 经办人
     */
    @Excel(name = "经办人")
    private String handledBy;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phone;

    /**
     * 物料编号
     */
    @Excel(name = "物料编号")
    private String materialCode;

    /**
     * 物料名称
     */
    @Excel(name = "物料名称")
    private String materialName;

    /**
     * 业务日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "业务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date businessDate;

    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /**
     * 卸货重量
     */
    @Excel(name = "卸货重量")
    private BigDecimal landingWeight;

    /**
     * 核算重量
     */
    @Excel(name = "核算重量")
    private BigDecimal effectiveWeight;

    /**
     * 核算单价
     */
    @Excel(name = "核算单价")
    private BigDecimal effectiveUnitPrice;

    /**
     * 卸货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "卸货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date landingDate;

    /**
     * 运输单状态
     */
    @Excel(name = "运输单状态", dictType = "cus_tms_status")
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setShippingOrder(String shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    public String getShippingOrder() {
        return shippingOrder;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setTractorNumber(String tractorNumber) {
        this.tractorNumber = tractorNumber;
    }

    public String getTractorNumber() {
        return tractorNumber;
    }

    public void setShippingFrom(String shippingFrom) {
        this.shippingFrom = shippingFrom;
    }

    public String getShippingFrom() {
        return shippingFrom;
    }

    public void setShippingFromName(String shippingFromName) {
        this.shippingFromName = shippingFromName;
    }

    public String getShippingFromName() {
        return shippingFromName;
    }

    public void setShippingTo(String shippingTo) {
        this.shippingTo = shippingTo;
    }

    public String getShippingTo() {
        return shippingTo;
    }

    public void setShippingToName(String shippingToName) {
        this.shippingToName = shippingToName;
    }

    public String getShippingToName() {
        return shippingToName;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setHandledBy(String handledBy) {
        this.handledBy = handledBy;
    }

    public String getHandledBy() {
        return handledBy;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setLandingWeight(BigDecimal landingWeight) {
        this.landingWeight = landingWeight;
    }

    public BigDecimal getLandingWeight() {
        return landingWeight;
    }

    public void setEffectiveWeight(BigDecimal effectiveWeight) {
        this.effectiveWeight = effectiveWeight;
    }

    public BigDecimal getEffectiveWeight() {
        return effectiveWeight;
    }

    public void setEffectiveUnitPrice(BigDecimal effectiveUnitPrice) {
        this.effectiveUnitPrice = effectiveUnitPrice;
    }

    public BigDecimal getEffectiveUnitPrice() {
        return effectiveUnitPrice;
    }

    public void setLandingDate(Date landingDate) {
        this.landingDate = landingDate;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shippingOrder", getShippingOrder())
                .append("shippingType", getShippingType())
                .append("batchNumber", getBatchNumber())
                .append("tractorNumber", getTractorNumber())
                .append("shippingFrom", getShippingFrom())
                .append("shippingFromName", getShippingFromName())
                .append("shippingTo", getShippingTo())
                .append("shippingToName", getShippingToName())
                .append("weight", getWeight())
                .append("handledBy", getHandledBy())
                .append("phone", getPhone())
                .append("materialCode", getMaterialCode())
                .append("materialName",getMaterialName())
                .append("businessDate", getBusinessDate())
                .append("unitPrice", getUnitPrice())
                .append("landingWeight", getLandingWeight())
                .append("effectiveWeight", getEffectiveWeight())
                .append("effectiveUnitPrice", getEffectiveUnitPrice())
                .append("landingDate", getLandingDate())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
