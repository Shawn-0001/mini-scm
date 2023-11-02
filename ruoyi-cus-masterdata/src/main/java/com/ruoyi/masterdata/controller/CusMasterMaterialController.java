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
import com.ruoyi.masterdata.domain.CusMasterMaterial;
import com.ruoyi.masterdata.service.ICusMasterMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料主数据Controller
 * 
 * @author ruoyi
 * @date 2023-11-02
 */
@RestController
@RequestMapping("/masterdata/material")
public class CusMasterMaterialController extends BaseController
{
    @Autowired
    private ICusMasterMaterialService cusMasterMaterialService;

    /**
     * 查询物料主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusMasterMaterial cusMasterMaterial)
    {
        startPage();
        List<CusMasterMaterial> list = cusMasterMaterialService.selectCusMasterMaterialList(cusMasterMaterial);
        return getDataTable(list);
    }

    /**
     * 导出物料主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:material:export')")
    @Log(title = "物料主数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusMasterMaterial cusMasterMaterial)
    {
        List<CusMasterMaterial> list = cusMasterMaterialService.selectCusMasterMaterialList(cusMasterMaterial);
        ExcelUtil<CusMasterMaterial> util = new ExcelUtil<CusMasterMaterial>(CusMasterMaterial.class);
        util.exportExcel(response, list, "物料主数据数据");
    }

    /**
     * 获取物料主数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cusMasterMaterialService.selectCusMasterMaterialById(id));
    }

    /**
     * 新增物料主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:material:add')")
    @Log(title = "物料主数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusMasterMaterial cusMasterMaterial)
    {
        return toAjax(cusMasterMaterialService.insertCusMasterMaterial(cusMasterMaterial));
    }

    /**
     * 修改物料主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:material:edit')")
    @Log(title = "物料主数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusMasterMaterial cusMasterMaterial)
    {
        return toAjax(cusMasterMaterialService.updateCusMasterMaterial(cusMasterMaterial));
    }

    /**
     * 删除物料主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:material:remove')")
    @Log(title = "物料主数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cusMasterMaterialService.deleteCusMasterMaterialByIds(ids));
    }
}
