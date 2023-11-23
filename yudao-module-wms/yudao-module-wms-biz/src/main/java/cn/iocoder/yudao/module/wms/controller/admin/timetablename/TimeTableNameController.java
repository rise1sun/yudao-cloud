package cn.iocoder.yudao.module.wms.controller.admin.timetablename;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
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

import cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.timetablename.TimeTableNameDO;
import cn.iocoder.yudao.module.wms.convert.timetablename.TimeTableNameConvert;
import cn.iocoder.yudao.module.wms.service.timetablename.TimeTableNameService;

@Tag(name= "管理后台 - 月份条码")
@RestController
@RequestMapping("/wms/time-table-name")
@Validated
public class TimeTableNameController {

    @Resource
    private TimeTableNameService timeTableNameService;

    @PostMapping("/create")
    @Operation(description ="创建月份条码")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:create')")
    public CommonResult<Integer> createTimeTableName(@Valid @RequestBody TimeTableNameCreateReqVO createReqVO) {
        return success(timeTableNameService.createTimeTableName(createReqVO));
    }

    @PutMapping("/update")
    @Operation(description ="更新月份条码")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:update')")
    public CommonResult<Boolean> updateTimeTableName(@Valid @RequestBody TimeTableNameUpdateReqVO updateReqVO) {
        timeTableNameService.updateTimeTableName(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(description ="删除月份条码")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:delete')")
    public CommonResult<Boolean> deleteTimeTableName(@RequestParam("id") Integer id) {
        timeTableNameService.deleteTimeTableName(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(description ="获得月份条码")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:query')")
    public CommonResult<TimeTableNameRespVO> getTimeTableName(@RequestParam("id") Integer id) {
        TimeTableNameDO timeTableName = timeTableNameService.getTimeTableName(id);
        return success(TimeTableNameConvert.INSTANCE.convert(timeTableName));
    }

    @GetMapping("/list")
    @Operation(description ="获得月份条码列表")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:query')")
    public CommonResult<List<TimeTableNameRespVO>> getTimeTableNameList(@RequestParam("ids") Collection<Integer> ids) {
        List<TimeTableNameDO> list = timeTableNameService.getTimeTableNameList(ids);
        return success(TimeTableNameConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(description ="获得月份条码分页")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:query')")
    public CommonResult<PageResult<TimeTableNameRespVO>> getTimeTableNamePage(@Valid TimeTableNamePageReqVO pageVO) {
        PageResult<TimeTableNameDO> pageResult = timeTableNameService.getTimeTableNamePage(pageVO);
        return success(TimeTableNameConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(description ="导出月份条码 Excel")
    @PreAuthorize("@ss.hasPermission('wms:time-table-name:export')")
    @OperateLog(type = EXPORT)
    public void exportTimeTableNameExcel(@Valid TimeTableNameExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TimeTableNameDO> list = timeTableNameService.getTimeTableNameList(exportReqVO);
        // 导出 Excel
        List<TimeTableNameExcelVO> datas = TimeTableNameConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "月份条码.xls", "数据", TimeTableNameExcelVO.class, datas);
    }

}
