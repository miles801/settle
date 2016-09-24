package com.michael.settle.conf.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.conf.bo.CompanyConfBo;
import com.michael.settle.conf.dao.CompanyConfDao;
import com.michael.settle.conf.domain.CompanyConf;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("companyConfDao")
public class CompanyConfDaoImpl extends HibernateDaoHelper implements CompanyConfDao {

    @Override
    public String save(CompanyConf companyConf) {
        return (String) getSession().save(companyConf);
    }

    @Override
    public void update(CompanyConf companyConf) {
        getSession().update(companyConf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CompanyConf> query(CompanyConfBo bo) {
        Criteria criteria = createCriteria(CompanyConf.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CompanyConf> pageQuery(CompanyConfBo bo) {
        Criteria criteria = createPagerCriteria(CompanyConf.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(CompanyConfBo bo) {
        Criteria criteria = createRowCountsCriteria(CompanyConf.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + CompanyConf.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(CompanyConf companyConf) {
        Assert.notNull(companyConf, "要删除的对象不能为空!");
        getSession().delete(companyConf);
    }

    @Override
    public CompanyConf findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (CompanyConf) getSession().get(CompanyConf.class, id);
    }

    @Override
    public CompanyConf findByCompany(String company) {
        Assert.hasText(company, "文交所编号不能为空!");
        return (CompanyConf) createCriteria(CompanyConf.class)
                .add(Restrictions.eq("company", company))
                .uniqueResult();
    }

    private void initCriteria(Criteria criteria, CompanyConfBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}