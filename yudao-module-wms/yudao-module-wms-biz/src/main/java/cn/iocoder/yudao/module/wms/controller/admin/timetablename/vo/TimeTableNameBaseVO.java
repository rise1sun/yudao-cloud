package cn.iocoder.yudao.module.wms.controller.admin.timetablename.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
* 月份条码 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class TimeTableNameBaseVO {

    @Schema(description = "表名称")
    private String tableName;

    @Schema(description = "月份")
    private Integer month;

}
