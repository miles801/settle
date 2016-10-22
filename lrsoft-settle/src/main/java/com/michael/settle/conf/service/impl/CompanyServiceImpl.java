package com.michael.settle.conf.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.conf.bo.CompanyBo;
import com.michael.settle.conf.dao.CompanyDao;
import com.michael.settle.conf.domain.Company;
import com.michael.settle.conf.service.CompanyService;
import com.michael.settle.conf.vo.CompanyVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService, BeanWrapCallback<Company, CompanyVo> {
    @Resource
    private CompanyDao companyDao;

    @Override
    public String save(Company company) {
        company.setDeleted(false);
        validate(company);
        String id = companyDao.save(company);
        return id;
    }

    @Override
    public void update(Company company) {
        validate(company);
        companyDao.update(company);
    }

    private void validate(Company company) {
        ValidatorUtils.validate(company);
    }

    @Override
    public PageVo pageQuery(CompanyBo bo) {
        PageVo vo = new PageVo();
        Long total = companyDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Company> companyList = companyDao.pageQuery(bo);
        List<CompanyVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(companyList, CompanyVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public CompanyVo findById(String id) {
        Company company = companyDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(company, CompanyVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            companyDao.deleteById(id);
        }
    }

    @Override
    public List<CompanyVo> query(CompanyBo bo) {
        List<Company> companyList = companyDao.query(bo);
        List<CompanyVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(companyList, CompanyVo.class);
        return vos;
    }

    @Override
    public void enable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Company company = companyDao.findById(id);
            Assert.notNull(company, "启用失败!数据不存在，请刷新后重试!");
            company.setDeleted(false);
        }
    }

    @Override
    public void disable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Company company = companyDao.findById(id);
            Assert.notNull(company, "启用失败!数据不存在，请刷新后重试!");
            company.setDeleted(true);
        }
    }

    @Override
    public List<Company> queryValid(CompanyBo bo) {
        if (bo == null) {
            bo = new CompanyBo();
        }
        bo.setDeleted(false);
        return companyDao.query(bo);
    }


    @Override
    public void doCallback(Company company, CompanyVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

    }
}
