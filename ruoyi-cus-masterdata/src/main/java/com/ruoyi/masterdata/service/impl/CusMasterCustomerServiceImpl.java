package com.ruoyi.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.masterdata.mapper.CusMasterCustomerMapper;
import com.ruoyi.masterdata.domain.CusMasterCustomer;
import com.ruoyi.masterdata.service.ICusMasterCustomerService;

/**
 * 客户主数据Service业务层处理
 * 
 * @author Shawn
 * @date 2023-11-04
 */
@Service
public class CusMasterCustomerServiceImpl implements ICusMasterCustomerService 
{
    @Autowired
    private CusMasterCustomerMapper cusMasterCustomerMapper;

    /**
     * 查询客户主数据
     * 
     * @param id 客户主数据主键
     * @return 客户主数据
     */
    @Override
    public CusMasterCustomer selectCusMasterCustomerById(Long id)
    {
        return cusMasterCustomerMapper.selectCusMasterCustomerById(id);
    }

    /**
     * 查询客户主数据
     *
     * @param code 客户主数据编码
     * @return 客户主数据
     */
    public CusMasterCustomer selectCustomerMasterByCode(String code){
        return cusMasterCustomerMapper.selectCustomerMasterByCode(code);
    }

    /**
     * 查询客户主数据列表
     * 
     * @param cusMasterCustomer 客户主数据
     * @return 客户主数据
     */
    @Override
    public List<CusMasterCustomer> selectCusMasterCustomerList(CusMasterCustomer cusMasterCustomer)
    {
        return cusMasterCustomerMapper.selectCusMasterCustomerList(cusMasterCustomer);
    }

    /**
     * 新增客户主数据
     * 
     * @param cusMasterCustomer 客户主数据
     * @return 结果
     */
    @Override
    public int insertCusMasterCustomer(CusMasterCustomer cusMasterCustomer)
    {
        cusMasterCustomer.setCreateTime(DateUtils.getNowDate());
        return cusMasterCustomerMapper.insertCusMasterCustomer(cusMasterCustomer);
    }

    /**
     * 修改客户主数据
     * 
     * @param cusMasterCustomer 客户主数据
     * @return 结果
     */
    @Override
    public int updateCusMasterCustomer(CusMasterCustomer cusMasterCustomer)
    {
        cusMasterCustomer.setUpdateTime(DateUtils.getNowDate());
        return cusMasterCustomerMapper.updateCusMasterCustomer(cusMasterCustomer);
    }

    /**
     * 批量删除客户主数据
     * 
     * @param ids 需要删除的客户主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterCustomerByIds(Long[] ids)
    {
        return cusMasterCustomerMapper.deleteCusMasterCustomerByIds(ids);
    }

    /**
     * 删除客户主数据信息
     * 
     * @param id 客户主数据主键
     * @return 结果
     */
    @Override
    public int deleteCusMasterCustomerById(Long id)
    {
        return cusMasterCustomerMapper.deleteCusMasterCustomerById(id);
    }
}
