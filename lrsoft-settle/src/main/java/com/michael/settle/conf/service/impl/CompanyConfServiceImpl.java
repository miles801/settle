package com.michael.settle.conf.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.conf.bo.CompanyConfBo;
import com.michael.settle.conf.dao.CompanyConfDao;
import com.michael.settle.conf.domain.CompanyConf;
import com.michael.settle.conf.service.CompanyConfService;
import com.michael.settle.conf.vo.CompanyConfVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("companyConfService")
public class CompanyConfServiceImpl implements CompanyConfService, BeanWrapCallback<CompanyConf, CompanyConfVo> {
    @Resource
    private CompanyConfDao companyConfDao;

    @Override
    public String save(CompanyConf companyConf) {
        validate(companyConf);
        CompanyConf cc = companyConfDao.findByCompany(companyConf.getCompany());
        Assert.isNull(cc, "保存失败!该文交所已经配置了相关的信息!");
        String id = companyConfDao.save(companyConf);
        return id;
    }

    @Override
    public void update(CompanyConf companyConf) {
        validate(companyConf);
        companyConfDao.update(companyConf);
    }

    private void validate(CompanyConf companyConf) {
        ValidatorUtils.validate(companyConf);

    }

    @Override
    public PageVo pageQuery(CompanyConfBo bo) {
        PageVo vo = new PageVo();
        Long total = companyConfDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<CompanyConf> companyConfList = companyConfDao.pageQuery(bo);
        List<CompanyConfVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(companyConfList, CompanyConfVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public CompanyConfVo findById(String id) {
        CompanyConf companyConf = companyConfDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(companyConf, CompanyConfVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            companyConfDao.deleteById(id);
        }
    }

    @Override
    public List<CompanyConfVo> query(CompanyConfBo bo) {
        List<CompanyConf> companyConfList = companyConfDao.query(bo);
        List<CompanyConfVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(companyConfList, CompanyConfVo.class);
        return vos;
    }


    @Override
    public void doCallback(CompanyConf companyConf, CompanyConfVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 文交所
        vo.setCompanyName(container.getSystemName("VIP_COMPANY", companyConf.getCompany()));

    }
}
