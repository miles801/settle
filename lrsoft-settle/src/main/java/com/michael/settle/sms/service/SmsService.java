package com.michael.settle.sms.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.sms.bo.SmsBo;
import com.michael.settle.sms.domain.Sms;
import com.michael.settle.sms.vo.SmsVo;

import java.util.List;

/**
 * @author Michael
 */
public interface SmsService {

    /**
     * 保存
     */
    String init();

    /**
     * 更新
     */
    void update(Sms sms);

    /**
     * 分页查询
     */
    PageVo pageQuery(SmsBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<SmsVo> query(SmsBo bo);

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
    List<Sms> queryValid(SmsBo bo);


    Sms get();
}
