package com.ruoyi.tms.service;

import java.util.List;
import com.ruoyi.tms.domain.CusTMSShippingInfo;

/**
 * 运单详细信息Service接口
 * 
 * @author shawn
 * @date 2023-11-06
 */
public interface ICusTMSShippingInfoService 
{
    /**
     * 查询运单详细信息
     * 
     * @param id 运单详细信息主键
     * @return 运单详细信息
     */
    public CusTMSShippingInfo selectCusTMSShippingInfoById(Long id);

    /**
     * 查询运单详细信息列表
     * 
     * @param cusTMSShippingInfo 运单详细信息
     * @return 运单详细信息集合
     */
    public List<CusTMSShippingInfo> selectCusTMSShippingInfoList(CusTMSShippingInfo cusTMSShippingInfo);

    /**
     * 新增运单详细信息
     * 
     * @param cusTMSShippingInfo 运单详细信息
     * @return 结果
     */
    public int insertCusTMSShippingInfo(CusTMSShippingInfo cusTMSShippingInfo);

    /**
     * 修改运单详细信息
     * 
     * @param cusTMSShippingInfo 运单详细信息
     * @return 结果
     */
    public int updateCusTMSShippingInfo(CusTMSShippingInfo cusTMSShippingInfo);

    /**
     * 批量删除运单详细信息
     * 
     * @param ids 需要删除的运单详细信息主键集合
     * @return 结果
     */
    public int deleteCusTMSShippingInfoByIds(Long[] ids);

    /**
     * 删除运单详细信息信息
     * 
     * @param id 运单详细信息主键
     * @return 结果
     */
    public int deleteCusTMSShippingInfoById(Long id);
}
