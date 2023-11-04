package com.ruoyi.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.masterdata.mapper.CusMasterMaterialMapper;
import com.ruoyi.masterdata.domain.CusMasterMaterial;
import com.ruoyi.masterdata.service.ICusMasterMaterialService;

/**
 * 物料主数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-02
 */
@Service
public class CusMasterMaterialServiceImpl implements ICusMasterMaterialService 
{
    @Autowired
    private CusMasterMaterialMapper cusMasterMaterialMapper;

    /**
     * 查询物料主数据
     *
     * @param code 物料主数据主键
     * @return 物料主数据
     */
    @Override
    public CusMasterMaterial selectMaterialMasterByCode(String code)
    {
        return cusMasterMaterialMapper.selectMaterialMasterByCode(code);
    }

    /**
     * 查询物料主数据
     * 
     * @param id 物料主数据主键
     * @return 物料主数据
     */
    @Override
    public CusMasterMaterial selectCusMasterMaterialById(Long id)
    {
        return cusMasterMaterialMapper.selectCusMasterMaterialById(id);
    }

    /**
     * 查询物料主数据列表
     * 
     * @param cusMasterMaterial 物料主数据
     * @return 物料主数据
     */
    @Override
    public List<CusMasterMaterial> selectCusMasterMaterialList(CusMasterMaterial cusMasterMaterial)
    {
        return cusMasterMaterialMapper.selectCusMasterMaterialList(cusMasterMaterial);
    }

    /**
     * 新增物料主数据
     * 
     * @param cusMasterMaterial 物料主数据
     * @return 结果
     */
    @Override
    public int insertCusMasterMaterial(CusMasterMaterial cusMasterMaterial)
    {
        cusMasterMaterial.setCreateTime(DateUtils.getNowDate());
        return cusMasterMaterialMapper.insertCusMasterMaterial(cusMasterMaterial);
    }

    /**
     * 修改物料主数据
     * 
     * @param cusMasterMaterial 物料主数据
     * @return 结果
     */
    @Override
    public int updateCusMasterMaterial(CusMasterMaterial cusMasterMaterial)
    {
        cusMasterMaterial.setUpdateTime(DateUtils.getNowDate());
        return cusMasterMaterialMapper.updateCusMasterMaterial(cusMasterMaterial);
    }

    /**
     * 批量删除物料主数据
     * 
     * @param ids 需要删除的物料主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterMaterialByIds(Long[] ids)
    {
        return cusMasterMaterialMapper.deleteCusMasterMaterialByIds(ids);
    }

    /**
     * 删除物料主数据信息
     * 
     * @param id 物料主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterMaterialById(Long id)
    {
        return cusMasterMaterialMapper.deleteCusMasterMaterialById(id);
    }
}
