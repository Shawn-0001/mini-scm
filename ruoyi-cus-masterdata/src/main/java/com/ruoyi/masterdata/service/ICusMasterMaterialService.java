package com.ruoyi.masterdata.service;

import java.util.List;
import com.ruoyi.masterdata.domain.CusMasterMaterial;

/**
 * 物料主数据Service接口
 * 
 * @author ruoyi
 * @date 2023-11-02
 */
public interface ICusMasterMaterialService 
{
    /**
     * 查询物料主数据
     * 
     * @param id 物料主数据主键
     * @return 物料主数据
     */
    public CusMasterMaterial selectCusMasterMaterialById(Long id);

    /**
     * 查询物料主数据
     *
     * @param code 物料主数据主键
     * @return 物料主数据
     */
    public CusMasterMaterial selectMaterialMasterByCode(String code);

    /**
     * 查询物料主数据列表
     * 
     * @param cusMasterMaterial 物料主数据
     * @return 物料主数据集合
     */
    public List<CusMasterMaterial> selectCusMasterMaterialList(CusMasterMaterial cusMasterMaterial);

    /**
     * 新增物料主数据
     * 
     * @param cusMasterMaterial 物料主数据
     * @return 结果
     */
    public int insertCusMasterMaterial(CusMasterMaterial cusMasterMaterial);

    /**
     * 修改物料主数据
     * 
     * @param cusMasterMaterial 物料主数据
     * @return 结果
     */
    public int updateCusMasterMaterial(CusMasterMaterial cusMasterMaterial);

    /**
     * 批量删除物料主数据
     * 
     * @param ids 需要删除的物料主数据主键集合
     * @return 结果
     */
    public int deleteCusMasterMaterialByIds(Long[] ids);

    /**
     * 删除物料主数据信息
     * 
     * @param id 物料主数据主键
     * @return 结果
     */
    public int deleteCusMasterMaterialById(Long id);
}
