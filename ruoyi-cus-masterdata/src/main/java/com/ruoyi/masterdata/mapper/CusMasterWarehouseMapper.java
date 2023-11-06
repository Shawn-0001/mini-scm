package com.ruoyi.masterdata.mapper;

import java.util.List;

import com.ruoyi.masterdata.domain.CusMasterSupplier;
import com.ruoyi.masterdata.domain.CusMasterWarehouse;

/**
 * 仓库主数据Mapper接口
 * 
 * @author shawn
 * @date 2023-11-05
 */
public interface CusMasterWarehouseMapper 
{
    /**
     * 查询仓库主数据
     * 
     * @param id 仓库主数据主键
     * @return 仓库主数据
     */
    public CusMasterWarehouse selectCusMasterWarehouseById(String id);

    /**
     * 查询仓库主数据
     *
     * @param code 仓库主数据编码
     * @return 仓库主数据
     */
    public CusMasterWarehouse selectWarehouseMasterByCode(String code);

    /**
     * 查询仓库主数据列表
     * 
     * @param cusMasterWarehouse 仓库主数据
     * @return 仓库主数据集合
     */
    public List<CusMasterWarehouse> selectCusMasterWarehouseList(CusMasterWarehouse cusMasterWarehouse);

    /**
     * 新增仓库主数据
     * 
     * @param cusMasterWarehouse 仓库主数据
     * @return 结果
     */
    public int insertCusMasterWarehouse(CusMasterWarehouse cusMasterWarehouse);

    /**
     * 修改仓库主数据
     * 
     * @param cusMasterWarehouse 仓库主数据
     * @return 结果
     */
    public int updateCusMasterWarehouse(CusMasterWarehouse cusMasterWarehouse);

    /**
     * 删除仓库主数据
     * 
     * @param id 仓库主数据主键
     * @return 结果
     */
    public int deleteCusMasterWarehouseById(String id);

    /**
     * 批量删除仓库主数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCusMasterWarehouseByIds(String[] ids);
}
