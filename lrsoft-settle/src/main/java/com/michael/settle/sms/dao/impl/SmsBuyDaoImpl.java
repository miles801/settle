package com.michael.settle.sms.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.sms.bo.SmsBuyBo;
import com.michael.settle.sms.dao.SmsBuyDao;
import com.michael.settle.sms.domain.SmsBuy;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("smsBuyDao")
public class SmsBuyDaoImpl extends HibernateDaoHelper implements SmsBuyDao {

    @Override
    public String save(SmsBuy smsBuy) {
        return (String) getSession().save(smsBuy);
    }

    @Override
    public void update(SmsBuy smsBuy) {
        getSession().update(smsBuy);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SmsBuy> query(SmsBuyBo bo) {
        Criteria criteria = createCriteria(SmsBuy.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.desc("occurTime"));
        criteria.addOrder(Order.desc("createdDatetime"));
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SmsBuy> pageQuery(SmsBuyBo bo) {
        Criteria criteria = createPagerCriteria(SmsBuy.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.desc("occurTime"));
        criteria.addOrder(Order.desc("createdDatetime"));
        return criteria.list();
    }

    @Override
    public Long getTotal(SmsBuyBo bo) {
        Criteria criteria = createRowCountsCriteria(SmsBuy.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + SmsBuy.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(SmsBuy smsBuy) {
        Assert.notNull(smsBuy, "要删除的对象不能为空!");
        getSession().delete(smsBuy);
    }

    @Override
    public SmsBuy findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (SmsBuy) getSession().get(SmsBuy.class, id);
    }


    private void initCriteria(Criteria criteria, SmsBuyBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}