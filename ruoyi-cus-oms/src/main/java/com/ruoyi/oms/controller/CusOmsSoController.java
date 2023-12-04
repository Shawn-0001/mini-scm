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
import com.ruoyi.oms.domain.CusOmsSo;
import com.ruoyi.oms.service.ICusOmsSoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 *
 * @author Shawn
 * @date 2023-12-04
 */
@RestController
@RequestMapping("/oms/so")
public class CusOmsSoController extends BaseController {
    @Autowired
    private ICusOmsSoService cusOmsSoService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('oms:so:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusOmsSo cusOmsSo) {
        startPage();
        List<CusOmsSo> list = cusOmsSoService.selectCusOmsSoList(cusOmsSo);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('oms:so:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusOmsSo cusOmsSo) {
        List<CusOmsSo> list = cusOmsSoService.selectCusOmsSoList(cusOmsSo);
        ExcelUtil<CusOmsSo> util = new ExcelUtil<CusOmsSo>(CusOmsSo.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('oms:so:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(cusOmsSoService.selectCusOmsSoById(id));
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('oms:so:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusOmsSo cusOmsSo) {
        cusOmsSo.setCreateBy(getUsername());
        return toAjax(cusOmsSoService.insertCusOmsSo(cusOmsSo));
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('oms:so:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusOmsSo cusOmsSo) {
        return toAjax(cusOmsSoService.updateCusOmsSo(cusOmsSo));
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('oms:so:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(cusOmsSoService.deleteCusOmsSoByIds(ids));
    }
}
