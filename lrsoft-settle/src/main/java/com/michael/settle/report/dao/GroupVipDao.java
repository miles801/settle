package com.michael.settle.report.dao;

import com.michael.settle.report.bo.GroupVipBo;
import com.michael.settle.report.domain.GroupVip;

import java.util.List;

/**
 * @author Michael
 */
public interface GroupVipDao {

    String save(GroupVip groupVip);

    void update(GroupVip groupVip);

    /**
     * 高级查询接口，不使用分页
     */
    List<GroupVip> query(GroupVipBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<GroupVip> pageQuery(GroupVipBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(GroupVipBo bo);

    GroupVip findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(GroupVip groupVip);

}
