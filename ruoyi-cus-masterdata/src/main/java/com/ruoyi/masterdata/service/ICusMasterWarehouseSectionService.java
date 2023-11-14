package com.ruoyi.masterdata.service;

import java.util.List;

import com.ruoyi.masterdata.domain.CusMasterWarehouseSection;

/**
 * 库区信息Service接口
 *
 * @author Shawn
 * @date 2023-11-13
 */
public interface ICusMasterWarehouseSectionService {
    /**
     * 查询库区信息
     *
     * @param warehouseCode 仓库编码
     * @param code   库区信息主键
     * @return 库区信息
     */
    public CusMasterWarehouseSection selectCusMasterWarehouseSectionByCode(String warehouseCode, String code);

    /**
     * 查询库区信息列表
     *
     * @param cusMasterWarehouseSection 库区信息
     * @return 库区信息集合
     */
    public List<CusMasterWarehouseSection> selectCusMasterWarehouseSectionList(CusMasterWarehouseSection cusMasterWarehouseSection);

    /**
     * 新增库区信息
     *
     * @param cusMasterWarehouseSection 库区信息
     * @return 结果
     */
    public int insertCusMasterWarehouseSection(CusMasterWarehouseSection cusMasterWarehouseSection);

    /**
     * 修改库区信息
     *
     * @param cusMasterWarehouseSection 库区信息
     * @return 结果
     */
    public int updateCusMasterWarehouseSection(CusMasterWarehouseSection cusMasterWarehouseSection);

    /**
     * 批量删除库区信息
     *
     * @param codes         需要删除的库区信息主键集合
     * @param warehouseCode 仓库编码
     * @return 结果
     */
    public int deleteCusMasterWarehouseSectionByCodes(String warehouseCode, String[] codes);

    /**
     * 删除库区信息信息
     *
     * @param code 库区信息主键
     *             param warehouseCode 仓库编码
     * @return 结果
     */
    public int deleteCusMasterWarehouseSectionByCode(String warehouseCode, String code);
}
