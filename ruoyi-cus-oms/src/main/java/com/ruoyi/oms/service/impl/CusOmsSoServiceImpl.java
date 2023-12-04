package com.ruoyi.oms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.oms.mapper.CusOmsSoMapper;
import com.ruoyi.oms.domain.CusOmsSo;
import com.ruoyi.oms.service.ICusOmsSoService;

/**
 * 销售订单Service业务层处理
 * 
 * @author Shawn
 * @date 2023-12-04
 */
@Service
public class CusOmsSoServiceImpl implements ICusOmsSoService 
{
    @Autowired
    private CusOmsSoMapper cusOmsSoMapper;

    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    @Override
    public CusOmsSo selectCusOmsSoById(Long id)
    {
        return cusOmsSoMapper.selectCusOmsSoById(id);
    }

    /**
     * 查询销售订单列表
     * 
     * @param cusOmsSo 销售订单
     * @return 销售订单
     */
    @Override
    public List<CusOmsSo> selectCusOmsSoList(CusOmsSo cusOmsSo)
    {
        return cusOmsSoMapper.selectCusOmsSoList(cusOmsSo);
    }

    /**
     * 新增销售订单
     * 
     * @param cusOmsSo 销售订单
     * @return 结果
     */
    @Override
    public int insertCusOmsSo(CusOmsSo cusOmsSo)
    {
        String currentNumber = cusOmsSoMapper.getCurrentSONumber();
        String newNumber = "";
        if (currentNumber != null && currentNumber != "") {
            int n = Integer.parseInt(currentNumber) + 1;
            newNumber = String.valueOf(n);
        } else {
            newNumber = "60" + "100001"; // 60: order created manually.
        }
        cusOmsSo.setOrderNumber(newNumber);
        return cusOmsSoMapper.insertCusOmsSo(cusOmsSo);
    }

    /**
     * 修改销售订单
     * 
     * @param cusOmsSo 销售订单
     * @return 结果
     */
    @Override
    public int updateCusOmsSo(CusOmsSo cusOmsSo)
    {
        cusOmsSo.setUpdateTime(DateUtils.getNowDate());
        return cusOmsSoMapper.updateCusOmsSo(cusOmsSo);
    }

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteCusOmsSoByIds(Long[] ids)
    {
        return cusOmsSoMapper.deleteCusOmsSoByIds(ids);
    }

    /**
     * 删除销售订单信息
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteCusOmsSoById(Long id)
    {
        return cusOmsSoMapper.deleteCusOmsSoById(id);
    }
}
