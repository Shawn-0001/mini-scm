package com.ruoyi.masterdata.mapper;

import java.util.List;

import com.ruoyi.masterdata.domain.CusMasterWarehouseSection;
import org.apache.ibatis.annotations.Param;

/**
 * 库区信息Mapper接口
 *
 * @author Shawn
 * @date 2023-11-13
 */
public interface CusMasterWarehouseSectionMapper {
    /**
     * 查询库区信息
     *
     * @param warehouseCode 仓库编码
     * @param code          库区信息主键
     * @return 库区信息
     */
    public CusMasterWarehouseSection selectCusMasterWarehouseSectionByCode(@Param("warehouseCode") String warehouseCode, @Param("code")String code);

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
     * 删除库区信息
     *
     * @param warehouseCode 仓库编码
     * @param code          库区信息主键
     * @return 结果
     */
    public int deleteCusMasterWarehouseSectionByCode(@Param("warehouseCode") String warehouseCode, @Param("code") String code);

    /**
     * 批量删除库区信息
     *
     * @param warehouseCode 仓库编码
     * @param codes         需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCusMasterWarehouseSectionByCodes(@Param("warehouseCode") String warehouseCode, @Param("codes") String[] codes);
}
