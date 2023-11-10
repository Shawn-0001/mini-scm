package com.ruoyi.tms.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.uuid.Seq;
import com.ruoyi.masterdata.domain.CusMasterMaterial;
import com.ruoyi.masterdata.domain.CusMasterWarehouse;
import com.ruoyi.masterdata.service.ICusMasterMaterialService;
import com.ruoyi.masterdata.service.ICusMasterWarehouseService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tms.mapper.CusTMSShippingInfoMapper;
import com.ruoyi.tms.domain.CusTMSShippingInfo;
import com.ruoyi.tms.service.ICusTMSShippingInfoService;

import javax.validation.Validator;

/**
 * 运单详细信息Service业务层处理
 *
 * @author shawn
 * @date 2023-11-06
 */
@Service
public class CusTMSShippingInfoServiceImpl implements ICusTMSShippingInfoService {
    @Autowired
    private CusTMSShippingInfoMapper cusTMSShippingInfoMapper;

    @Autowired
    private ICusMasterWarehouseService cusMasterWarehouseService;

    @Autowired
    private ICusMasterMaterialService cusMasterMaterialService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    protected Validator validator;

    /**
     * 查询运单详细信息
     *
     * @param id 运单详细信息主键
     * @return 运单详细信息
     */
    @Override
    public CusTMSShippingInfo selectCusTMSShippingInfoById(Long id) {
        return cusTMSShippingInfoMapper.selectCusTMSShippingInfoById(id);
    }

    /**
     * 查询运单详细信息列表
     *
     * @param cusTMSShippingInfo 运单详细信息
     * @return 运单详细信息
     */
    @Override
    public List<CusTMSShippingInfo> selectCusTMSShippingInfoList(CusTMSShippingInfo cusTMSShippingInfo) {
        return cusTMSShippingInfoMapper.selectCusTMSShippingInfoList(cusTMSShippingInfo);
    }

    /**
     * 新增运单详细信息
     *
     * @param cusTMSShippingInfo 运单详细信息
     * @return 结果
     */
    @Override
    public int insertCusTMSShippingInfo(CusTMSShippingInfo cusTMSShippingInfo) {
        cusTMSShippingInfo.setCreateTime(DateUtils.getNowDate());
        return cusTMSShippingInfoMapper.insertCusTMSShippingInfo(cusTMSShippingInfo);
    }

    /**
     * 修改运单详细信息
     *
     * @param cusTMSShippingInfo 运单详细信息
     * @return 结果
     */
    @Override
    public int updateCusTMSShippingInfo(CusTMSShippingInfo cusTMSShippingInfo) {
        cusTMSShippingInfo.setUpdateTime(DateUtils.getNowDate());
        return cusTMSShippingInfoMapper.updateCusTMSShippingInfo(cusTMSShippingInfo);
    }

    /**
     * 批量删除运单详细信息
     *
     * @param ids 需要删除的运单详细信息主键
     * @return 结果
     */
    @Override
    public int deleteCusTMSShippingInfoByIds(Long[] ids) {
        return cusTMSShippingInfoMapper.deleteCusTMSShippingInfoByIds(ids);
    }

    /**
     * 删除运单详细信息信息
     *
     * @param id 运单详细信息主键
     * @return 结果
     */
    @Override
    public int deleteCusTMSShippingInfoById(Long id) {
        return cusTMSShippingInfoMapper.deleteCusTMSShippingInfoById(id);
    }

    /**
     * 导入运输单数据
     *
     * @param shippingInfoList 运输单数据列表
     * @param isUpdateSupport  是否更新支持，如果已存在，则进行更新数据
     * @param operName         操作用户
     * @return 结果
     */
    public String importShippingInfo(List<CusTMSShippingInfo> shippingInfoList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(shippingInfoList) || shippingInfoList.size() == 0) {
            throw new ServiceException("导入运输单数据不能为空！");
        }

        int successNumber = 0;
        int failureNumber = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

//        String transportdocumentsName = StringUtils.EMPTY;

        // line number
        int i = 1;

        for (CusTMSShippingInfo data : shippingInfoList) {
            try {
                BeanValidators.validateWithException(validator, data);

                // check batch number
                //TODO

                // check the material code
                CusMasterMaterial material = cusMasterMaterialService.selectMaterialMasterByCode(data.getMaterialCode());
                if (material == null) {
                    throw new Exception("未找到 [物料编码] ，请检查！！");
                }

                // check the shippingFrom
                CusMasterWarehouse warehouseCodeFrom = cusMasterWarehouseService.selectWarehouseMasterByCode(data.getShippingFrom());
                if (warehouseCodeFrom == null) {
                    throw new Exception("未找到 [发货仓库编码] ，请检查！！");
                }
                // check the shippingTo
                CusMasterWarehouse warehouseCodeTo = cusMasterWarehouseService.selectWarehouseMasterByCode(data.getShippingTo());
                if (warehouseCodeTo == null) {
                    throw new Exception("未找到 [卸货仓库编码] ，请检查！！");
                }

                // check the handled By
//                SysUser sysUser = sysUserService.selectUserByUserName(data.getHandledBy());
//                if (sysUser == null) {
//                    throw new Exception("未找到 [经办人] ，请检查！！");
//                }

                // generate shipping number
                String shippingNumber = "50" + System.currentTimeMillis() / 1000;
                data.setShippingOrder(shippingNumber);

                // set status
                data.setStatus("10");

                // set create by
                data.setCreateBy(operName);

                // insert data
                insertCusTMSShippingInfo(data);

                successNumber++;
                successMsg.append("<br/>" + successNumber + ": 第 " + i + " 行数据导入成功, 运单号 ：" + data.getShippingOrder());

            } catch (Exception e) {
                failureNumber++;
                String msg = "<br/>" + failureNumber + ": 第 " + i + " 行数据导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
            i = i + 1;
        }
        if (failureNumber > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNumber + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNumber + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
