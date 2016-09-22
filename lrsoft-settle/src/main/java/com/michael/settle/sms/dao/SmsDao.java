package com.michael.settle.sms.dao;

import com.michael.settle.sms.bo.SmsBo;
import com.michael.settle.sms.domain.Sms;

import java.util.List;

/**
 * @author Michael
 */
public interface SmsDao {

    String save(Sms sms);

    void update(Sms sms);

    /**
     * 高级查询接口，不使用分页
     */
    List<Sms> query(SmsBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Sms> pageQuery(SmsBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(SmsBo bo);

    Sms findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Sms sms);

    /**
     * 获取唯一的短信配置
     */
    Sms get();

}
