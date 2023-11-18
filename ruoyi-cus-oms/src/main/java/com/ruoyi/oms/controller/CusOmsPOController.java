package com.ruoyi.oms.controller;

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
import com.ruoyi.oms.domain.CusOmsPO;
import com.ruoyi.oms.service.ICusOmsPOService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购订单Controller
 *
 * @author Shawn
 * @date 2023-11-14
 */
@RestController
@RequestMapping("/oms/po")
public class CusOmsPOController extends BaseController {
    @Autowired
    private ICusOmsPOService cusOmsPOService;

    /**
     * 查询采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('oms:po:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusOmsPO cusOmsPO) {
        startPage();
        List<CusOmsPO> list = cusOmsPOService.selectCusOmsPOList(cusOmsPO);
        return getDataTable(list);
    }

    /**
     * 导出采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('oms:po:export')")
    @Log(title = "采购订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusOmsPO cusOmsPO) {
        List<CusOmsPO> list = cusOmsPOService.selectCusOmsPOList(cusOmsPO);
        ExcelUtil<CusOmsPO> util = new ExcelUtil<CusOmsPO>(CusOmsPO.class);
        util.exportExcel(response, list, "采购订单数据");
    }

    /**
     * 获取采购订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('oms:po:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(cusOmsPOService.selectCusOmsPOById(id));
    }

    /**
     * 新增采购订单
     */
    @PreAuthorize("@ss.hasPermi('oms:po:add')")
    @Log(title = "采购订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusOmsPO cusOmsPO) {
        cusOmsPO.setCreateBy(getUsername());
        return toAjax(cusOmsPOService.insertCusOmsPO(cusOmsPO));
    }

    /**
     * 修改采购订单
     */
    @PreAuthorize("@ss.hasPermi('oms:po:edit')")
    @Log(title = "采购订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusOmsPO cusOmsPO) {
        cusOmsPO.setUpdateBy(getUsername());
        return toAjax(cusOmsPOService.updateCusOmsPO(cusOmsPO));
    }

    /**
     * 删除采购订单
     */
    @PreAuthorize("@ss.hasPermi('oms:po:remove')")
    @Log(title = "采购订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(cusOmsPOService.deleteCusOmsPOByIds(ids));
    }
}
