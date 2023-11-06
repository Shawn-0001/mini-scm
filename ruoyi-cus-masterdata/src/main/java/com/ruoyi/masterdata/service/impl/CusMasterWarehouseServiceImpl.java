package com.ruoyi.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.masterdata.domain.CusMasterSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.masterdata.mapper.CusMasterWarehouseMapper;
import com.ruoyi.masterdata.domain.CusMasterWarehouse;
import com.ruoyi.masterdata.service.ICusMasterWarehouseService;

/**
 * 仓库主数据Service业务层处理
 * 
 * @author shawn
 * @date 2023-11-05
 */
@Service
public class CusMasterWarehouseServiceImpl implements ICusMasterWarehouseService 
{
    @Autowired
    private CusMasterWarehouseMapper cusMasterWarehouseMapper;

    /**
     * 查询仓库主数据
     * 
     * @param id 仓库主数据主键
     * @return 仓库主数据
     */
    @Override
    public CusMasterWarehouse selectCusMasterWarehouseById(String id)
    {
        return cusMasterWarehouseMapper.selectCusMasterWarehouseById(id);
    }

    /**
     * 查询仓库主数据
     *
     * @param code 仓库主数据编码
     * @return 仓库主数据
     */
    public CusMasterWarehouse selectWarehouseMasterByCode(String code){
        return cusMasterWarehouseMapper.selectWarehouseMasterByCode(code);
    }

    /**
     * 查询仓库主数据列表
     * 
     * @param cusMasterWarehouse 仓库主数据
     * @return 仓库主数据
     */
    @Override
    public List<CusMasterWarehouse> selectCusMasterWarehouseList(CusMasterWarehouse cusMasterWarehouse)
    {
        return cusMasterWarehouseMapper.selectCusMasterWarehouseList(cusMasterWarehouse);
    }

    /**
     * 新增仓库主数据
     * 
     * @param cusMasterWarehouse 仓库主数据
     * @return 结果
     */
    @Override
    public int insertCusMasterWarehouse(CusMasterWarehouse cusMasterWarehouse)
    {
        cusMasterWarehouse.setCreateTime(DateUtils.getNowDate());
        return cusMasterWarehouseMapper.insertCusMasterWarehouse(cusMasterWarehouse);
    }

    /**
     * 修改仓库主数据
     * 
     * @param cusMasterWarehouse 仓库主数据
     * @return 结果
     */
    @Override
    public int updateCusMasterWarehouse(CusMasterWarehouse cusMasterWarehouse)
    {
        cusMasterWarehouse.setUpdateTime(DateUtils.getNowDate());
        return cusMasterWarehouseMapper.updateCusMasterWarehouse(cusMasterWarehouse);
    }

    /**
     * 批量删除仓库主数据
     * 
     * @param ids 需要删除的仓库主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterWarehouseByIds(String[] ids)
    {
        return cusMasterWarehouseMapper.deleteCusMasterWarehouseByIds(ids);
    }

    /**
     * 删除仓库主数据信息
     * 
     * @param id 仓库主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterWarehouseById(String id)
    {
        return cusMasterWarehouseMapper.deleteCusMasterWarehouseById(id);
    }
}
