package com.michael.settle.report.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.report.bo.GroupBonusBo;
import com.michael.settle.report.domain.GroupBonus;
import com.michael.settle.report.vo.GroupBonusVo;

import java.util.List;

/**
 * @author Michael
 */
public interface GroupBonusService {

    /**
     * 保存
     */
    String save(GroupBonus groupBonus);

    /**
     * 更新
     */
    void update(GroupBonus groupBonus);

    /**
     * 分页查询
     */
    PageVo pageQuery(GroupBonusBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<GroupBonusVo> query(GroupBonusBo bo);

    /**
     * 根据ID查询对象的信息
     */
    GroupBonusVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


}
