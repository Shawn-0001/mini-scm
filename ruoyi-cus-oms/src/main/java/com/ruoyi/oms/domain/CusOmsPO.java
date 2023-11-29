package com.ruoyi.oms.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购订单对象 cus_oms_po
 *
 * @author Shawn
 * @date 2023-11-14
 */
public class CusOmsPO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 自增序列
     */
    private Long id;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderNumber;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private String status;

    /**
     * 订单类型
     */
    @Excel(name = "订单类型")
    private String type;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String contractId;

    /**
     * 经办人
     */
    @Excel(name = "经办人")
    private String handledBy;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private String department;

    /**
     * 业务日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "业务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date businessDate;

    /**
     * 物料编码
     */
    @Excel(name = "物料编码")
    private String materialCode;

    @Excel(name = "计量单位", dictType = "cus_material_unit")
    private String unit;

    /**
     * 采购数量
     */
    @Excel(name = "采购数量")
    private BigDecimal quantity;

    /**
     * 采购单价
     */
    @Excel(name = "采购单价")
    private BigDecimal unitPrice;

    /**
     * 供应商代码
     */
    @Excel(name = "供应商代码")
    private String supplierCode;

    /**
     * 预计到货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setHandledBy(String handledBy) {
        this.handledBy = handledBy;
    }

    public String getHandledBy() {
        return handledBy;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setmaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getmaterialCode() {
        return materialCode;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderNumber", getOrderNumber())
                .append("status", getStatus())
                .append("type", getType())
                .append("contractId", getContractId())
                .append("handledBy", getHandledBy())
                .append("department", getDepartment())
                .append("businessDate", getBusinessDate())
                .append("materialCode", getmaterialCode())
                .append("unit",getUnit())
                .append("quantity", getQuantity())
                .append("unitPrice", getUnitPrice())
                .append("supplierCode", getSupplierCode())
                .append("deliveryDate", getDeliveryDate())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
