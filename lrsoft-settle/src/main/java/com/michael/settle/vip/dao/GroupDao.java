package com.michael.settle.vip.dao;

import com.michael.settle.vip.bo.GroupBo;
import com.michael.settle.vip.domain.Group;

import java.util.List;

/**
 * @author Michael
 */
public interface GroupDao {

    String save(Group group);

    void update(Group group);

    /**
     * 高级查询接口，不使用分页
     */
    List<Group> query(GroupBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Group> pageQuery(GroupBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(GroupBo bo);

    Group findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Group group);

    /**
     * 判断是否具有重复的项
     *
     * @param code 编号
     * @param id   排除自身
     * @return true：存在
     */
    boolean hasCode(String code, String id);

    /**
     * 根据编号查询团队
     *
     * @param code 编号
     * @return 团队
     */
    Group findByCode(String code);

    /**
     * 查询指定文交所指定名称的团队的编号，如果有多个，则取第一个
     *
     * @param company   文交所编号
     * @param groupName 团队名称
     * @return 团队编号
     */
    String findCode(String company, String groupName);
}
