package com.michael.settle.conf.service.impl;

import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.conf.bo.StepPercentBo;
import com.michael.settle.conf.dao.CompanyDao;
import com.michael.settle.conf.dao.StepPercentDao;
import com.michael.settle.conf.domain.StepPercent;
import com.michael.settle.conf.service.StepPercentService;
import com.michael.settle.conf.vo.StepPercentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("stepPercentService")
public class StepPercentServiceImpl implements StepPercentService, BeanWrapCallback<StepPercent, StepPercentVo> {
    @Resource
    private StepPercentDao stepPercentDao;
    @Resource
    private CompanyDao companyDao;

    @Override
    public String save(StepPercent stepPercent) {
        validate(stepPercent);
        String id = stepPercentDao.save(stepPercent);
        return id;
    }

    @Override
    public void update(StepPercent stepPercent) {
        validate(stepPercent);
        stepPercentDao.update(stepPercent);
    }

    private void validate(StepPercent stepPercent) {
        ValidatorUtils.validate(stepPercent);
    }

    @Override
    public PageVo pageQuery(StepPercentBo bo) {
        PageVo vo = new PageVo();
        Long total = stepPercentDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<StepPercent> stepPercentList = stepPercentDao.pageQuery(bo);
        List<StepPercentVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(stepPercentList, StepPercentVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public StepPercentVo findById(String id) {
        StepPercent stepPercent = stepPercentDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(stepPercent, StepPercentVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            stepPercentDao.deleteById(id);
        }
    }

    @Override
    public List<StepPercentVo> query(StepPercentBo bo) {
        List<StepPercent> stepPercentList = stepPercentDao.query(bo);
        List<StepPercentVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(stepPercentList, StepPercentVo.class);
        return vos;
    }

    @Override
    public void doCallback(StepPercent stepPercent, StepPercentVo vo) {
        vo.setCompanyName(companyDao.getName(stepPercent.getCompany()));
    }
}
