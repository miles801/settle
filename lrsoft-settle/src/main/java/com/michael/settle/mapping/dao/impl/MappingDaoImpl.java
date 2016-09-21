package com.michael.settle.mapping.dao.impl;

import com.michael.core.hibernate.HibernateDaoHelper;
import com.michael.core.hibernate.criteria.CriteriaUtils;
import com.michael.settle.mapping.bo.MappingBo;
import com.michael.settle.mapping.dao.MappingDao;
import com.michael.settle.mapping.domain.Mapping;
import com.michael.utils.string.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("mappingDao")
public class MappingDaoImpl extends HibernateDaoHelper implements MappingDao {

    @Override
    public String save(Mapping mapping) {
        return (String) getSession().save(mapping);
    }

    @Override
    public void update(Mapping mapping) {
        getSession().update(mapping);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Mapping> query(MappingBo bo) {
        Criteria criteria = createCriteria(Mapping.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Mapping> pageQuery(MappingBo bo) {
        Criteria criteria = createPagerCriteria(Mapping.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(MappingBo bo) {
        Criteria criteria = createRowCountsCriteria(Mapping.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Mapping.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(Mapping mapping) {
        Assert.notNull(mapping, "要删除的对象不能为空!");
        getSession().delete(mapping);
    }

    @Override
    public Mapping findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (Mapping) getSession().get(Mapping.class, id);
    }

    @Override
    public boolean exist(String company, String name, String id) {
        Assert.hasText(company, "查询失败!文交所不能为空!");
        Assert.hasText(name, "查询失败!表不能为空!");
        Criteria criteria = createRowCountsCriteria(Mapping.class)
                .add(Restrictions.eq("company", company))
                .add(Restrictions.eq("name", name));
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.ne("id", id));
        }
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public String getMapping(String company, String name) {
        Assert.hasText(company, "查询失败!文交所不能为空!");
        Assert.hasText(name, "查询失败!表不能为空!");
        return (String) createCriteria(Mapping.class)
                .setProjection(Projections.property("content"))
                .add(Restrictions.eq("company", company))
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    private void initCriteria(Criteria criteria, MappingBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}