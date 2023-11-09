package com.ruoyi.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.masterdata.domain.CusMasterSupplier;
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
import com.ruoyi.masterdata.domain.CusMasterWarehouse;
import com.ruoyi.masterdata.service.ICusMasterWarehouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库主数据Controller
 *
 * @author shawn
 * @date 2023-11-05
 */
@RestController
@RequestMapping("/masterdata/warehouse")
public class CusMasterWarehouseController extends BaseController {
    @Autowired
    private ICusMasterWarehouseService cusMasterWarehouseService;

    /**
     * 查询仓库主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusMasterWarehouse cusMasterWarehouse) {
        startPage();
        List<CusMasterWarehouse> list = cusMasterWarehouseService.selectCusMasterWarehouseList(cusMasterWarehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库主数据列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:export')")
    @Log(title = "仓库主数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusMasterWarehouse cusMasterWarehouse) {
        List<CusMasterWarehouse> list = cusMasterWarehouseService.selectCusMasterWarehouseList(cusMasterWarehouse);
        ExcelUtil<CusMasterWarehouse> util = new ExcelUtil<CusMasterWarehouse>(CusMasterWarehouse.class);
        util.exportExcel(response, list, "仓库主数据数据");
    }

    /**
     * 获取仓库主数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(cusMasterWarehouseService.selectCusMasterWarehouseById(id));
    }

    /**
     * 获取仓库主数据详细信息 by code
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:query')")
    @GetMapping(value = "/code/{code}")
    public AjaxResult getInfoByCode(@PathVariable("code") String code) {
        return success(cusMasterWarehouseService.selectWarehouseMasterByCode(code));
    }

    /**
     * 新增仓库主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:add')")
    @Log(title = "仓库主数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusMasterWarehouse cusMasterWarehouse) {
        String code = cusMasterWarehouse.getCode();
        CusMasterWarehouse s = cusMasterWarehouseService.selectWarehouseMasterByCode(code);
        if (s != null) {
            return error("仓库编码： <" + code + ">  已经存在, 请重新输入！");
        } else {
            cusMasterWarehouse.setCreateBy(getUsername());
            cusMasterWarehouseService.insertCusMasterWarehouse(cusMasterWarehouse);
            return success();
        }
    }

    /**
     * 修改仓库主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:edit')")
    @Log(title = "仓库主数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusMasterWarehouse cusMasterWarehouse) {
        cusMasterWarehouse.setUpdateBy(getUsername());
        return toAjax(cusMasterWarehouseService.updateCusMasterWarehouse(cusMasterWarehouse));
    }

    /**
     * 删除仓库主数据
     */
    @PreAuthorize("@ss.hasPermi('masterdata:warehouse:remove')")
    @Log(title = "仓库主数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(cusMasterWarehouseService.deleteCusMasterWarehouseByIds(ids));
    }
}
