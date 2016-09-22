package com.michael.settle.sms.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.sms.bo.SmsSendBo;
import com.michael.settle.sms.domain.SmsSend;
import com.michael.settle.sms.vo.SmsSendVo;

import java.util.List;

/**
 * @author Michael
 */
public interface SmsSendService {

    /**
     * 保存
     */
    String save(SmsSend smsSend);

    /**
     * 更新
     */
    void update(SmsSend smsSend);

    /**
     * 分页查询
     */
    PageVo pageQuery(SmsSendBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<SmsSendVo> query(SmsSendBo bo);

    /**
     * 根据ID查询对象的信息
     */
    SmsSendVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


}
