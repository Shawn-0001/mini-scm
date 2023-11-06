package com.ruoyi.masterdata.mapper;

import java.util.List;
import com.ruoyi.masterdata.domain.CusMasterSupplier;

/**
 * 供应商主数据Mapper接口
 * 
 * @author shawn
 * @date 2023-11-05
 */
public interface CusMasterSupplierMapper 
{
    /**
     * 查询供应商主数据
     * 
     * @param id 供应商主数据主键
     * @return 供应商主数据
     */
    public CusMasterSupplier selectCusMasterSupplierById(Long id);

    /**
     * 查询供应商主数据
     *
     * @param code 供应商主数据编码
     * @return 供应商主数据
     */
    public CusMasterSupplier selectSupplierMasterByCode(String code);
    /**
     * 查询供应商主数据列表
     * 
     * @param cusMasterSupplier 供应商主数据
     * @return 供应商主数据集合
     */
    public List<CusMasterSupplier> selectCusMasterSupplierList(CusMasterSupplier cusMasterSupplier);

    /**
     * 新增供应商主数据
     * 
     * @param cusMasterSupplier 供应商主数据
     * @return 结果
     */
    public int insertCusMasterSupplier(CusMasterSupplier cusMasterSupplier);

    /**
     * 修改供应商主数据
     * 
     * @param cusMasterSupplier 供应商主数据
     * @return 结果
     */
    public int updateCusMasterSupplier(CusMasterSupplier cusMasterSupplier);

    /**
     * 删除供应商主数据
     * 
     * @param id 供应商主数据主键
     * @return 结果
     */
    public int deleteCusMasterSupplierById(Long id);

    /**
     * 批量删除供应商主数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCusMasterSupplierByIds(Long[] ids);
}
