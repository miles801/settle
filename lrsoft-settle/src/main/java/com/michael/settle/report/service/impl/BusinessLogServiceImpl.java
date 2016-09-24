package com.michael.settle.report.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.report.bo.BusinessLogBo;
import com.michael.settle.report.dao.BusinessLogDao;
import com.michael.settle.report.domain.BusinessLog;
import com.michael.settle.report.service.BusinessLogService;
import com.michael.settle.report.vo.BusinessLogVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("businessLogService")
public class BusinessLogServiceImpl implements BusinessLogService, BeanWrapCallback<BusinessLog, BusinessLogVo> {
    @Resource
    private BusinessLogDao businessLogDao;

    @Override
    public String save(BusinessLog businessLog) {
        validate(businessLog);
        String id = businessLogDao.save(businessLog);
        return id;
    }

    @Override
    public void update(BusinessLog businessLog) {
        validate(businessLog);
        businessLogDao.update(businessLog);
    }

    private void validate(BusinessLog businessLog) {
        ValidatorUtils.validate(businessLog);
    }

    @Override
    public PageVo pageQuery(BusinessLogBo bo) {
        PageVo vo = new PageVo();
        Long total = businessLogDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<BusinessLog> businessLogList = businessLogDao.pageQuery(bo);
        List<BusinessLogVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(businessLogList, BusinessLogVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public BusinessLogVo findById(String id) {
        BusinessLog businessLog = businessLogDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(businessLog, BusinessLogVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            businessLogDao.deleteById(id);
        }
    }

    @Override
    public List<BusinessLogVo> query(BusinessLogBo bo) {
        List<BusinessLog> businessLogList = businessLogDao.query(bo);
        List<BusinessLogVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(businessLogList, BusinessLogVo.class);
        return vos;
    }


    @Override
    public void doCallback(BusinessLog businessLog, BusinessLogVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 文交所
        vo.setCompanyName(container.getSystemName("VIP_COMPANY", businessLog.getCompany()));

    }
}
