package com.michael.settle.sms.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.sms.bo.SmsBuyBo;
import com.michael.settle.sms.dao.SmsBuyDao;
import com.michael.settle.sms.dao.SmsDao;
import com.michael.settle.sms.domain.Sms;
import com.michael.settle.sms.domain.SmsBuy;
import com.michael.settle.sms.service.SmsBuyService;
import com.michael.settle.sms.vo.SmsBuyVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("smsBuyService")
public class SmsBuyServiceImpl implements SmsBuyService, BeanWrapCallback<SmsBuy, SmsBuyVo> {
    @Resource
    private SmsBuyDao smsBuyDao;

    @Resource
    private SmsDao smsDao;

    @Override
    public synchronized String save(SmsBuy smsBuy) {
        validate(smsBuy);
        String id = smsBuyDao.save(smsBuy);

        // 短信总条数增加，如果短信配置不存在，则创建一个默认的
        Sms sms = smsDao.get();
        if (sms == null) {
            sms = new Sms();
            sms.setCounts(0);
            sms.setDeleted(false);
            smsDao.save(sms);
        }
        sms.setCounts(sms.getCounts() + smsBuy.getCounts());
        return id;
    }

    @Override
    public void update(SmsBuy smsBuy) {
        Assert.isTrue(false, "不支持该方法!");
    }

    private void validate(SmsBuy smsBuy) {
        ValidatorUtils.validate(smsBuy);
    }

    @Override
    public PageVo pageQuery(SmsBuyBo bo) {
        PageVo vo = new PageVo();
        Long total = smsBuyDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<SmsBuy> smsBuyList = smsBuyDao.pageQuery(bo);
        List<SmsBuyVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(smsBuyList, SmsBuyVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public SmsBuyVo findById(String id) {
        SmsBuy smsBuy = smsBuyDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(smsBuy, SmsBuyVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        Assert.isTrue(false, "不支持该方法!");
    }

    @Override
    public List<SmsBuyVo> query(SmsBuyBo bo) {
        List<SmsBuy> smsBuyList = smsBuyDao.query(bo);
        List<SmsBuyVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(smsBuyList, SmsBuyVo.class);
        return vos;
    }


    @Override
    public void doCallback(SmsBuy smsBuy, SmsBuyVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();
    }
}
