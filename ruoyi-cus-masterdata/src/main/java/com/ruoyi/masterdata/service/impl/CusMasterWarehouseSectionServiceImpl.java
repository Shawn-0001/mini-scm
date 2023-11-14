package com.ruoyi.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.masterdata.mapper.CusMasterWarehouseSectionMapper;
import com.ruoyi.masterdata.domain.CusMasterWarehouseSection;
import com.ruoyi.masterdata.service.ICusMasterWarehouseSectionService;

/**
 * 库区信息Service业务层处理
 * 
 * @author Shawn
 * @date 2023-11-13
 */
@Service
public class CusMasterWarehouseSectionServiceImpl implements ICusMasterWarehouseSectionService 
{
    @Autowired
    private CusMasterWarehouseSectionMapper cusMasterWarehouseSectionMapper;

    /**
     * 查询库区信息
     * 
     * @param code 库区编码
     * @param warehouseCode 仓库编码
     * @return 库区信息
     */
    @Override
    public CusMasterWarehouseSection selectCusMasterWarehouseSectionByCode(String warehouseCode, String code)
    {
        return cusMasterWarehouseSectionMapper.selectCusMasterWarehouseSectionByCode(warehouseCode, code);
    }

    /**
     * 查询库区信息列表
     * 
     * @param cusMasterWarehouseSection 库区信息
     * @return 库区信息
     */
    @Override
    public List<CusMasterWarehouseSection> selectCusMasterWarehouseSectionList(CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        return cusMasterWarehouseSectionMapper.selectCusMasterWarehouseSectionList(cusMasterWarehouseSection);
    }

    /**
     * 新增库区信息
     * 
     * @param cusMasterWarehouseSection 库区信息
     * @return 结果
     */
    @Override
    public int insertCusMasterWarehouseSection(CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        cusMasterWarehouseSection.setCreateTime(DateUtils.getNowDate());
        return cusMasterWarehouseSectionMapper.insertCusMasterWarehouseSection(cusMasterWarehouseSection);
    }

    /**
     * 修改库区信息
     * 
     * @param cusMasterWarehouseSection 库区信息
     * @return 结果
     */
    @Override
    public int updateCusMasterWarehouseSection(CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        cusMasterWarehouseSection.setUpdateTime(DateUtils.getNowDate());
        return cusMasterWarehouseSectionMapper.updateCusMasterWarehouseSection(cusMasterWarehouseSection);
    }

    /**
     * 批量删除库区信息
     * 
     * @param codes 需要删除的库区信息主键
     * @param warehouseCode 仓库编码
     * @return 结果
     */
    @Override
    public int deleteCusMasterWarehouseSectionByCodes(String warehouseCode, String[] codes)
    {
        return cusMasterWarehouseSectionMapper.deleteCusMasterWarehouseSectionByCodes(warehouseCode, codes);
    }

    /**
     * 删除库区信息信息
     * 
     * @param code 库区信息主键
     * @param warehouseCode 仓库编码
     * @return 结果
     */
    @Override
    public int deleteCusMasterWarehouseSectionByCode(String warehouseCode, String code)
    {
        return cusMasterWarehouseSectionMapper.deleteCusMasterWarehouseSectionByCode(warehouseCode, code);
    }
}
