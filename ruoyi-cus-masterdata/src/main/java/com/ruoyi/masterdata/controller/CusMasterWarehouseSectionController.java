package com.ruoyi.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.masterdata.domain.CusMasterWarehouse;
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
import com.ruoyi.masterdata.domain.CusMasterWarehouseSection;
import com.ruoyi.masterdata.service.ICusMasterWarehouseSectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库区信息Controller
 * 
 * @author Shawn
 * @date 2023-11-13
 */
@RestController
@RequestMapping("/masterdata/warehouse/section")
public class CusMasterWarehouseSectionController extends BaseController
{
    @Autowired
    private ICusMasterWarehouseSectionService cusMasterWarehouseSectionService;

    /**
     * 查询库区信息列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        startPage();
        List<CusMasterWarehouseSection> list = cusMasterWarehouseSectionService.selectCusMasterWarehouseSectionList(cusMasterWarehouseSection);
        return getDataTable(list);
    }

    /**
     * 导出库区信息列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:export')")
    @Log(title = "库区信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        List<CusMasterWarehouseSection> list = cusMasterWarehouseSectionService.selectCusMasterWarehouseSectionList(cusMasterWarehouseSection);
        ExcelUtil<CusMasterWarehouseSection> util = new ExcelUtil<CusMasterWarehouseSection>(CusMasterWarehouseSection.class);
        util.exportExcel(response, list, "库区信息数据");
    }

    /**
     * 获取库区信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:query')")
    @GetMapping(value = "/{warehouseCode}/{code}")
    public AjaxResult getInfo(@PathVariable("warehouseCode") String warehouseCode, @PathVariable("code") String code)
    {
        return success(cusMasterWarehouseSectionService.selectCusMasterWarehouseSectionByCode(warehouseCode, code));
    }

    /**
     * 新增库区信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:add')")
    @Log(title = "库区信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        String warehouseCode = cusMasterWarehouseSection.getWarehouseCode();
        String code = cusMasterWarehouseSection.getCode();
        CusMasterWarehouseSection s = cusMasterWarehouseSectionService.selectCusMasterWarehouseSectionByCode(warehouseCode, code);
        if (s != null) {
            return error("库区编码： <" + code + ">  已经存在, 请重新输入！");
        } else {
            cusMasterWarehouseSection.setCreateBy(getUsername());
            cusMasterWarehouseSectionService.insertCusMasterWarehouseSection(cusMasterWarehouseSection);
            return success();
        }
    }

    /**
     * 修改库区信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:edit')")
    @Log(title = "库区信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusMasterWarehouseSection cusMasterWarehouseSection)
    {
        return toAjax(cusMasterWarehouseSectionService.updateCusMasterWarehouseSection(cusMasterWarehouseSection));
    }

    /**
     * 删除库区信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:remove')")
    @Log(title = "库区信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{warehouseCode}/{codes}")
    public AjaxResult remove(@PathVariable("warehouseCode") String warehouseCode, @PathVariable("codes") String[] codes)
    {
        return toAjax(cusMasterWarehouseSectionService.deleteCusMasterWarehouseSectionByCodes(warehouseCode, codes));
    }
}
