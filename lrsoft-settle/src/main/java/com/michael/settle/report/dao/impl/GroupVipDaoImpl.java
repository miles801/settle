package com.michael.settle.report.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.report.bo.GroupVipBo;
import com.michael.settle.report.dao.GroupVipDao;
import com.michael.settle.report.domain.GroupVip;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("groupVipDao")
public class GroupVipDaoImpl extends HibernateDaoHelper implements GroupVipDao {

    @Override
    public String save(GroupVip groupVip) {
        return (String) getSession().save(groupVip);
    }

    @Override
    public void update(GroupVip groupVip) {
        getSession().update(groupVip);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupVip> query(GroupVipBo bo) {
        Criteria criteria = createCriteria(GroupVip.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupVip> pageQuery(GroupVipBo bo) {
        Criteria criteria = createPagerCriteria(GroupVip.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(GroupVipBo bo) {
        Criteria criteria = createRowCountsCriteria(GroupVip.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + GroupVip.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(GroupVip groupVip) {
        Assert.notNull(groupVip, "要删除的对象不能为空!");
        getSession().delete(groupVip);
    }

    @Override
    public GroupVip findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (GroupVip) getSession().get(GroupVip.class, id);
    }


    private void initCriteria(Criteria criteria, GroupVipBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}