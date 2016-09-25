package com.michael.settle.report.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.report.bo.GroupVipBo;
import com.michael.settle.report.dao.GroupVipDao;
import com.michael.settle.report.domain.GroupVip;
import com.michael.settle.report.service.GroupVipService;
import com.michael.settle.report.vo.GroupVipVo;
import com.michael.settle.vip.service.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("groupVipService")
public class GroupVipServiceImpl implements GroupVipService, BeanWrapCallback<GroupVip, GroupVipVo> {
    @Resource
    private GroupVipDao groupVipDao;

    @Override
    public String save(GroupVip groupVip) {
        validate(groupVip);
        String id = groupVipDao.save(groupVip);
        return id;
    }

    @Override
    public void update(GroupVip groupVip) {
        validate(groupVip);
        groupVipDao.update(groupVip);
    }

    private void validate(GroupVip groupVip) {
        ValidatorUtils.validate(groupVip);
    }

    @Override
    public PageVo pageQuery(GroupVipBo bo) {
        PageVo vo = new PageVo();
        Long total = groupVipDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<GroupVip> groupVipList = groupVipDao.pageQuery(bo);
        List<GroupVipVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(groupVipList, GroupVipVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public GroupVipVo findById(String id) {
        GroupVip groupVip = groupVipDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(groupVip, GroupVipVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            groupVipDao.deleteById(id);
        }
    }

    @Override
    public List<GroupVipVo> query(GroupVipBo bo) {
        List<GroupVip> groupVipList = groupVipDao.query(bo);
        List<GroupVipVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(groupVipList, GroupVipVo.class);
        return vos;
    }


    @Override
    public void doCallback(GroupVip groupVip, GroupVipVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 文交所
        vo.setCompanyName(container.getSystemName(Params.COMPANY, groupVip.getCompany()));

    }
}
