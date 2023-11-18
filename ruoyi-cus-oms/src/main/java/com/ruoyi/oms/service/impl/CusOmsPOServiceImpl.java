package com.ruoyi.oms.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.oms.mapper.CusOmsPOMapper;
import com.ruoyi.oms.domain.CusOmsPO;
import com.ruoyi.oms.service.ICusOmsPOService;

/**
 * 采购订单Service业务层处理
 *
 * @author Shawn
 * @date 2023-11-14
 */
@Service
public class CusOmsPOServiceImpl implements ICusOmsPOService {
    @Autowired
    private CusOmsPOMapper cusOmsPOMapper;

    /**
     * 查询采购订单
     *
     * @param id 采购订单主键
     * @return 采购订单
     */
    @Override
    public CusOmsPO selectCusOmsPOById(Long id) {
        return cusOmsPOMapper.selectCusOmsPOById(id);
    }

    /**
     * 查询采购订单列表
     *
     * @param cusOmsPO 采购订单
     * @return 采购订单
     */
    @Override
    public List<CusOmsPO> selectCusOmsPOList(CusOmsPO cusOmsPO) {
        return cusOmsPOMapper.selectCusOmsPOList(cusOmsPO);
    }

    /**
     * 新增采购订单
     *
     * @param cusOmsPO 采购订单
     * @return 结果
     */
    @Override
    public int insertCusOmsPO(CusOmsPO cusOmsPO) {
        String currentNumber = cusOmsPOMapper.getCurrentPONumber();
//        System.out.println("-------------------------------------------");
//        System.out.println(currentNumber);
//        System.out.println("-------------------------------------------");
        String newNumber = "";
        if (currentNumber != null && currentNumber != "") {
            int n = Integer.parseInt(currentNumber) + 1;
            newNumber = String.valueOf(n);
        } else {
            newNumber = "10" + "100001"; // 10: order created manually.
        }
        cusOmsPO.setOrderNumber(newNumber);
        return cusOmsPOMapper.insertCusOmsPO(cusOmsPO);
    }

    /**
     * 修改采购订单
     *
     * @param cusOmsPO 采购订单
     * @return 结果
     */
    @Override
    public int updateCusOmsPO(CusOmsPO cusOmsPO) {
        return cusOmsPOMapper.updateCusOmsPO(cusOmsPO);
    }

    /**
     * 批量删除采购订单
     *
     * @param ids 需要删除的采购订单主键
     * @return 结果
     */
    @Override
    public int deleteCusOmsPOByIds(Long[] ids) {
        return cusOmsPOMapper.deleteCusOmsPOByIds(ids);
    }

    /**
     * 删除采购订单信息
     *
     * @param id 采购订单主键
     * @return 结果
     */
    @Override
    public int deleteCusOmsPOById(Long id) {
        return cusOmsPOMapper.deleteCusOmsPOById(id);
    }
}
