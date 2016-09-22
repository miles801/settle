package com.michael.settle.sms.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.sms.bo.SmsSendBo;
import com.michael.settle.sms.dao.SmsDao;
import com.michael.settle.sms.dao.SmsSendDao;
import com.michael.settle.sms.domain.Sms;
import com.michael.settle.sms.domain.SmsSend;
import com.michael.settle.sms.service.SmsSendService;
import com.michael.settle.sms.vo.SmsSendVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("smsSendService")
public class SmsSendServiceImpl implements SmsSendService, BeanWrapCallback<SmsSend, SmsSendVo> {
    @Resource
    private SmsSendDao smsSendDao;

    @Resource
    private SmsDao smsDao;

    @Override
    public synchronized String save(SmsSend smsSend) {
        validate(smsSend);
        String id = smsSendDao.save(smsSend);

        // 短信总条数-1
        Sms sms = smsDao.get();
        Assert.notNull(sms, "短信功能已经禁用!");
        Assert.isTrue(!sms.getDeleted(), "短信功能已经禁用!");
        Assert.isTrue(sms.getCounts() > 0, "短信条数已经用完!请联系管理员充值!");
        sms.setCounts(sms.getCounts() - 1);
        return id;
    }

    @Override
    public void update(SmsSend smsSend) {
        Assert.isTrue(false, "不支持该方法!");
    }

    private void validate(SmsSend smsSend) {
        ValidatorUtils.validate(smsSend);
    }

    @Override
    public PageVo pageQuery(SmsSendBo bo) {
        PageVo vo = new PageVo();
        Long total = smsSendDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<SmsSend> smsSendList = smsSendDao.pageQuery(bo);
        List<SmsSendVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(smsSendList, SmsSendVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public SmsSendVo findById(String id) {
        SmsSend smsSend = smsSendDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(smsSend, SmsSendVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        Assert.isTrue(false, "不支持该方法!");
    }

    @Override
    public List<SmsSendVo> query(SmsSendBo bo) {
        List<SmsSend> smsSendList = smsSendDao.query(bo);
        List<SmsSendVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(smsSendList, SmsSendVo.class);
        return vos;
    }


    @Override
    public void doCallback(SmsSend smsSend, SmsSendVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

    }
}
