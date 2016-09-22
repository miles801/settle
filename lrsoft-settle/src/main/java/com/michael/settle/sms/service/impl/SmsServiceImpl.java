package com.michael.settle.sms.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.sms.bo.SmsBo;
import com.michael.settle.sms.dao.SmsDao;
import com.michael.settle.sms.domain.Sms;
import com.michael.settle.sms.service.SmsService;
import com.michael.settle.sms.vo.SmsVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService, BeanWrapCallback<Sms, SmsVo> {
    @Resource
    private SmsDao smsDao;

    @Override
    public String init() {
        Assert.isNull(smsDao.get(), "初始化失败!短信功能已经初始化，请勿重复操作!");
        Sms sms = new Sms();
        sms.setDeleted(false);
        sms.setCounts(0);
        String id = smsDao.save(sms);
        return id;
    }

    @Override
    public void update(Sms sms) {
        Assert.isTrue(false, "不支持该方法!");
    }

    private void validate(Sms sms) {
        ValidatorUtils.validate(sms);
    }

    @Override
    public PageVo pageQuery(SmsBo bo) {
        PageVo vo = new PageVo();
        Long total = smsDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Sms> smsList = smsDao.pageQuery(bo);
        List<SmsVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(smsList, SmsVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public void deleteByIds(String[] ids) {
        Assert.isTrue(false, "不支持该方法!");
    }

    @Override
    public List<SmsVo> query(SmsBo bo) {
        List<Sms> smsList = smsDao.query(bo);
        List<SmsVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(smsList, SmsVo.class);
        return vos;
    }

    @Override
    public void enable(String[] ids) {
        Sms sms = smsDao.get();
        Assert.notNull(sms, "启用失败!数据不存在，请刷新后重试!");
        sms.setDeleted(false);
    }

    @Override
    public void disable(String[] ids) {
        Sms sms = smsDao.get();
        Assert.notNull(sms, "启用失败!数据不存在，请刷新后重试!");
        sms.setDeleted(true);
    }

    @Override
    public List<Sms> queryValid(SmsBo bo) {
        if (bo == null) {
            bo = new SmsBo();
        }
        bo.setDeleted(false);
        return smsDao.query(bo);
    }

    @Override
    public Sms get() {
        return smsDao.get();
    }

    @Override
    public void doCallback(Sms sms, SmsVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

    }
}
