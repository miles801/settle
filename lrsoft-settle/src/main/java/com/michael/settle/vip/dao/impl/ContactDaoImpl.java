package com.michael.settle.vip.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.vip.bo.ContactBo;
import com.michael.settle.vip.dao.ContactDao;
import com.michael.settle.vip.domain.Contact;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("contactDao")
public class ContactDaoImpl extends HibernateDaoHelper implements ContactDao {

    @Override
    public String save(Contact contact) {
        return (String) getSession().save(contact);
    }

    @Override
    public void update(Contact contact) {
        getSession().update(contact);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Contact> query(ContactBo bo) {
        Criteria criteria = createCriteria(Contact.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Contact> pageQuery(ContactBo bo) {
        Criteria criteria = createPagerCriteria(Contact.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.asc("company"));
        criteria.addOrder(Order.asc("groupId"));
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }

    @Override
    public Long getTotal(ContactBo bo) {
        Criteria criteria = createRowCountsCriteria(Contact.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Contact.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Contact contact) {
        Assert.notNull(contact, "要删除的对象不能为空!");
        getSession().delete(contact);
    }

    @Override
    public Contact findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Contact) getSession().get(Contact.class, id);
    }


    private void initCriteria(Criteria criteria, ContactBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}