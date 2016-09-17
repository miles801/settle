package com.michael.settle.vip.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.vip.bo.GroupBo;
import com.michael.settle.vip.domain.Group;
import com.michael.settle.vip.vo.GroupVo;

import java.util.List;

/**
 * @author Michael
 */
public interface GroupService {

    /**
     * 保存
     */
    String save(Group group);

    /**
     * 更新
     */
    void update(Group group);

    /**
     * 分页查询
     */
    PageVo pageQuery(GroupBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<GroupVo> query(GroupBo bo);

    /**
     * 根据ID查询对象的信息
     */
    GroupVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


    /**
     * 批量启用
     */
    void enable(String[] ids);

    /**
     * 批量禁用
     */
    void disable(String[] ids);

    /**
     * 查询所有有效的数据（不使用分页）
     *
     * @param bo 可选的查询条件
     */
    List<Group> queryValid(GroupBo bo);


    /**
     * 导入数据
     *
     * @param attachmentIds 上传的附件列表
     */
    void importData(String company, String[] attachmentIds);
}
