package com.michael.settle.vip.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.vip.bo.BusinessBo;
import com.michael.settle.vip.dao.BusinessDao;
import com.michael.settle.vip.domain.Business;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("businessDao")
public class BusinessDaoImpl extends HibernateDaoHelper implements BusinessDao {

    @Override
    public String save(Business business) {
        return (String) getSession().save(business);
    }

    @Override
    public void update(Business business) {
        getSession().update(business);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Business> query(BusinessBo bo) {
        Criteria criteria = createCriteria(Business.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Business> pageQuery(BusinessBo bo) {
        Criteria criteria = createPagerCriteria(Business.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(BusinessBo bo) {
        Criteria criteria = createRowCountsCriteria(Business.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Business.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Business business) {
        Assert.notNull(business, "要删除的对象不能为空!");
        getSession().delete(business);
    }

    @Override
    public Business findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Business) getSession().get(Business.class, id);
    }

    @Override
    public void clear(String empId) {
        Assert.hasText(empId, "操作失败!员工ID不能为空!");
        getSession().createQuery("delete from " + Business.class.getName() + " g where g.creatorId=?")
                .setParameter(0, empId)
                .executeUpdate();
    }

    private void initCriteria(Criteria criteria, BusinessBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}