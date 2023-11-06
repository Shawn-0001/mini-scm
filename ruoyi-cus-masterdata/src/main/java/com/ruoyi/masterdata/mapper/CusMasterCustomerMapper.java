package com.ruoyi.masterdata.mapper;

import java.util.List;
import com.ruoyi.masterdata.domain.CusMasterCustomer;

/**
 * 客户主数据Mapper接口
 * 
 * @author Shawn
 * @date 2023-11-04
 */
public interface CusMasterCustomerMapper 
{
    /**
     * 查询客户主数据
     * 
     * @param id 客户主数据主键
     * @return 客户主数据
     */
    public CusMasterCustomer selectCusMasterCustomerById(Long id);

    /**
     * 查询客户主数据
     *
     * @param code 客户主数据编码
     * @return 客户主数据
     */
    public CusMasterCustomer selectCustomerMasterByCode(String code);

    /**
     * 查询客户主数据列表
     * 
     * @param cusMasterCustomer 客户主数据
     * @return 客户主数据集合
     */
    public List<CusMasterCustomer> selectCusMasterCustomerList(CusMasterCustomer cusMasterCustomer);

    /**
     * 新增客户主数据
     * 
     * @param cusMasterCustomer 客户主数据
     * @return 结果
     */
    public int insertCusMasterCustomer(CusMasterCustomer cusMasterCustomer);

    /**
     * 修改客户主数据
     * 
     * @param cusMasterCustomer 客户主数据
     * @return 结果
     */
    public int updateCusMasterCustomer(CusMasterCustomer cusMasterCustomer);

    /**
     * 删除客户主数据
     * 
     * @param id 客户主数据主键
     * @return 结果
     */
    public int deleteCusMasterCustomerById(Long id);

    /**
     * 批量删除客户主数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCusMasterCustomerByIds(Long[] ids);
}
