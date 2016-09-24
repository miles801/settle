package com.michael.settle.report.service.impl;

import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.settle.report.bo.GroupBonusBo;
import com.michael.settle.report.dao.GroupBonusDao;
import com.michael.settle.report.domain.GroupBonus;
import com.michael.settle.report.service.GroupBonusService;
import com.michael.settle.report.vo.GroupBonusVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("groupBonusService")
public class GroupBonusServiceImpl implements GroupBonusService, BeanWrapCallback<GroupBonus, GroupBonusVo> {
    @Resource
    private GroupBonusDao groupBonusDao;

    @Override
    public String save(GroupBonus groupBonus) {
        validate(groupBonus);
        String id = groupBonusDao.save(groupBonus);
        return id;
    }

    @Override
    public void update(GroupBonus groupBonus) {
        validate(groupBonus);
        groupBonusDao.update(groupBonus);
    }

    private void validate(GroupBonus groupBonus) {
        ValidatorUtils.validate(groupBonus);
    }

    @Override
    public PageVo pageQuery(GroupBonusBo bo) {
        PageVo vo = new PageVo();
        Long total = groupBonusDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<GroupBonus> groupBonusList = groupBonusDao.pageQuery(bo);
        List<GroupBonusVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(groupBonusList, GroupBonusVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public GroupBonusVo findById(String id) {
        GroupBonus groupBonus = groupBonusDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(groupBonus, GroupBonusVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            groupBonusDao.deleteById(id);
        }
    }

    @Override
    public List<GroupBonusVo> query(GroupBonusBo bo) {
        List<GroupBonus> groupBonusList = groupBonusDao.query(bo);
        List<GroupBonusVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(groupBonusList, GroupBonusVo.class);
        return vos;
    }


    @Override
    public void doCallback(GroupBonus groupBonus, GroupBonusVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 所属文交所
        vo.setCompanyName(container.getSystemName("VIP_COMPANY", groupBonus.getCompany()));

    }
}
