package com.michael.settle.sms.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.sms.bo.SmsBo;
import com.michael.settle.sms.dao.SmsDao;
import com.michael.settle.sms.domain.Sms;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("smsDao")
public class SmsDaoImpl extends HibernateDaoHelper implements SmsDao {

    @Override
    public String save(Sms sms) {
        return (String) getSession().save(sms);
    }

    @Override
    public void update(Sms sms) {
        getSession().update(sms);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Sms> query(SmsBo bo) {
        Criteria criteria = createCriteria(Sms.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Sms> pageQuery(SmsBo bo) {
        Criteria criteria = createPagerCriteria(Sms.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SmsBo bo) {
        Criteria criteria = createRowCountsCriteria(Sms.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Sms.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Sms sms) {
        Assert.notNull(sms, "要删除的对象不能为空!");
        getSession().delete(sms);
    }

    @Override
    public Sms get() {
        return (Sms) createCriteria(Sms.class)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public Sms findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Sms) getSession().get(Sms.class, id);
    }


    private void initCriteria(Criteria criteria, SmsBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}