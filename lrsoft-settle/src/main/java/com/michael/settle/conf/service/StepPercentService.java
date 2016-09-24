package com.michael.settle.conf.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.conf.bo.StepPercentBo;
import com.michael.settle.conf.domain.StepPercent;
import com.michael.settle.conf.vo.StepPercentVo;

import java.util.List;

/**
 * @author Michael
 */
public interface StepPercentService {

    /**
     * 保存
     */
    String save(StepPercent stepPercent);

    /**
     * 更新
     */
    void update(StepPercent stepPercent);

    /**
     * 分页查询
     */
    PageVo pageQuery(StepPercentBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<StepPercentVo> query(StepPercentBo bo);

    /**
     * 根据ID查询对象的信息
     */
    StepPercentVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


}
