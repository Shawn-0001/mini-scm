package com.ruoyi.tms.controller;

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
import com.ruoyi.tms.domain.CusTMSShippingInfo;
import com.ruoyi.tms.service.ICusTMSShippingInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 运单详细信息Controller
 * 
 * @author shawn
 * @date 2023-11-06
 */
@RestController
@RequestMapping("/tms/shippingInfo")
public class CusTMSShippingInfoController extends BaseController
{
    @Autowired
    private ICusTMSShippingInfoService cusTMSShippingInfoService;

    /**
     * 查询运单详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('tms:shippingInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CusTMSShippingInfo cusTMSShippingInfo)
    {
        startPage();
        List<CusTMSShippingInfo> list = cusTMSShippingInfoService.selectCusTMSShippingInfoList(cusTMSShippingInfo);
        return getDataTable(list);
    }

    /**
     * 导出运单详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('tms:shippingInfo:export')")
    @Log(title = "运单详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CusTMSShippingInfo cusTMSShippingInfo)
    {
        List<CusTMSShippingInfo> list = cusTMSShippingInfoService.selectCusTMSShippingInfoList(cusTMSShippingInfo);
        ExcelUtil<CusTMSShippingInfo> util = new ExcelUtil<CusTMSShippingInfo>(CusTMSShippingInfo.class);
        util.exportExcel(response, list, "运单详细信息数据");
    }

    /**
     * 获取运单详细信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('tms:shippingInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(cusTMSShippingInfoService.selectCusTMSShippingInfoById(id));
    }

    /**
     * 新增运单详细信息
     */
    @PreAuthorize("@ss.hasPermi('tms:shippingInfo:add')")
    @Log(title = "运单详细信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CusTMSShippingInfo cusTMSShippingInfo)
    {
        return toAjax(cusTMSShippingInfoService.insertCusTMSShippingInfo(cusTMSShippingInfo));
    }

    /**
     * 修改运单详细信息
     */
    @PreAuthorize("@ss.hasPermi('tms:shippingInfo:edit')")
    @Log(title = "运单详细信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CusTMSShippingInfo cusTMSShippingInfo)
    {
        return toAjax(cusTMSShippingInfoService.updateCusTMSShippingInfo(cusTMSShippingInfo));
    }

    /**
     * 删除运单详细信息
     */
    @PreAuthorize("@ss.hasPermi('tms:shippingInfo:remove')")
    @Log(title = "运单详细信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cusTMSShippingInfoService.deleteCusTMSShippingInfoByIds(ids));
    }
}
