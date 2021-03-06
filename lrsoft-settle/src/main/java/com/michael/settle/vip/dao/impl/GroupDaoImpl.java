package com.michael.settle.vip.dao.impl;

import com.michael.core.context.SecurityContext;
import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.vip.bo.GroupBo;
import com.michael.settle.vip.dao.GroupDao;
import com.michael.settle.vip.domain.Group;
import com.michael.utils.string.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("groupDao")
public class GroupDaoImpl extends HibernateDaoHelper implements GroupDao {

    @Override
    public String save(Group group) {
        return (String) getSession().save(group);
    }

    @Override
    public void update(Group group) {
        getSession().update(group);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> query(GroupBo bo) {
        Criteria criteria = createCriteria(Group.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Group> pageQuery(GroupBo bo) {
        Criteria criteria = createPagerCriteria(Group.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.asc("company"));
        criteria.addOrder(Order.asc("code"));
        return criteria.list();
    }

    @Override
    public Long getTotal(GroupBo bo) {
        Criteria criteria = createRowCountsCriteria(Group.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Group.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Group group) {
        Assert.notNull(group, "要删除的对象不能为空!");
        getSession().delete(group);
    }

    @Override
    public Group findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Group) getSession().get(Group.class, id);
    }

    @Override
    public boolean hasCode(String code, String id) {
        Assert.hasText(code, "查询失败!编号不能为空!");
        Criteria criteria = createRowCountsCriteria(Group.class)
                .add(Restrictions.eq("orgId", SecurityContext.getOrgId()))
                .add(Restrictions.eq("code", code));
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.ne("id", id));
        }
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public Group findByCode(String code) {
        Assert.hasText(code, "查询失败!团队编号不能为空!");
        return (Group) createCriteria(Group.class)
                .add(Restrictions.eq("code", code))
                .add(Restrictions.eq("orgId", SecurityContext.getOrgId()))
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public String findCode(String company, String groupName) {
        Assert.hasText(company, "查询失败!文交所编号不能为空!");
        Assert.hasText(groupName, "查询失败!团队名称不能为空!");
        return (String) createCriteria(Group.class)
                .setProjection(Projections.property("code"))
                .add(Restrictions.eq("company", company))
                .add(Restrictions.eq("name", groupName))
                .add(Restrictions.eq("orgId", SecurityContext.getOrgId()))
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public void clear(String empId) {
        Assert.hasText(empId, "操作失败!员工ID不能为空!");
        getSession().createQuery("delete from " + Group.class.getName() + " g where g.creatorId=?")
                .setParameter(0, empId)
                .executeUpdate();
    }

    private void initCriteria(Criteria criteria, GroupBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}