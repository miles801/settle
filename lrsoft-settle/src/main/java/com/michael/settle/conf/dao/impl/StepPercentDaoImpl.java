package com.michael.settle.conf.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.conf.bo.StepPercentBo;
import com.michael.settle.conf.dao.StepPercentDao;
import com.michael.settle.conf.domain.StepPercent;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("stepPercentDao")
public class StepPercentDaoImpl extends HibernateDaoHelper implements StepPercentDao {

    @Override
    public String save(StepPercent stepPercent) {
        return (String) getSession().save(stepPercent);
    }

    @Override
    public void update(StepPercent stepPercent) {
        getSession().update(stepPercent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StepPercent> query(StepPercentBo bo) {
        Criteria criteria = createCriteria(StepPercent.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StepPercent> pageQuery(StepPercentBo bo) {
        Criteria criteria = createPagerCriteria(StepPercent.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(StepPercentBo bo) {
        Criteria criteria = createRowCountsCriteria(StepPercent.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + StepPercent.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(StepPercent stepPercent) {
        Assert.notNull(stepPercent, "要删除的对象不能为空!");
        getSession().delete(stepPercent);
    }

    @Override
    public StepPercent findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (StepPercent) getSession().get(StepPercent.class, id);
    }

    @Override
    public Double getPercent(String company, Double value) {
        Assert.hasText(company, "查询失败!文交所不能为空!");
        Assert.notNull(value, "查询失败!值不能为空!");
        return (Double) getSession().createQuery(" select sp.percent from " + StepPercent.class.getName() + " sp where sp.company=? and sp.minValue<? and sp.maxValue>?")
                .setParameter(0, company)
                .setParameter(1, value)
                .setParameter(2, value)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StepPercent> queryByCompany(String company) {
        Assert.hasText(company, "查询失败!文交所不能为空!");
        return createCriteria(StepPercent.class)
                .add(Restrictions.eq("company", company))
                .list();
    }

    private void initCriteria(Criteria criteria, StepPercentBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}