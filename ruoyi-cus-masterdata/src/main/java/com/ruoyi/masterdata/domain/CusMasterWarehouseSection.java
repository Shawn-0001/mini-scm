package com.ruoyi.masterdata.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库区信息对象 cus_master_warehouse_section
 * 
 * @author Shawn
 * @date 2023-11-13
 */
public class CusMasterWarehouseSection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增序列 */
    private Long id;

    /** 库区编码 */
    @Excel(name = "库区编码")
    private String code;

    /** 库区名称 */
    @Excel(name = "库区名称")
    private String name;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 库区地址 */
    @Excel(name = "库区地址")
    private String location;

    /** 管理人员 */
    @Excel(name = "管理人员")
    private String contactPic;

    /** 联系方式1 */
    @Excel(name = "联系方式1")
    private String contactPhone1;

    /** 联系方式2 */
    @Excel(name = "联系方式2")
    private String contactPhone2;

    /** 库区类别 */
    @Excel(name = "库区类别")
    private String category;

    /** 占地面积 */
    @Excel(name = "占地面积")
    private String area;

    /** 最大容量 */
    @Excel(name = "最大容量")
    private String volume;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 备注 */
    @Excel(name = "备注")
    private String comments;

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
    public void setWarehouseCode(String warehouseCode) 
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() 
    {
        return warehouseCode;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setContactPic(String contactPic) 
    {
        this.contactPic = contactPic;
    }

    public String getContactPic() 
    {
        return contactPic;
    }
    public void setContactPhone1(String contactPhone1) 
    {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactPhone1() 
    {
        return contactPhone1;
    }
    public void setContactPhone2(String contactPhone2) 
    {
        this.contactPhone2 = contactPhone2;
    }

    public String getContactPhone2() 
    {
        return contactPhone2;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setVolume(String volume) 
    {
        this.volume = volume;
    }

    public String getVolume() 
    {
        return volume;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setComments(String comments) 
    {
        this.comments = comments;
    }

    public String getComments() 
    {
        return comments;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("warehouseCode", getWarehouseCode())
            .append("location", getLocation())
            .append("contactPic", getContactPic())
            .append("contactPhone1", getContactPhone1())
            .append("contactPhone2", getContactPhone2())
            .append("category", getCategory())
            .append("area", getArea())
            .append("volume", getVolume())
            .append("unit", getUnit())
            .append("comments", getComments())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
