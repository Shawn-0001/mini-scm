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
import com.ruoyi.masterdata.domain.CusMasterSupplier;
import com.ruoyi.masterdata.service.ICusMasterSupplierService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商主数据Controller
 * 
 * @author shawn
 * @date 2023-11-05
 */
@RestController
@RequestMapping("/masterdata/supplier")
public class CusMasterSupplierController extends BaseController
{
    @Autowired
    private ICusMasterSupplierService cusMasterSupplierService;

    /**
     * 查询供应商主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusMasterSupplier cusMasterSupplier)
    {
        startPage();
        List<CusMasterSupplier> list = cusMasterSupplierService.selectCusMasterSupplierList(cusMasterSupplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:supplier:export')")
    @Log(title = "供应商主数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusMasterSupplier cusMasterSupplier)
    {
        List<CusMasterSupplier> list = cusMasterSupplierService.selectCusMasterSupplierList(cusMasterSupplier);
        ExcelUtil<CusMasterSupplier> util = new ExcelUtil<CusMasterSupplier>(CusMasterSupplier.class);
        util.exportExcel(response, list, "供应商主数据数据");
    }

    /**
     * 获取供应商主数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:supplier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cusMasterSupplierService.selectCusMasterSupplierById(id));
    }

    /**
     * 新增供应商主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:supplier:add')")
    @Log(title = "供应商主数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusMasterSupplier cusMasterSupplier)
    {
        String code = cusMasterSupplier.getCode();
        CusMasterSupplier s = cusMasterSupplierService.selectSupplierMasterByCode(code);
        if (s != null) {
            return error("供应商编码： <" + code + ">  已经存在, 请重新输入！");
        } else {
            cusMasterSupplier.setCreateBy(getUsername());
            cusMasterSupplierService.insertCusMasterSupplier(cusMasterSupplier);
            return success();
        }
    }

    /**
     * 修改供应商主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:supplier:edit')")
    @Log(title = "供应商主数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusMasterSupplier cusMasterSupplier)
    {
        cusMasterSupplier.setUpdateBy(getUsername());
        return toAjax(cusMasterSupplierService.updateCusMasterSupplier(cusMasterSupplier));
    }

    /**
     * 删除供应商主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:supplier:remove')")
    @Log(title = "供应商主数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cusMasterSupplierService.deleteCusMasterSupplierByIds(ids));
    }
}
