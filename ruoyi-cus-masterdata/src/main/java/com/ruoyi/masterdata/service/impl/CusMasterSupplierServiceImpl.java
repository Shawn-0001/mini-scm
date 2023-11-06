package com.ruoyi.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.masterdata.mapper.CusMasterSupplierMapper;
import com.ruoyi.masterdata.domain.CusMasterSupplier;
import com.ruoyi.masterdata.service.ICusMasterSupplierService;

/**
 * 供应商主数据Service业务层处理
 * 
 * @author shawn
 * @date 2023-11-05
 */
@Service
public class CusMasterSupplierServiceImpl implements ICusMasterSupplierService 
{
    @Autowired
    private CusMasterSupplierMapper cusMasterSupplierMapper;

    /**
     * 查询供应商主数据
     * 
     * @param id 供应商主数据主键
     * @return 供应商主数据
     */
    @Override
    public CusMasterSupplier selectCusMasterSupplierById(Long id)
    {
        return cusMasterSupplierMapper.selectCusMasterSupplierById(id);
    }

    /**
     * 查询供应商主数据
     *
     * @param code 供应商主数据编码
     * @return 供应商主数据
     */
    public CusMasterSupplier selectSupplierMasterByCode(String code){
        return cusMasterSupplierMapper.selectSupplierMasterByCode(code);
    }

    /**
     * 查询供应商主数据列表
     * 
     * @param cusMasterSupplier 供应商主数据
     * @return 供应商主数据
     */
    @Override
    public List<CusMasterSupplier> selectCusMasterSupplierList(CusMasterSupplier cusMasterSupplier)
    {
        return cusMasterSupplierMapper.selectCusMasterSupplierList(cusMasterSupplier);
    }

    /**
     * 新增供应商主数据
     * 
     * @param cusMasterSupplier 供应商主数据
     * @return 结果
     */
    @Override
    public int insertCusMasterSupplier(CusMasterSupplier cusMasterSupplier)
    {
        cusMasterSupplier.setCreateTime(DateUtils.getNowDate());
        return cusMasterSupplierMapper.insertCusMasterSupplier(cusMasterSupplier);
    }

    /**
     * 修改供应商主数据
     * 
     * @param cusMasterSupplier 供应商主数据
     * @return 结果
     */
    @Override
    public int updateCusMasterSupplier(CusMasterSupplier cusMasterSupplier)
    {
        cusMasterSupplier.setUpdateTime(DateUtils.getNowDate());
        return cusMasterSupplierMapper.updateCusMasterSupplier(cusMasterSupplier);
    }

    /**
     * 批量删除供应商主数据
     * 
     * @param ids 需要删除的供应商主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterSupplierByIds(Long[] ids)
    {
        return cusMasterSupplierMapper.deleteCusMasterSupplierByIds(ids);
    }

    /**
     * 删除供应商主数据信息
     * 
     * @param id 供应商主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterSupplierById(Long id)
    {
        return cusMasterSupplierMapper.deleteCusMasterSupplierById(id);
    }
}
