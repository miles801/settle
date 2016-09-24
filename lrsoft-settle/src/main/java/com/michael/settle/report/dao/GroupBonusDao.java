package com.michael.settle.report.dao;

import com.michael.settle.report.bo.GroupBonusBo;
import com.michael.settle.report.domain.GroupBonus;

import java.util.List;

/**
 * @author Michael
 */
public interface GroupBonusDao {

    String save(GroupBonus groupBonus);

    void update(GroupBonus groupBonus);

    /**
     * 高级查询接口，不使用分页
     */
    List<GroupBonus> query(GroupBonusBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<GroupBonus> pageQuery(GroupBonusBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(GroupBonusBo bo);

    GroupBonus findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(GroupBonus groupBonus);

}
