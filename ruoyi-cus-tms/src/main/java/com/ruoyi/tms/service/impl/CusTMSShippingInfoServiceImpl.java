package com.ruoyi.tms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tms.mapper.CusTMSShippingInfoMapper;
import com.ruoyi.tms.domain.CusTMSShippingInfo;
import com.ruoyi.tms.service.ICusTMSShippingInfoService;

/**
 * 运单详细信息Service业务层处理
 * 
 * @author shawn
 * @date 2023-11-06
 */
@Service
public class CusTMSShippingInfoServiceImpl implements ICusTMSShippingInfoService 
{
    @Autowired
    private CusTMSShippingInfoMapper cusTMSShippingInfoMapper;

    /**
     * 查询运单详细信息
     * 
     * @param id 运单详细信息主键
     * @return 运单详细信息
     */
    @Override
    public CusTMSShippingInfo selectCusTMSShippingInfoById(Long id)
    {
        return cusTMSShippingInfoMapper.selectCusTMSShippingInfoById(id);
    }

    /**
     * 查询运单详细信息列表
     * 
     * @param cusTMSShippingInfo 运单详细信息
     * @return 运单详细信息
     */
    @Override
    public List<CusTMSShippingInfo> selectCusTMSShippingInfoList(CusTMSShippingInfo cusTMSShippingInfo)
    {
        return cusTMSShippingInfoMapper.selectCusTMSShippingInfoList(cusTMSShippingInfo);
    }

    /**
     * 新增运单详细信息
     * 
     * @param cusTMSShippingInfo 运单详细信息
     * @return 结果
     */
    @Override
    public int insertCusTMSShippingInfo(CusTMSShippingInfo cusTMSShippingInfo)
    {
        cusTMSShippingInfo.setCreateTime(DateUtils.getNowDate());
        return cusTMSShippingInfoMapper.insertCusTMSShippingInfo(cusTMSShippingInfo);
    }

    /**
     * 修改运单详细信息
     * 
     * @param cusTMSShippingInfo 运单详细信息
     * @return 结果
     */
    @Override
    public int updateCusTMSShippingInfo(CusTMSShippingInfo cusTMSShippingInfo)
    {
        cusTMSShippingInfo.setUpdateTime(DateUtils.getNowDate());
        return cusTMSShippingInfoMapper.updateCusTMSShippingInfo(cusTMSShippingInfo);
    }

    /**
     * 批量删除运单详细信息
     * 
     * @param ids 需要删除的运单详细信息主键
     * @return 结果
     */
    @Override
    public int deleteCusTMSShippingInfoByIds(Long[] ids)
    {
        return cusTMSShippingInfoMapper.deleteCusTMSShippingInfoByIds(ids);
    }

    /**
     * 删除运单详细信息信息
     * 
     * @param id 运单详细信息主键
     * @return 结果
     */
    @Override
    public int deleteCusTMSShippingInfoById(Long id)
    {
        return cusTMSShippingInfoMapper.deleteCusTMSShippingInfoById(id);
    }
}
