package com.michael.settle.sms.dao;

import com.michael.settle.sms.bo.SmsSendBo;
import com.michael.settle.sms.domain.SmsSend;

import java.util.List;

/**
 * @author Michael
 */
public interface SmsSendDao {

    String save(SmsSend smsSend);

    void update(SmsSend smsSend);

    /**
     * 高级查询接口，不使用分页
     */
    List<SmsSend> query(SmsSendBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<SmsSend> pageQuery(SmsSendBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(SmsSendBo bo);

    SmsSend findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(SmsSend smsSend);

}
