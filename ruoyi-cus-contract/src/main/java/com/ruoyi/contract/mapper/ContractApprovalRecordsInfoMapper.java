package com.ruoyi.contract.mapper;

import com.ruoyi.contract.domain.ContractApprovalRecordsInfo;

import java.util.List;

/**
 * 合同管理-审批记录Mapper接口
 * 
 * @author changgong
 * @date 2022-11-29
 */
public interface ContractApprovalRecordsInfoMapper 
{
    /**
     * 查询合同管理-审批记录
     * 
     * @param approvalRecordId 合同管理-审批记录主键
     * @return 合同管理-审批记录
     */
    public ContractApprovalRecordsInfo selectContractApprovalRecordsInfoByApprovalRecordId(String approvalRecordId);

    /**
     * 查询合同管理-审批记录列表
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 合同管理-审批记录集合
     */
    public List<ContractApprovalRecordsInfo> selectContractApprovalRecordsInfoList(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 根据审批编号和用户编号，取得审批记录
     *
     * @param contractApprovalRecordsInfo
     * @return
     */
    public ContractApprovalRecordsInfo selectContractApprovalRecordsInfoByApprovalIdAndUserId(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 查询合同管理-根据审批编号，取得审批记录数据
     *
     * @param approvalId 审批编号
     * @return 合同管理-审批记录集合
     */
    public List<ContractApprovalRecordsInfo> getContractApprovalRecordsByApprovalId(String approvalId);

    /**
     * 新增合同管理-审批记录
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 结果
     */
    public int insertContractApprovalRecordsInfo(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 修改合同管理-审批记录
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 结果
     */
    public int updateContractApprovalRecordsInfo(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 删除合同管理-审批记录
     * 
     * @param approvalRecordId 合同管理-审批记录主键
     * @return 结果
     */
    public int deleteContractApprovalRecordsInfoByApprovalRecordId(String approvalRecordId);

    /**
     * 批量删除合同管理-审批记录
     * 
     * @param approvalRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractApprovalRecordsInfoByApprovalRecordIds(String[] approvalRecordIds);
}
