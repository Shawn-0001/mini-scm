package com.ruoyi.oms.mapper;

import java.util.List;
import com.ruoyi.oms.domain.CusOmsSo;

/**
 * 销售订单Mapper接口
 * 
 * @author Shawn
 * @date 2023-12-04
 */
public interface CusOmsSoMapper 
{
    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    public CusOmsSo selectCusOmsSoById(Long id);

    /**
     * 查询销售订单列表
     * 
     * @param cusOmsSo 销售订单
     * @return 销售订单集合
     */
    public List<CusOmsSo> selectCusOmsSoList(CusOmsSo cusOmsSo);

    /**
     * 新增销售订单
     * 
     * @param cusOmsSo 销售订单
     * @return 结果
     */
    public int insertCusOmsSo(CusOmsSo cusOmsSo);

    /**
     * 修改销售订单
     * 
     * @param cusOmsSo 销售订单
     * @return 结果
     */
    public int updateCusOmsSo(CusOmsSo cusOmsSo);

    /**
     * 删除销售订单
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    public int deleteCusOmsSoById(Long id);

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCusOmsSoByIds(Long[] ids);

    /**
     * 获取最大销售订单编号
     *
     * @return 最大销售订单编号
     */
    public String getCurrentSONumber();
}
