package cn.iocoder.yudao.module.wms.service.formulaitem;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.wms.controller.app.pda.vo.WarehousingVO;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaordertask.FormulaOrderTaskDO;
import cn.iocoder.yudao.module.wms.dal.mysql.formulaordertask.FormulaOrderTaskMapper;
import cn.iocoder.yudao.module.wms.service.batch.BatchService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.wms.controller.admin.formulaitem.vo.*;
import cn.iocoder.yudao.module.wms.dal.dataobject.formulaitem.FormulaItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.wms.convert.formulaitem.FormulaItemConvert;
import cn.iocoder.yudao.module.wms.dal.mysql.formulaitem.FormulaItemMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wms.enums.ErrorCodeConstants.*;

/**
 * 工艺流程节点 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class FormulaItemServiceImpl implements FormulaItemService {

    @Resource
    private FormulaItemMapper formulaItemMapper;

    @Resource
    private BatchService batchService;

    @Resource
    private FormulaOrderTaskMapper formulaOrderTaskMapper;


    @Override
    public Integer createFormulaItem(FormulaItemCreateReqVO createReqVO) {
        // 插入
        FormulaItemDO formulaItem = FormulaItemConvert.INSTANCE.convert(createReqVO);
        formulaItemMapper.insert(formulaItem);
        // 返回
        return formulaItem.getId();
    }

    @Override
    public void updateFormulaItem(FormulaItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateFormulaItemExists(updateReqVO.getId());
        // 更新
        FormulaItemDO updateObj = FormulaItemConvert.INSTANCE.convert(updateReqVO);
        formulaItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteFormulaItem(Integer id) {
        // 校验存在
        validateFormulaItemExists(id);
        // 删除
        formulaItemMapper.deleteById(id);
    }

    private void validateFormulaItemExists(Integer id) {
        if (formulaItemMapper.selectById(id) == null) {
            throw exception(FORMULA_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public FormulaItemDO getFormulaItem(Integer id) {
        return formulaItemMapper.selectById(id);
    }

    @Override
    public List<FormulaItemDO> getFormulaItemList(Collection<Integer> ids) {
        return formulaItemMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FormulaItemDO> getFormulaItemPage(FormulaItemPageReqVO pageReqVO) {
        return formulaItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FormulaItemDO> getFormulaItemList(FormulaItemExportReqVO exportReqVO) {
        return formulaItemMapper.selectList(exportReqVO);
    }

    @Override
    public List<FormulaItemDO> getFormulaItemLists() {
        return formulaItemMapper.selectLists();
    }

    @Override
    public Integer formulaItemFlow(WarehousingVO warehousingVO) {
        Integer orderTaskId = warehousingVO.getOrderTaskId();
      /*  List<FormulaOrderTaskDO> formulaOrderTaskDOS = formulaOrderTaskMapper
                .selectList(new LambdaQueryWrapperX<FormulaOrderTaskDO>()
                .eq(FormulaOrderTaskDO::getId, orderTaskId));*/
        if(orderTaskId == null){
            //初始节点 根据当前使用批次获取节点信息
            Map<String, Object> resultMap = batchService.getBatchAndFormulaInfo();
            Integer formulaId = (Integer) resultMap.get("formulaId");
            Integer formulaItemId = (Integer) resultMap.get("formulaItemId");
            String formulaName = (String) resultMap.get("formulaName");
            String formulaItemName = (String) resultMap.get("formulaItemName");
            //生成OrderTask号
            Long batchId = (Long) resultMap.get("batchId");
            long timestamp = DateUtil.current();
            String orderTaskNo  =""+batchId+formulaId+formulaItemId+timestamp;
            FormulaOrderTaskDO formulaOrderTaskDO = new FormulaOrderTaskDO().setFormulaId(String.valueOf(formulaId))
                    .setFormulaName(formulaName)
                    //第一次生成任务，还没到第一个工艺节点
                    //.setFormulaItemId(String.valueOf(formulaItemId))
                    //.setFormulaItemName(formulaItemName)
                    .setTrayNo(warehousingVO.getTrayNo())
                    .setOrderTask(orderTaskNo);
            int num = formulaOrderTaskMapper.insert(formulaOrderTaskDO);
            log.info("formulaOrderTask成功插入{}条记录",num);
            Integer id = formulaOrderTaskDO.getId();
            return id;
        }

        FormulaItemDO formulaItemDO = getNewFormulaItemByOldFormulaId(warehousingVO.getOrderTaskId());
        //更新工艺流转信息
        LambdaUpdateWrapper<FormulaOrderTaskDO> updateWrapper= new LambdaUpdateWrapper<FormulaOrderTaskDO>()
                .eq(FormulaOrderTaskDO::getId, warehousingVO.getOrderTaskId())
                .set(FormulaOrderTaskDO::getFormulaItemId, formulaItemDO.getId())
                .set(FormulaOrderTaskDO::getFormulaItemName,formulaItemDO.getName() );
        formulaOrderTaskMapper.update(null, updateWrapper);
        return warehousingVO.getOrderTaskId();
    }

    @Override
    public FormulaItemDO getFormulaItemByorderTaskId(Integer orderTaskId) {
        return formulaItemMapper.getFormulaItemByorderTaskId(orderTaskId);
    }

    @Override
    public FormulaItemDO getNewFormulaItemByOldFormulaId(Integer orderTaskId) {
        return formulaOrderTaskMapper.getNewFormulaItemByOldFormulaId(orderTaskId);
    }

}
