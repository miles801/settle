package com.michael.settle.sms.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.sms.bo.SmsBuyBo;
import com.michael.settle.sms.domain.SmsBuy;
import com.michael.settle.sms.vo.SmsBuyVo;

import java.util.List;

/**
 * @author Michael
 */
public interface SmsBuyService {

    /**
     * 保存
     */
    String save(SmsBuy smsBuy);

    /**
     * 更新
     */
    void update(SmsBuy smsBuy);

    /**
     * 分页查询
     */
    PageVo pageQuery(SmsBuyBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<SmsBuyVo> query(SmsBuyBo bo);

    /**
     * 根据ID查询对象的信息
     */
    SmsBuyVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


}
