package com.ruoyi.contract.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;;
import com.ruoyi.contract.domain.*;
import com.ruoyi.contract.service.*;
import com.ruoyi.masterdata.domain.CusMasterCustomer;
import com.ruoyi.masterdata.domain.CusMasterMaterial;
import com.ruoyi.masterdata.service.ICusMasterCustomerService;
import com.ruoyi.masterdata.service.ICusMasterMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 合同管理Controller
 * 
 * @author changgong
 * @date 2022-10-30
 */
@RestController
@RequestMapping("/contract/mgr")
public class ContractContentInfoController extends BaseController
{
    @Autowired
    private IContractContentInfoService contractContentInfoService;

    @Autowired
    private IContractAdditionalInfoService contractAdditionalInfoService;

    @Autowired
    private IContractApprovalInfoService contractApprovalInfoService;

    @Autowired
    private IContractApprovalRecordsInfoService contractApprovalRecordsInfoService;

    @Autowired
    private ICusMasterMaterialService masterDataMaterialInfoService;

    @Autowired
    private ICusMasterCustomerService masterDataClientInfoService;

    @Resource
    private IContractSyncLogService contractSyncLogService;

    /**
     * 查询合同管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ContractContentInfo contractContentInfo) {

        // 设置请求分页数据
        startPage();

        // 查询合同管理列表
        Long deptId = this.getDeptId();
        contractContentInfo.setBelongDeptId(deptId);
        List<ContractContentInfo> list = contractContentInfoService.selectContractContentInfoList(contractContentInfo);

        // 生成前端显示合同数据列表
        contractContentInfoService.makeModelViewData(list);

        // 处理合同管理保存和生成按钮状态
        handleConstractIsExist(contractContentInfo);

        // 响应请求分页数据
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractContentInfo contractContentInfo)
    {
        List<ContractContentInfo> list = contractContentInfoService.selectContractContentInfoList(contractContentInfo);
        ExcelUtil<ContractContentInfo> util = new ExcelUtil<ContractContentInfo>(ContractContentInfo.class);
        util.exportExcel(response, list, "合同管理数据");
    }

    /**
     * 获取合同管理详细信息
     */
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") String contractId) {

        ContractContentInfo contractContentInfo = contractContentInfoService.selectContractContentInfoByContractId(contractId);

        // 取得所有客户主数据
        List<CusMasterCustomer> clientList = masterDataClientInfoService.selectCusMasterCustomerList(new CusMasterCustomer());
        // 客户主数据列表转成Map（key：baseId, value：companyName）
        Map<Long, String> clientMap = clientList
                .stream()
                .collect(Collectors.toMap(CusMasterCustomer::getId, CusMasterCustomer::getName));

        clientMap.forEach((key, value) -> {
            if (StringUtils.contains(value, contractContentInfo.getOppositeCompanyName())) {
                contractContentInfo.setBaseId(String.valueOf(key));
                contractContentInfo.setCompanyName(contractContentInfo.getOppositeCompanyName());
            }

            return;
        });

        // 处理合同管理保存和生成按钮状态
        handleConstractIsExist(contractContentInfo);

