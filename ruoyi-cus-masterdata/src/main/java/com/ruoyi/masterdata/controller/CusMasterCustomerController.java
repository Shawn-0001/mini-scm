package com.ruoyi.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.masterdata.domain.CusMasterCustomer;
import com.ruoyi.masterdata.service.ICusMasterCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户主数据Controller
 * 
 * @author Shawn
 * @date 2023-11-04
 */
@RestController
@RequestMapping("/masterdata/customer")
public class CusMasterCustomerController extends BaseController
{
    @Autowired
    private ICusMasterCustomerService cusMasterCustomerService;

    /**
     * 查询客户主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusMasterCustomer cusMasterCustomer)
    {
        startPage();
        List<CusMasterCustomer> list = cusMasterCustomerService.selectCusMasterCustomerList(cusMasterCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:customer:export')")
    @Log(title = "客户主数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusMasterCustomer cusMasterCustomer)
    {
        List<CusMasterCustomer> list = cusMasterCustomerService.selectCusMasterCustomerList(cusMasterCustomer);
        ExcelUtil<CusMasterCustomer> util = new ExcelUtil<CusMasterCustomer>(CusMasterCustomer.class);
        util.exportExcel(response, list, "客户主数据数据");
    }

    /**
     * 获取客户主数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cusMasterCustomerService.selectCusMasterCustomerById(id));
    }

    /**
     * 新增客户主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:customer:add')")
    @Log(title = "客户主数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusMasterCustomer cusMasterCustomer)
    {
        return toAjax(cusMasterCustomerService.insertCusMasterCustomer(cusMasterCustomer));
    }

    /**
     * 修改客户主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:customer:edit')")
    @Log(title = "客户主数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusMasterCustomer cusMasterCustomer)
    {
        return toAjax(cusMasterCustomerService.updateCusMasterCustomer(cusMasterCustomer));
    }

    /**
     * 删除客户主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:customer:remove')")
    @Log(title = "客户主数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cusMasterCustomerService.deleteCusMasterCustomerByIds(ids));
    }
}
