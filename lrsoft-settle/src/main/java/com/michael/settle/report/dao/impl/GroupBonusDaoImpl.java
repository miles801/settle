package com.michael.settle.report.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.report.bo.GroupBonusBo;
import com.michael.settle.report.dao.GroupBonusDao;
import com.michael.settle.report.domain.GroupBonus;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("groupBonusDao")
public class GroupBonusDaoImpl extends HibernateDaoHelper implements GroupBonusDao {

    @Override
    public String save(GroupBonus groupBonus) {
        return (String) getSession().save(groupBonus);
    }

    @Override
    public void update(GroupBonus groupBonus) {
        getSession().update(groupBonus);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupBonus> query(GroupBonusBo bo) {
        Criteria criteria = createCriteria(GroupBonus.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupBonus> pageQuery(GroupBonusBo bo) {
        Criteria criteria = createPagerCriteria(GroupBonus.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(GroupBonusBo bo) {
        Criteria criteria = createRowCountsCriteria(GroupBonus.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + GroupBonus.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(GroupBonus groupBonus) {
        Assert.notNull(groupBonus, "要删除的对象不能为空!");
        getSession().delete(groupBonus);
    }

    @Override
    public GroupBonus findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (GroupBonus) getSession().get(GroupBonus.class, id);
    }


    private void initCriteria(Criteria criteria, GroupBonusBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}