package com.michael.settle.report.service.impl;

import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.HibernateUtils;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.Order;
import com.michael.core.pager.PageVo;
import com.michael.core.pager.Pager;
import com.michael.settle.conf.service.BonusUtils;
import com.michael.settle.report.bo.GroupVipBo;
import com.michael.settle.report.dao.GroupVipDao;
import com.michael.settle.report.domain.GroupVip;
import com.michael.settle.report.service.GroupVipService;
import com.michael.settle.report.vo.GroupVipVo;
import com.michael.settle.vip.bo.ContactBo;
import com.michael.settle.vip.dao.ContactDao;
import com.michael.settle.vip.dao.VipDao;
import com.michael.settle.vip.domain.Contact;
import com.michael.utils.string.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Michael
 */
@Service("groupVipService")
public class GroupVipServiceImpl implements GroupVipService, BeanWrapCallback<GroupVip, GroupVipVo> {
    @Resource
    private GroupVipDao groupVipDao;

    @Resource
    private ContactDao contactDao;

    @Resource
    private VipDao vipDao;

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
    public synchronized int sendSms(GroupVipBo bo) {
        if (bo == null) {
            bo = new GroupVipBo();
        }
        bo.setSendSms(false);
        bo.setBonus(true);
        List<GroupVip> data = groupVipDao.query(bo);
        Assert.notEmpty(data, "短信发送失败!没有需要发送的数据!");
        int i = 0;
        // 发送短信
        DecimalFormat format = new DecimalFormat("##,###.00#");
        // 查询通讯录
        Map<String, String[]> mobileMap = queryMobileMap(bo.getCompany());
        for (GroupVip vip : data) {
            try (Session session = HibernateUtils.openSession()) {
                session.beginTransaction();
                Map<String, String> map = new HashMap<>();
                map.put("groupName", vip.getGroupName());
                map.put("month", vip.getOccurDate().getMonth() + "");
                map.put("mondy", format.format(vip.getPayMoney()));
                final String[] info = mobileMap.get(vip.getGroupCode());
                if (info == null || info[0] == null) {
                    vip.setErrorMsg("电话号码为空!");
                } else {
                    String mobile = info[0];
                    String account = info[1];
                    if (account != null && account.length() > 4) {
                        account = account.substring(account.length() - 4);
                    }
                    map.put("account", account);
                    String errorMsg = BonusUtils.sendSms(mobile, map);
                    vip.setSmsSendDate(new Date());
                    if (StringUtils.isEmpty(errorMsg)) {
                        vip.setSendSms(true);
                        vip.setErrorMsg(null);
                        i++;
                    } else {
                        vip.setSmsContent("");
                        vip.setErrorMsg(errorMsg);
                    }
                }
                session.update(vip);
                session.getTransaction().commit();
                session.clear();
            }
        }
        return i;
    }

    private Map<String, String[]> queryMobileMap(String company) {
        Map<String, String[]> map = new HashMap<>();
        ContactBo bo = new ContactBo();
        bo.setCompany(company);
        bo.setDeleted(false);
        List<Contact> contacts = contactDao.query(bo);
        if (contacts != null) {
            for (Contact c : contacts) {
                String[] arr = new String[2];
                arr[0] = c.getMobile();
                arr[1] = c.getBankAccount();
                map.put(c.getGroupId(), arr);
            }
        }
        return map;
    }

    @Override
    public void setBonus(GroupVipBo bo) {
        if (bo == null) {
            bo = new GroupVipBo();
        }
        bo.setSendSms(false);
        bo.setBonus(false);
        List<GroupVip> data = groupVipDao.query(bo);
        if (data != null) {
            for (GroupVip vip : data) {
                vip.setBonus(true);
            }
        }
    }

    @Override
    public List<Map<String, Object>> analysis1(final String company) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String sql = "select t1.company company, t1.groupName groupName," +
                "t2.assignCounts-t1.assignCounts assignCounts, " +
                "t2.businessCounts-t1.businessCounts businessCounts, " +
                "t2.payMoney-t1.payMoney payMoney " +
                "from settle_group_vip t1 " +
                " join settle_group_vip t2 on month(t1.occurDate)+1  = month(t2.occurDate) " +
                " where month(t1.occurDate)=? and year(t1.occurDate)=? and t1.company=? " +
                " group by t1.company, t1.groupName ";
        if (Pager.getOrder() != null && Pager.getOrder().hasNext()) {
            Order order = Pager.getOrder().next();
            sql += " order by " + order.getName() + (order.isReverse() ? " desc " : " asc ");
        } else {
            sql += " order by assignCounts desc";
        }
        List<Map<String, Object>> o = vipDao.sqlQuery(sql, new ArrayList<Object>() {{
            add(calendar.get(Calendar.MONTH));
            add(calendar.get(Calendar.YEAR));
            add(company);
        }});
        return o;
    }

    @Override
    public List<Map<String, Object>> analysis2(final String company) {
        return null;
    }

    @Override
    public List<Map<String, Object>> analysis3(final String groupName) {
        return null;
    }

    @Override
    public void doCallback(GroupVip groupVip, GroupVipVo vo) {

    }
}
