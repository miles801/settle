package com.michael.settle.vip.dao.impl;

import com.michael.core.context.SecurityContext;
import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.core.pager.Pager;
import com.michael.settle.vip.bo.VipBo;
import com.michael.settle.vip.dao.VipDao;
import com.michael.settle.vip.domain.Vip;
import com.michael.utils.collection.CollectionUtils;
import com.michael.utils.string.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;


/**
 * @author Michael
 */
@Repository("vipDao")
public class VipDaoImpl extends HibernateDaoHelper implements VipDao {

    @Override
    public String save(Vip vip) {
        return (String) getSession().save(vip);
    }

    @Override
    public void update(Vip vip) {
        getSession().update(vip);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vip> query(VipBo bo) {
        Criteria criteria = createCriteria(Vip.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vip> pageQuery(VipBo bo) {
        Criteria criteria = createPagerCriteria(Vip.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.asc("company"));
        criteria.addOrder(Order.asc("code"));
        return criteria.list();
    }

    @Override
    public Long getTotal(VipBo bo) {
        Criteria criteria = createRowCountsCriteria(Vip.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Vip.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Vip vip) {
        Assert.notNull(vip, "要删除的对象不能为空!");
        getSession().delete(vip);
    }

    @Override
    public Vip findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Vip) getSession().get(Vip.class, id);
    }

    @Override
    public boolean hasCode(String code, String id) {
        Assert.hasText(code, "查询失败!编号不能为空!");
        Criteria criteria = createRowCountsCriteria(Vip.class)
                .add(Restrictions.eq("code", code))
                .add(Restrictions.eq("orgId", SecurityContext.getOrgId()));
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.ne("id", id));
        }
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public void clear(String empId) {
        Assert.hasText(empId, "操作失败!用户ID不能为空!");
        getSession().createQuery("delete from " + Vip.class.getName() + " v where v.creatorId=?")
                .setParameter(0, empId)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> sqlQuery(String sql, List<Object> params) {
        Assert.hasText(sql, "查询失败!SQL不能为没空!");
        Query query = getSession().createSQLQuery(sql);
        if (CollectionUtils.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (Pager.getStart() != null) {
            query.setFirstResult(Pager.getStart());
        }
        if (Pager.getLimit() != null) {
            query.setMaxResults(Pager.getLimit());
        }
        return query.list();
    }

    @Override
    public String findName(String vipCode) {
        Assert.hasText(vipCode, "查询失败!编号不能为空!");
        return (String) getSession().createQuery("select v.name from " + Vip.class.getName() + " v where v.code=?")
                .setParameter(0, vipCode)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public Vip findByCode(String vipCode) {
        Assert.hasText(vipCode, "查询失败!编号不能为空!");
        return (Vip) createCriteria(Vip.class)
                .add(Restrictions.eq("code", vipCode))
                .setMaxResults(1)
                .uniqueResult();
    }

    private void initCriteria(Criteria criteria, VipBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}