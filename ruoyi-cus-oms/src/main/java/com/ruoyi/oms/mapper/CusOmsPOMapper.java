package com.ruoyi.oms.mapper;

import java.util.List;
import com.ruoyi.oms.domain.CusOmsPO;

/**
 * 采购订单Mapper接口
 * 
 * @author Shawn
 * @date 2023-11-14
 */
public interface CusOmsPOMapper 
{
    /**
     * 查询采购订单
     * 
     * @param id 采购订单主键
     * @return 采购订单
     */
    public CusOmsPO selectCusOmsPOById(Long id);

    /**
     * 查询采购订单列表
     * 
     * @param cusOmsPO 采购订单
     * @return 采购订单集合
     */
    public List<CusOmsPO> selectCusOmsPOList(CusOmsPO cusOmsPO);

    /**
     * 新增采购订单
     * 
     * @param cusOmsPO 采购订单
     * @return 结果
     */
    public int insertCusOmsPO(CusOmsPO cusOmsPO);

    /**
     * 修改采购订单
     * 
     * @param cusOmsPO 采购订单
     * @return 结果
     */
    public int updateCusOmsPO(CusOmsPO cusOmsPO);

    /**
     * 删除采购订单
     * 
     * @param id 采购订单主键
     * @return 结果
     */
    public int deleteCusOmsPOById(Long id);

    /**
     * 批量删除采购订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCusOmsPOByIds(Long[] ids);

    /**
     * 获取最大采购订单编号
     *
     * @return 最大采购订单编号
     */
    public String getCurrentPONumber();
}
