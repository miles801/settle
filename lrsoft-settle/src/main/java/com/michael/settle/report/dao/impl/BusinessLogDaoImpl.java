package com.michael.settle.report.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.report.bo.BusinessLogBo;
import com.michael.settle.report.dao.BusinessLogDao;
import com.michael.settle.report.domain.BusinessLog;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("businessLogDao")
public class BusinessLogDaoImpl extends HibernateDaoHelper implements BusinessLogDao {

    @Override
    public String save(BusinessLog businessLog) {
        return (String) getSession().save(businessLog);
    }

    @Override
    public void update(BusinessLog businessLog) {
        getSession().update(businessLog);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessLog> query(BusinessLogBo bo) {
        Criteria criteria = createCriteria(BusinessLog.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BusinessLog> pageQuery(BusinessLogBo bo) {
        Criteria criteria = createPagerCriteria(BusinessLog.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.desc("groupName"));
        criteria.addOrder(Order.desc("money"));
        return criteria.list();
    }

    @Override
    public Long getTotal(BusinessLogBo bo) {
        Criteria criteria = createRowCountsCriteria(BusinessLog.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + BusinessLog.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(BusinessLog businessLog) {
        Assert.notNull(businessLog, "要删除的对象不能为空!");
        getSession().delete(businessLog);
    }

    @Override
    public BusinessLog findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (BusinessLog) getSession().get(BusinessLog.class, id);
    }


    private void initCriteria(Criteria criteria, BusinessLogBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}