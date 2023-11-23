package cn.iocoder.yudao.module.wms.convert.fireprocess;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.wms.controller.admin.fireProcess.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.fireprocess.FireProcessDO;

/**
 * 消防任务 Convert
 *
 * @author 管理员
 */
@Mapper
public interface FireProcessConvert {

    FireProcessConvert INSTANCE = Mappers.getMapper(FireProcessConvert.class);

    FireProcessDO convert(FireProcessCreateReqVO bean);

    FireProcessDO convert(FireProcessUpdateReqVO bean);

    FireProcessRespVO convert(FireProcessDO bean);

    List<FireProcessRespVO> convertList(List<FireProcessDO> list);

    PageResult<FireProcessRespVO> convertPage(PageResult<FireProcessDO> page);

    List<FireProcessExcelVO> convertList02(List<FireProcessDO> list);

}
