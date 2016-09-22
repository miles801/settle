package com.michael.settle.sms.dao;

import com.michael.settle.sms.bo.SmsBuyBo;
import com.michael.settle.sms.domain.SmsBuy;

import java.util.List;

/**
 * @author Michael
 */
public interface SmsBuyDao {

    String save(SmsBuy smsBuy);

    void update(SmsBuy smsBuy);

    /**
     * 高级查询接口，不使用分页
     */
    List<SmsBuy> query(SmsBuyBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<SmsBuy> pageQuery(SmsBuyBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(SmsBuyBo bo);

    SmsBuy findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(SmsBuy smsBuy);

}