        return AjaxResult.success(contractContentInfo);
    }

    /**
     * 新增合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractContentInfo contractContentInfo) {

        int retCode = 1;
        contractContentInfo.setBelongDeptId(getDeptId());
        contractContentInfo.setCreateBy(SecurityUtils.getUsername());
        contractContentInfo.setCreateTime(DateUtils.parseDate(DateUtils.getTime()));
        contractContentInfo.setUpdateBy(SecurityUtils.getUsername());
        contractContentInfo.setUpdateTime(DateUtils.parseDate(DateUtils.getTime()));
        contractContentInfo.setBizVersion(1L);

        CusMasterMaterial material = masterDataMaterialInfoService
                .selectMasterDataMaterialInfoByMaterialId(contractContentInfo.getGoodsName());

        contractContentInfo.setGoodsId(contractContentInfo.getGoodsName());
        contractContentInfo.setGoodsName(material.getName());
        String actionType = contractContentInfo.getContractActionType();
        if (StringUtils.equals(actionType, "2")) {
            contractContentInfo.setConstractIsExist(1);
            boolean isExist = contractContentInfoService.isExistContractByContractId(contractContentInfo.getContractId());
            if (!isExist) {
                // 未进行保存合同，直接进行生成订单的场合
                contractContentInfo.setContractStatus("MANUALADD");
                contractContentInfo.setLocalContractStatus("5"); // 本地合同状态：手动添加
                contractContentInfoService.insertContractContentInfo(contractContentInfo);
            } else {
                contractContentInfoService.updateContractContentInfo(contractContentInfo);
            }

            // 根据合同数据，生成到采购表或者销售表的场合
            retCode = contractContentInfoService.importContractDataIntoPurchaseSaleTable(contractContentInfo);
        } else {
            // 新增和首次保存合同的场合
            contractContentInfo.setContractStatus("MANUALADD");
            contractContentInfo.setLocalContractStatus("5"); // 本地合同状态：手动添加
            retCode = contractContentInfoService.insertContractContentInfo(contractContentInfo);
        }

        return toAjax(retCode);
    }

    /**
     * 修改合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractContentInfo contractContentInfo) {

        int retCode = 1;
        contractContentInfo.setBelongDeptId(getDeptId());
        contractContentInfo.setUpdateTime(DateUtils.getNowDate());
        contractContentInfo.setUpdateBy(SecurityUtils.getUsername());

        String actionType = contractContentInfo.getContractActionType();
        if (StringUtils.equals(actionType, "1")) {
            // 更新和非首次保存合同的场合
            contractContentInfo.setLocalContractStatus("2");
//            retCode = contractContentInfoService.updateContractContentInfo(contractContentInfo);
        } else {
            // 根据合同数据，生成到采购表或者销售表的场合
            contractContentInfo.setConstractIsExist(1);
            contractContentInfo.setLocalContractStatus("3");

            contractContentInfoService.importContractDataIntoPurchaseSaleTable(contractContentInfo);
        }

        contractContentInfoService.updateContractContentInfo(contractContentInfo);

        return toAjax(retCode);
    }

    /**
     * 删除合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contractId}")
    public AjaxResult remove(@PathVariable String[] contractId) {
        return toAjax(contractContentInfoService.deleteContractContentInfoByContractIds(contractId));
    }

    /**
     * 从钉钉同步合同数据
     */
    @PostMapping("/sync")
    public AjaxResult sync(ContractContentInfo contractContentInfo) throws Exception {

        System.out.println("-------------------------从钉钉同步合同数据开始-------------------------");

        // 初始时间
        long startTime = System.currentTimeMillis();

        AjaxResult syncReslut = AjaxResult.success();
        if (contractContentInfoService.syncContractContentInfo() == 10001) {
            syncReslut = AjaxResult.error("未取得所有可管理的表单列表");
        }

        // 结束时间
        long endTime = System.currentTimeMillis();

        // 打印
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

        System.out.println("-------------------------从钉钉同步合同数据结束-------------------------");

        return syncReslut;
    }

    /**
     * 从钉钉同步合同数据
     */
    @PostMapping("/fk/sync")
    public AjaxResult fkSync() throws Exception {

        System.out.println("-------------------------从钉钉同步合同数据开始-------------------------");

        // 初始时间
        long startTime = System.currentTimeMillis();

        contractContentInfoService.syncFkContractInfo();

        // 结束时间
        long endTime = System.currentTimeMillis();

        // 打印
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

        System.out.println("-------------------------从钉钉同步合同数据结束-------------------------");

        return AjaxResult.success();
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file, UploadData uploadData)
            throws InvalidExtensionException, IOException {

        String filePath = RuoYiConfig.getUploadPath();
        String fileName =  file.getOriginalFilename();
        File file_dir= new File(filePath);
        if(!file_dir.exists()) {
            file_dir.mkdirs();
        }

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        File desc = createAbsoluteFile(filePath, fileName);
        file.transferTo(desc);

        ContractAdditionalInfo contractAdditionalInfo = new ContractAdditionalInfo();
        contractAdditionalInfo.setBizVersion(1L);
        contractAdditionalInfo.setCreateTime(DateUtils.getNowDate());
        contractAdditionalInfo.setUpdateTime(DateUtils.getNowDate());
        contractAdditionalInfo.setCreateBy(SecurityUtils.getUsername());
        contractAdditionalInfo.setUpdateBy(SecurityUtils.getUsername());
        contractAdditionalInfo.setContractId(uploadData.getUploadContractId());

        if (fileName.lastIndexOf(".jpeg") > 0 || fileName.lastIndexOf(".png") > 0) {
            contractAdditionalInfo.setUploadImagePath(filePath + File.separator + fileName);
        } else {
            contractAdditionalInfo.setUplloadFilePath(filePath + File.separator + fileName);
        }

        contractAdditionalInfoService.insertContractAdditionalInfo(contractAdditionalInfo);

        return AjaxResult.success();
    }

    /**
     * 获取合同附件列表
     */
    @GetMapping(value = "/additional/{contractId}")
    public TableDataInfo getContractAdditional(@PathVariable("contractId") String contractId) {
        List<ContractAdditionalInfo> list = contractAdditionalInfoService.selectContractAdditionalInfoByContractId(contractId);
        return getDataTable(list);
    }

    /**
     * 根据订单编号，取得附件
     */
    @GetMapping(value = "/order/additional/{orderId}")
    public TableDataInfo getOrderAdditional(@PathVariable("orderId") String orderId) {
        List<ContractAdditionalInfo> list = contractAdditionalInfoService.selectOrderAdditional(orderId);
        return getDataTable(list);
    }

    /**
     * 删除合同附件
     */
    @GetMapping(value = "/del/{contractId}/additional/{additionalId}")
    public TableDataInfo delContractAdditional(@PathVariable("contractId") String contractId,
                                               @PathVariable("additionalId") String additionalId) {

        int delResult = contractAdditionalInfoService.deleteContractAdditionalInfoByAdditionalId(additionalId);
        toAjax(delResult);

        List<ContractAdditionalInfo> list = contractAdditionalInfoService.selectContractAdditionalInfoByContractId(contractId);
        return getDataTable(list);
    }

    /**
     * 导入合同模板下载。
     *
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<ContractContentInfo> util = new ExcelUtil<ContractContentInfo>(ContractContentInfo.class);
        util.importTemplateExcel(response, "合同数据");
    }

    /**
     * 导入合同数据.
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "合同管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {

        ExcelUtil<ContractContentInfo> util = new ExcelUtil<ContractContentInfo>(ContractContentInfo.class);
        List<ContractContentInfo> contractList = util.importExcel(file.getInputStream());

        List<String> materialNameList = contractList
                .parallelStream()
                .filter(element -> StringUtils.isNotBlank(element.getGoodsName()))
                .map(ContractContentInfo::getGoodsName).collect(Collectors.toList());

        List<String> companyNameList = contractList
                .parallelStream()
                .filter(element -> StringUtils.isNotBlank(element.getOppositeCompanyName()))
                .map(ContractContentInfo::getOppositeCompanyName).collect(Collectors.toList());

        List<CusMasterMaterial> materialIdList =  masterDataMaterialInfoService
                .getMaterialIds(materialNameList.toArray(new String[materialNameList.size()]));

        List<CusMasterCustomer> clientIdList =  masterDataClientInfoService
                .getClientIds(companyNameList.toArray(new String[companyNameList.size()]));

        Map<String, Long> materialMap = materialIdList
                .stream()
                .collect(Collectors.toMap(CusMasterMaterial::getName, CusMasterMaterial::getId));

        Map<String, Long> clientMap = clientIdList
                .stream()
                .collect(Collectors.toMap(CusMasterCustomer::getName, CusMasterCustomer::getId));

        contractList.stream().forEach(element -> {
            if (materialMap.containsKey(element.getGoodsName())) {
                element.setGoodsId(materialMap.get(element.getGoodsName()).toString());
            }

            if (clientMap.containsKey(element.getOppositeCompanyName())) {
                element.setOppositeCompanyName(String.valueOf(clientMap.get(element.getOppositeCompanyName())));
            }

            element.setContractStatus("IMPORT");
            element.setLocalContractStatus("4"); // 本地合同状态：手动导入
        });

        String operName = getUsername();
        String message = contractContentInfoService.importContract(contractList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 根据合同编号，取得合同审批审批数据
     */
    @GetMapping(value = "/approval/{contractId}")
    public AjaxResult getContractApprovalInfoByContractId(@PathVariable("contractId") String contractId) {

        ContractApprovalInfo contractApprovalInfo = contractApprovalInfoService
                .getContractApprovalInfoByContractId(contractId);

        List<ContractApprovalRecordsInfo> list = null;
        if (contractApprovalInfo != null && StringUtils.isNotBlank(contractApprovalInfo.getApprovalId())) {
            list = contractApprovalRecordsInfoService
                    .getContractApprovalRecordsByApprovalId(contractApprovalInfo.getApprovalId());

            contractApprovalInfo.setApprovalRecordList(list);
        }

        return AjaxResult.success(contractApprovalInfo);
    }

    /**
     * 根据审批编号，取得审批记录数据
     */
    @GetMapping(value = "/approval/record/{approvalId}")
    public TableDataInfo getContractApprovalRecordsByApprovalId(@PathVariable("approvalId") String approvalId) {
        List<ContractApprovalRecordsInfo> list = contractApprovalRecordsInfoService
                .getContractApprovalRecordsByApprovalId(approvalId);
        return getDataTable(list);
    }

    /**
     * 合同同步结果数据列表
     *
     * @param contractSyncLog
     * @return
     * @throws Exception
     */
    @GetMapping("/sync/result")
    public TableDataInfo syncResult(ContractSyncLog contractSyncLog) throws Exception {

        // 设置请求分页数据
        startPage();

        // 查询合同同步结果数据列表
        List<ContractSyncLog> listContractSyncLog = contractSyncLogService.selectContractSyncLogList(contractSyncLog);

        // 响应请求分页数据
        return getDataTable(listContractSyncLog);
    }

    private static final File createAbsoluteFile(String uploadDir, String fileName)
            throws IOException {

        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }

        if (!desc.exists()) {
            desc.createNewFile();
        }

        return desc;
    }

    /**
     * 处理合同管理保存和生成按钮状态
     *
     * @param contractContentInfo
     */
    private void handleConstractIsExist(ContractContentInfo contractContentInfo) {
        // 管理员不能保存或者生成订单
        if (this.getDeptId() == 103 || this.getDeptId() == 100
                || StringUtils.equals(contractContentInfo.getLocalContractStatus(), "3")) {
            contractContentInfo.setConstractIsExist(1);
        } else {
            contractContentInfo.setConstractIsExist(0);
        }
    }
}
