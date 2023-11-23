package cn.iocoder.yudao.module.wms.controller.admin.formulaitem;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.module.wms.convert.formulaitem.FormulaItemConvert;
import cn.iocoder.yudao.module.wms.service.formulaitem.FormulaItemService;

@Tag(name=  "管理后台 - 工艺流程节点")
@RestController
@RequestMapping("/wms/formula-item")
@Validated
public class FormulaItemController {

    @Resource
    private FormulaItemService formulaItemService;

    @PostMapping("/create")
    @Operation(description ="创建工艺流程节点")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:create')")
    public CommonResult<Integer> createFormulaItem(@Valid @RequestBody FormulaItemCreateReqVO createReqVO) {
        return success(formulaItemService.createFormulaItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新工艺流程节点")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:update')")
    public CommonResult<Boolean> updateFormulaItem(@Valid @RequestBody FormulaItemUpdateReqVO updateReqVO) {
        formulaItemService.updateFormulaItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除工艺流程节点")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:delete')")
    public CommonResult<Boolean> deleteFormulaItem(@RequestParam("id") Integer id) {
        formulaItemService.deleteFormulaItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得工艺流程节点")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:query')")
    public CommonResult<FormulaItemRespVO> getFormulaItem(@RequestParam("id") Integer id) {
        FormulaItemDO formulaItem = formulaItemService.getFormulaItem(id);
        return success(FormulaItemConvert.INSTANCE.convert(formulaItem));
    }

    @GetMapping("/list")
    @Operation(description ="获得工艺流程节点列表")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:query')")
    public CommonResult<List<FormulaItemRespVO>> getFormulaItemList(@RequestParam("ids") Collection<Integer> ids) {
        List<FormulaItemDO> list = formulaItemService.getFormulaItemList(ids);
        return success(FormulaItemConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/getList")
    @Operation(description ="获得工艺流程节点列表")
// @PreAuthorize("@ss.hasPermission('wms:formula-item:query')")
    public CommonResult<List<FormulaItemRespVO>> getFormulaItemLists() {
        // 获得 FORMULAITEM 列表
        List<FormulaItemDO> list = formulaItemService.getFormulaItemLists();
        if (CollUtil.isEmpty(list)) {
            return success(Collections.emptyList());
        }
        // 转换为返回结果
        List<FormulaItemRespVO> resp = FormulaItemConvert.INSTANCE.convertList(list);
        return success(resp);
    }


    @GetMapping("/page")
    @Operation(description ="获得工艺流程节点分页")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:query')")
    public CommonResult<PageResult<FormulaItemRespVO>> getFormulaItemPage(@Valid FormulaItemPageReqVO pageVO) {
        PageResult<FormulaItemDO> pageResult = formulaItemService.getFormulaItemPage(pageVO);
        return success(FormulaItemConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出工艺流程节点 Excel")
    @PreAuthorize("@ss.hasPermission('wms:formula-item:export')")
    @OperateLog(type = EXPORT)
    public void exportFormulaItemExcel(@Valid FormulaItemExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FormulaItemDO> list = formulaItemService.getFormulaItemList(exportReqVO);
        // 导出 Excel
        List<FormulaItemExcelVO> datas = FormulaItemConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "工艺流程节点.xls", "数据", FormulaItemExcelVO.class, datas);
    }

}
