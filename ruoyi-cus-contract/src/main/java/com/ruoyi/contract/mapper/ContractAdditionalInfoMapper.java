package com.ruoyi.contract.mapper;

import com.ruoyi.contract.domain.ContractAdditionalInfo;

import java.util.List;

/**
 * contractMapper接口
 * 
 * @author changgong0513
 * @date 2022-11-01
 */
public interface ContractAdditionalInfoMapper 
{
    /**
     * 查询contract
     * 
     * @param contractId contract主键
     * @return contract
     */
    public List<ContractAdditionalInfo> selectContractAdditionalInfoByContractId(String contractId);

    /**
     * 根据订单编号，取得附件
     *
     * @param orderId
     * @return
     */
    public List<ContractAdditionalInfo> selectOrderAdditional(String orderId);

    /**
     * 查询contract列表
     * 
     * @param contractAdditionalInfo contract
     * @return contract集合
     */
    public List<ContractAdditionalInfo> selectContractAdditionalInfoList(ContractAdditionalInfo contractAdditionalInfo);

    /**
     * 新增contract
     * 
     * @param contractAdditionalInfo contract
     * @return 结果
     */
    public int insertContractAdditionalInfo(ContractAdditionalInfo contractAdditionalInfo);

    /**
     * 修改contract
     * 
     * @param contractAdditionalInfo contract
     * @return 结果
     */
    public int updateContractAdditionalInfo(ContractAdditionalInfo contractAdditionalInfo);

    /**
     * 删除contract
     * 
     * @param additionalId contract主键
     * @return 结果
     */
    public int deleteContractAdditionalInfoByAdditionalId(String additionalId);

    /**
     * 批量删除contract
     * 
     * @param additionalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractAdditionalInfoByAdditionalIds(String[] additionalIds);


    /**
     * 删除上传文件
     *
     * @param filePath
     * @return
     */
    public int deleteUploadFile(String filePath);
}
