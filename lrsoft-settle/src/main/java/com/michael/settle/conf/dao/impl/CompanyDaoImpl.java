package com.michael.settle.conf.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.conf.bo.CompanyBo;
import com.michael.settle.conf.dao.CompanyDao;
import com.michael.settle.conf.domain.Company;
import com.michael.utils.string.StringUtils;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Michael
 */
@Repository("companyDao")
public class CompanyDaoImpl extends HibernateDaoHelper implements CompanyDao {

    // key为ID，value为Name
    private Map<String, String> names = new HashMap<>();

    @Override
    public String save(Company company) {
        return (String) getSession().save(company);
    }

    @Override
    public void update(Company company) {
        getSession().update(company);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Company> query(CompanyBo bo) {
        Criteria criteria = createCriteria(Company.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Company> pageQuery(CompanyBo bo) {
        Criteria criteria = createPagerCriteria(Company.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(CompanyBo bo) {
        Criteria criteria = createRowCountsCriteria(Company.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Company.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Company company) {
        Assert.notNull(company, "要删除的对象不能为空!");
        getSession().delete(company);
    }

    @Override
    public Company findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Company) getSession().get(Company.class, id);
    }

    @Override
    public String getName(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        String name = names.get(id);
        if (name == null) {
            name = (String) getSession().createQuery(" select c.name from " + Company.class.getName() + " c where c.id=?")
                    .setParameter(0, id)
                    .uniqueResult();
            names.put(id, name);
        }
        return name;
    }

    private void initCriteria(Criteria criteria, CompanyBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}