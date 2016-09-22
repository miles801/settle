package com.michael.settle.sms.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.sms.bo.SmsSendBo;
import com.michael.settle.sms.dao.SmsSendDao;
import com.michael.settle.sms.domain.SmsSend;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("smsSendDao")
public class SmsSendDaoImpl extends HibernateDaoHelper implements SmsSendDao {

    @Override
    public String save(SmsSend smsSend) {
        return (String) getSession().save(smsSend);
    }

    @Override
    public void update(SmsSend smsSend) {
        getSession().update(smsSend);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SmsSend> query(SmsSendBo bo) {
        Criteria criteria = createCriteria(SmsSend.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SmsSend> pageQuery(SmsSendBo bo) {
        Criteria criteria = createPagerCriteria(SmsSend.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SmsSendBo bo) {
        Criteria criteria = createRowCountsCriteria(SmsSend.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + SmsSend.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(SmsSend smsSend) {
        Assert.notNull(smsSend, "要删除的对象不能为空!");
        getSession().delete(smsSend);
    }

    @Override
    public SmsSend findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (SmsSend) getSession().get(SmsSend.class, id);
    }


    private void initCriteria(Criteria criteria, SmsSendBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}