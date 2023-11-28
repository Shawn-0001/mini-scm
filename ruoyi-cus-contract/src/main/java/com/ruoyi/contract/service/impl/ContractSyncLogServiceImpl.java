package com.ruoyi.contract.service.impl;

import com.ruoyi.contract.domain.ContractSyncLog;
import com.ruoyi.contract.mapper.ContractSyncLogMapper;
import com.ruoyi.contract.service.IContractSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同同步日志Service业务层处理
 * 
 * @author changgong
 * @date 2023-03-18
 */
@Service
public class ContractSyncLogServiceImpl implements IContractSyncLogService
{
    @Autowired
    private ContractSyncLogMapper contractSyncLogMapper;

    /**
     * 查询合同同步日志
     * 
     * @param syncLogId 合同同步日志主键
     * @return 合同同步日志
     */
    @Override
    public ContractSyncLog selectContractSyncLogBySyncLogId(String syncLogId)
    {
        return contractSyncLogMapper.selectContractSyncLogBySyncLogId(syncLogId);
    }

    /**
     * 查询合同同步日志列表
     * 
     * @param contractSyncLog 合同同步日志
     * @return 合同同步日志
     */
    @Override
    public List<ContractSyncLog> selectContractSyncLogList(ContractSyncLog contractSyncLog)
    {
        return contractSyncLogMapper.selectContractSyncLogList(contractSyncLog);
    }

    /**
     * 新增合同同步日志
     * 
     * @param contractSyncLog 合同同步日志
     * @return 结果
     */
    @Override
    public int insertContractSyncLog(ContractSyncLog contractSyncLog)
    {
        return contractSyncLogMapper.insertContractSyncLog(contractSyncLog);
    }

    /**
     * 修改合同同步日志
     * 
     * @param contractSyncLog 合同同步日志
     * @return 结果
     */
    @Override
    public int updateContractSyncLog(ContractSyncLog contractSyncLog)
    {
        return contractSyncLogMapper.updateContractSyncLog(contractSyncLog);
    }

    /**
     * 批量删除合同同步日志
     * 
     * @param syncLogIds 需要删除的合同同步日志主键
     * @return 结果
     */
    @Override
    public int deleteContractSyncLogBySyncLogIds(String[] syncLogIds)
    {
        return contractSyncLogMapper.deleteContractSyncLogBySyncLogIds(syncLogIds);
    }

    /**
     * 删除合同同步日志信息
     * 
     * @param syncLogId 合同同步日志主键
     * @return 结果
     */
    @Override
    public int deleteContractSyncLogBySyncLogId(String syncLogId)
    {
        return contractSyncLogMapper.deleteContractSyncLogBySyncLogId(syncLogId);
    }
}
