package com.michael.settle.report.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.report.bo.GroupVipBo;
import com.michael.settle.report.domain.GroupVip;
import com.michael.settle.report.vo.GroupVipVo;

import java.util.List;

/**
 * @author Michael
 */
public interface GroupVipService {

    /**
     * 保存
     */
    String save(GroupVip groupVip);

    /**
     * 更新
     */
    void update(GroupVip groupVip);

    /**
     * 分页查询
     */
    PageVo pageQuery(GroupVipBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<GroupVipVo> query(GroupVipBo bo);

    /**
     * 根据ID查询对象的信息
     */
    GroupVipVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);

    /**
     * 发送短信（会自动设置条件：未发送的，已经设置为返佣的）
     * 查询所有符合条件的，然后发送短信
     */
    int sendSms(GroupVipBo bo);

    /**
     * 设置返佣
     */
    void setBonus(GroupVipBo bo);
}
