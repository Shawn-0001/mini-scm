package com.ruoyi.masterdata.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商主数据对象 cus_master_supplier
 * 
 * @author shawn
 * @date 2023-11-05
 */
public class CusMasterSupplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增序列 */
    private Long id;

    /** 供应商编号 */
    @Excel(name = "供应商编号")
    private String code;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String name;

    /** 注册城市 */
    @Excel(name = "注册城市")
    private String registerLocation;

    /** 地址 */
    @Excel(name = "地址")
    private String location;

    /** 邮编 */
    @Excel(name = "邮编")
    private String postCode;

    /** 企业法人 */
    @Excel(name = "企业法人")
    private String legalPerson;

    /** 注册资金 */
    @Excel(name = "注册资金")
    private BigDecimal registeredCapital;

    /** 传真号码 */
    @Excel(name = "传真号码")
    private String fax;

    /** 公司网址 */
    @Excel(name = "公司网址")
    private String website;

    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    private String contactsName;

    /** 联系人手机 */
    @Excel(name = "联系人手机")
    private String contactsPhone;

    /** 联系人电子邮箱 */
    @Excel(name = "联系人电子邮箱")
    private String contactsEmail;

    /** 办公地点 */
    @Excel(name = "办公地点")
    private String contactsOfficeLocation;

    /** 开户行 */
    @Excel(name = "开户行")
    private String bankName;

    /** 账号 */
    @Excel(name = "账号")
    private String bankAccount;

    /** 税号 */
    @Excel(name = "税号")
    private String taxNumber;

    /** 发票地址 */
    @Excel(name = "发票地址")
    private String invoiceAddress;

    /** 发票类型 */
    @Excel(name = "发票类型", dictType = "cus_masterdata_invoice_type")
    private String invoiceType;

    /** 收件人姓名 */
    @Excel(name = "收件人姓名")
    private String recipientName;

    /** 收件人电话 */
    @Excel(name = "收件人电话")
    private String recipientPhone;

    /** 收件人地址 */
    @Excel(name = "收件人地址")
    private String recipientAddress;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRegisterLocation(String registerLocation) 
    {
        this.registerLocation = registerLocation;
    }

    public String getRegisterLocation() 
    {
        return registerLocation;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setPostCode(String postCode) 
    {
        this.postCode = postCode;
    }

    public String getPostCode() 
    {
        return postCode;
    }
    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
    }
    public void setRegisteredCapital(BigDecimal registeredCapital) 
    {
        this.registeredCapital = registeredCapital;
    }

    public BigDecimal getRegisteredCapital() 
    {
        return registeredCapital;
    }
    public void setFax(String fax) 
    {
        this.fax = fax;
    }

    public String getFax() 
    {
        return fax;
    }
    public void setWebsite(String website) 
    {
        this.website = website;
    }

    public String getWebsite() 
    {
        return website;
    }
    public void setContactsName(String contactsName) 
    {
        this.contactsName = contactsName;
    }

    public String getContactsName() 
    {
        return contactsName;
    }
    public void setContactsPhone(String contactsPhone) 
    {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsPhone() 
    {
        return contactsPhone;
    }
    public void setContactsEmail(String contactsEmail) 
    {
        this.contactsEmail = contactsEmail;
    }

    public String getContactsEmail() 
    {
        return contactsEmail;
    }
    public void setContactsOfficeLocation(String contactsOfficeLocation) 
    {
        this.contactsOfficeLocation = contactsOfficeLocation;
    }

    public String getContactsOfficeLocation() 
    {
        return contactsOfficeLocation;
    }
    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }
    public void setBankAccount(String bankAccount) 
    {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() 
    {
        return bankAccount;
    }
    public void setTaxNumber(String taxNumber) 
    {
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber() 
    {
        return taxNumber;
    }
    public void setInvoiceAddress(String invoiceAddress) 
    {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceAddress() 
    {
        return invoiceAddress;
    }
    public void setInvoiceType(String invoiceType) 
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType() 
    {
        return invoiceType;
    }
    public void setRecipientName(String recipientName) 
    {
        this.recipientName = recipientName;
    }

    public String getRecipientName() 
    {
        return recipientName;
    }
    public void setRecipientPhone(String recipientPhone) 
    {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientPhone() 
    {
        return recipientPhone;
    }
    public void setRecipientAddress(String recipientAddress) 
    {
        this.recipientAddress = recipientAddress;
    }

    public String getRecipientAddress() 
    {
        return recipientAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("registerLocation", getRegisterLocation())
            .append("location", getLocation())
            .append("postCode", getPostCode())
            .append("legalPerson", getLegalPerson())
            .append("registeredCapital", getRegisteredCapital())
            .append("fax", getFax())
            .append("website", getWebsite())
            .append("contactsName", getContactsName())
            .append("contactsPhone", getContactsPhone())
            .append("contactsEmail", getContactsEmail())
            .append("contactsOfficeLocation", getContactsOfficeLocation())
            .append("bankName", getBankName())
            .append("bankAccount", getBankAccount())
            .append("taxNumber", getTaxNumber())
            .append("invoiceAddress", getInvoiceAddress())
            .append("invoiceType", getInvoiceType())
            .append("recipientName", getRecipientName())
            .append("recipientPhone", getRecipientPhone())
            .append("recipientAddress", getRecipientAddress())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
