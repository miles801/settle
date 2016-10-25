package com.michael.settle.mapping.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.conf.dao.CompanyDao;
import com.michael.settle.mapping.bo.MappingBo;
import com.michael.settle.mapping.dao.MappingDao;
import com.michael.settle.mapping.domain.Mapping;
import com.michael.settle.mapping.service.MappingService;
import com.michael.settle.mapping.vo.MappingVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("mappingService")
public class MappingServiceImpl implements MappingService, BeanWrapCallback<Mapping, MappingVo> {
    @Resource
    private MappingDao mappingDao;

    @Resource
    private CompanyDao companyDao;

    @Override
    public String save(Mapping mapping) {
        mapping.setDeleted(false);
        validate(mapping);
        String id = mappingDao.save(mapping);
        return id;
    }

    @Override
    public void update(Mapping mapping) {
        validate(mapping);
        mappingDao.update(mapping);
    }

    private void validate(Mapping mapping) {
        ValidatorUtils.validate(mapping);

        // 同一个文交所，同一个表不能有重复项
        boolean exist = mappingDao.exist(mapping.getCompany(), mapping.getName(), mapping.getId());
        Assert.isTrue(!exist, "操作失败!该文教所已经配置了表格映射!请不要重复创建!");
    }

    @Override
    public PageVo pageQuery(MappingBo bo) {
        PageVo vo = new PageVo();
        Long total = mappingDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Mapping> mappingList = mappingDao.pageQuery(bo);
        List<MappingVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(mappingList, MappingVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public MappingVo findById(String id) {
        Mapping mapping = mappingDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(mapping, MappingVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            mappingDao.deleteById(id);
        }
    }

    @Override
    public List<MappingVo> query(MappingBo bo) {
        List<Mapping> mappingList = mappingDao.query(bo);
        List<MappingVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(mappingList, MappingVo.class);
        return vos;
    }

    @Override
    public void enable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Mapping mapping = mappingDao.findById(id);
            Assert.notNull(mapping, "启用失败!数据不存在，请刷新后重试!");
            mapping.setDeleted(false);
        }
    }

    @Override
    public void disable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Mapping mapping = mappingDao.findById(id);
            Assert.notNull(mapping, "启用失败!数据不存在，请刷新后重试!");
            mapping.setDeleted(true);
        }
    }

    @Override
    public List<Mapping> queryValid(MappingBo bo) {
        if (bo == null) {
            bo = new MappingBo();
        }
        bo.setDeleted(false);
        return mappingDao.query(bo);
    }


    @Override
    public void doCallback(Mapping mapping, MappingVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        vo.setCompanyName(companyDao.getName(mapping.getCompany()));

        // 表名称
        vo.setNameName(container.getSystemName("TABLE_NAME", mapping.getName()));

    }
}
