package com.michael.settle.vip.service.impl;

import com.michael.base.attachment.AttachmentProvider;
import com.michael.base.attachment.utils.AttachmentHolder;
import com.michael.base.attachment.vo.AttachmentVo;
import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.SystemContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.context.SecurityContext;
import com.michael.core.hibernate.HibernateUtils;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.Order;
import com.michael.core.pager.PageVo;
import com.michael.core.pager.Pager;
import com.michael.poi.core.Context;
import com.michael.poi.core.Handler;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.core.RuntimeContext;
import com.michael.poi.imp.cfg.ColMapping;
import com.michael.poi.imp.cfg.Configuration;
import com.michael.settle.conf.dao.CompanyConfDao;
import com.michael.settle.conf.dao.CompanyDao;
import com.michael.settle.conf.dao.StepPercentDao;
import com.michael.settle.conf.domain.CompanyConf;
import com.michael.settle.conf.domain.StepPercent;
import com.michael.settle.mapping.dao.MappingDao;
import com.michael.settle.report.dao.BusinessLogDao;
import com.michael.settle.report.dao.GroupBonusDao;
import com.michael.settle.report.dao.GroupVipDao;
import com.michael.settle.report.domain.GroupVip;
import com.michael.settle.vip.bo.VipBo;
import com.michael.settle.vip.dao.BusinessDao;
import com.michael.settle.vip.dao.GroupDao;
import com.michael.settle.vip.dao.VipDao;
import com.michael.settle.vip.domain.Group;
import com.michael.settle.vip.domain.Vip;
import com.michael.settle.vip.dto.VipDTO;
import com.michael.settle.vip.service.Params;
import com.michael.settle.vip.service.VipService;
import com.michael.settle.vip.vo.VipVo;
import com.michael.utils.beans.BeanCopyUtils;
import com.michael.utils.string.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

/**
 * @author Michael
 */
@Service("vipService")
public class VipServiceImpl implements VipService, BeanWrapCallback<Vip, VipVo> {
    @Resource
    private VipDao vipDao;

    @Resource
    private MappingDao mappingDao;

    @Resource
    private GroupVipDao groupVipDao;

    @Resource
    private GroupBonusDao groupBonusDao;

    @Resource
    private CompanyConfDao companyConfDao;

    @Resource
    private StepPercentDao stepPercentDao;

    @Resource
    private BusinessDao businessDao;

    @Resource
    private BusinessLogDao businessLogDao;

    @Resource
    private GroupDao groupDao;

    @Resource
    private CompanyDao companyDao;

    @Override
    public String save(Vip vip) {
        validate(vip);
        String id = vipDao.save(vip);
        return id;
    }

    @Override
    public void update(Vip vip) {
        validate(vip);
        vipDao.update(vip);
    }

    private void validate(Vip vip) {
        ValidatorUtils.validate(vip);

        // 验证重复 - 编号
        boolean hasCode = vipDao.hasCode(vip.getCode(), vip.getId());
        Assert.isTrue(!hasCode, "操作失败!编号[" + vip.getCode() + "]已经存在!");

    }

    @Override
    public PageVo pageQuery(VipBo bo) {
        PageVo vo = new PageVo();
        Long total = vipDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Vip> vipList = vipDao.pageQuery(bo);
        List<VipVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(vipList, VipVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public VipVo findById(String id) {
        Vip vip = vipDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(vip, VipVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            vipDao.deleteById(id);
        }
    }

    @Override
    public List<VipVo> query(VipBo bo) {
        List<Vip> vipList = vipDao.query(bo);
        List<VipVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(vipList, VipVo.class);
        return vos;
    }

    @Override
    public void clear() {
        vipDao.clear(SecurityContext.getEmpId());
    }

    @Override
    public void report() {

        Logger logger = Logger.getLogger(VipServiceImpl.class);
        logger.info("******** 产生报表数据 : start ********");

        logger.info("******** 1. 交易会员 ********");
        Session session = HibernateUtils.getSession(false);
        // 查询当前人所创建的会员数据
        VipBo bo = new VipBo();
        bo.setCreatorId(SecurityContext.getEmpId());
        Long total = vipDao.getTotal(bo);
        Assert.isTrue(total != null && total > 0, "报表生成失败!当前用户还未导入任何会员数据!");

        // 获取统计结果
        String sql = "select t1.*,t2.businessCounts businessCounts ,t2.businessTime businessTime,t3.groupName,t3.money,t3.fee \n" +
                "\tfrom (SELECT v.company company,  v.groupId groupId, count(v.id) total, \n" +
                "\t\tsum(case v.assignStatus when '1' then 1 else 0 end)  assignCounts,\n" +
                "\t\tsum(case v.status when '1' then 1 else 0 end)  normalCounts  FROM " +
                "settle_vip v where v.creatorId=?  group by v.company, v.groupId) \n" +
                "t1 left join (\n" +
                "\t\tselect count(DISTINCT b.vipCode) businessCounts ,b.company company,b.groupCode groupCode,b.businessTime from settle_business b group by b.company, b.groupCode,b.businessTime) \n" +
                "t2 on t1.company=t2.company and  t1.groupId=t2.groupCode\n" +
                "left join (\n" +
                "\t\tSELECT b.company company,b.groupCode groupCode,b.groupName groupName,sum(b.money) money,sum(b.fee) fee FROM settle_business b group by b.company,b.groupCode,b.groupName\n" +
                ") t3 on t2.company=t3.company and t1.groupId=t3.groupCode order by t3.money desc";
        List<Map<String, Object>> o = vipDao.sqlQuery(sql, new ArrayList<Object>() {{
            add(SecurityContext.getEmpId());
        }});

        // 创建团队会员报表数据
        Assert.notEmpty(o, "报表生成失败!没有查询到符合的结果集!");
        int index = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        Date occurDate = calendar.getTime();    // 时间设置为上个月的1号
        final Map<String, CompanyConf> companyMap = new HashMap<>();        // 公式配置
        final Map<String, List<StepPercent>> percentMap = new HashMap<>();  // 阶梯比例
        for (Map<String, Object> foo : o) {
            GroupVip vip = new GroupVip();
            vip.setBonus(false);
            vip.setSendSms(false);
            String company = (String) foo.get("company");
            String companyName = companyDao.getName(company);
            vip.setCompany(company);
            vip.setCompanyName(companyName);
            vip.setGroupCode(foo.get("groupId").toString());
            String groupName = (String) foo.get("groupName");
            if (StringUtils.isEmpty(groupName)) {
                groupName = vip.getGroupCode();
            }
            vip.setGroupName(groupName);
            // 会员数量
            BigInteger vipCounts = (BigInteger) foo.get("total");
            vip.setVipCounts(vipCounts == null ? 0 : Integer.parseInt(vipCounts.toString()));
            // 正常数量
            BigDecimal normalCounts = (BigDecimal) foo.get("normalCounts");
            vip.setNormalCounts(normalCounts == null ? 0 : Integer.parseInt(normalCounts.toString()));

            // 非正常数量
            vip.setOtherCounts(vip.getVipCounts() - vip.getNormalCounts());
            // 签约数量
            BigDecimal assignCounts = (BigDecimal) foo.get("assignCounts");
            vip.setAssignCounts(assignCounts == null ? 0 : Integer.parseInt(assignCounts.toString()));

            // 未签约数量
            vip.setNotAssignCounts(vip.getVipCounts() - vip.getAssignCounts());


            // 有交易的会员数量
            BigInteger businessCounts = (BigInteger) foo.get("businessCounts");
            vip.setBusinessCounts(businessCounts == null ? 0 : Integer.parseInt(businessCounts.toString()));

            // 成交额
            Double totalMoney = (Double) foo.get("money");
            final double money = totalMoney == null ? 0 : totalMoney;
            vip.setTotalMoney(money);

            // 交易时间
            vip.setOccurDate(occurDate);

            if (money > 0) {
                Double p = null;
                Double fee = (Double) foo.get("fee");


                // 交易时间
                Date businessTime = (Date) foo.get("businessTime");
                if (businessTime != null) {
                    occurDate = businessTime;
                    vip.setOccurDate(occurDate);
                }

                vip.setFee(fee);          // 手续费
                CompanyConf conf = companyMap.get(company);
                if (conf == null) {
                    conf = companyConfDao.findByCompany(company);
                    companyMap.put(company, conf);
                }
                Assert.notNull(conf, "报表生成失败!文交所[" + companyName + "]所对应的各项系数未配置!");

                // 标准佣金
                String commission = conf.getCommission();
                Double c = null;
                if (commission.matches("\\d+(\\.\\d+)?")) {
                    c = fee * Double.parseDouble(commission);
                } else if (commission.matches("\\d+/\\d+")) {
                    String[] tmp = commission.split("/");
                    c = fee * Integer.parseInt(tmp[0]) / Integer.parseInt(tmp[1]);
                } else {
                    Assert.isTrue(false, "不合法的标准佣金配置：" + commission);
                }
                vip.setCommission(new BigDecimal(c).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                // 阶梯比例
                List<StepPercent> percents = percentMap.get(company);
                if (percents == null) {
                    percents = stepPercentDao.queryByCompany(company);
                    Assert.notEmpty(percents, "报表生成失败!文交所[" + companyName + "]对应的阶梯比例未配置!");
                    percentMap.put(company, percents);
                }

                int type = percents.get(0).getType();
                double cm = money;
                if (type == 2) {
                    cm = vip.getFee();
                } else if (type == 3) {
                    cm = vip.getCommission();
                }
                for (StepPercent sp : percents) {
                    if (sp.getMinValue() <= cm && sp.getMaxValue() >= cm) {
                        p = sp.getPercent();
                        break;
                    }
                }
                Assert.notNull(p, String.format("报表生成失败!文交所[%s]下团队[%s]的交易额为[%s]，不在阶梯比例范围内!", companyName, groupName, money));
                vip.setStepPercent(p);

                // 含税服务费 = 标准佣金*阶梯比例 (南京使用的是成交额，其他文交所使用手续费）
                vip.setTaxServerFee(new BigDecimal(vip.getCommission() * vip.getStepPercent()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());

                // 支付金额 = 含税服务费*固定比例
                vip.setPercent(conf.getPercent());
                vip.setPayMoney(new BigDecimal(vip.getTaxServerFee() * conf.getPercent()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());

                // 除税支付金额 = 支付金额 * 税率
                vip.setOutofTax(new BigDecimal(vip.getPayMoney() * (1 - conf.getTax())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());

                // 税金 = 支付金额 - 除税支付金额
                vip.setTax(new BigDecimal(vip.getPayMoney() - vip.getOutofTax()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            }


            groupVipDao.save(vip);
            index++;
            if (index % 20 == 0) {
                session.flush();
                session.clear();
            }
        }

        logger.info("******** 产生报表数据 : end ********");
    }

    @Override
    public List<Map<String, Object>> analysis(final Map<String, Object> params) {
        String sql = "SELECT " +
                "v.company,v.companyName ,v.groupCode,v.groupName,v.vipCounts,v.normalCounts,v.assignCounts,b.totalMoney,b.fee,b.commission,b.stepPercent,b.taxServerFee,b.payMoney,b.percent,b.outofTax,b.tax,b.occurDate,b.description " +
                "FROM settle_group_vip v left join " +
                "settle_group_bonus b on v.company=b.company where v.groupCode=b.groupCode and b.occurDate between ? and ? and v.creatorId=? ";
        final String company = (String) params.get("company");
        if (StringUtils.isNotEmpty(company)) {
            sql += " and company=? ";
        }
        if (Pager.getOrder() != null && Pager.getOrder().hasNext()) {
            Order order = Pager.getOrder().next();
            sql += " order by " + order.getName() + (order.isReverse() ? " desc " : " asc ");
        } else {
            sql += " order by totalMoney desc";
        }
        List<Map<String, Object>> o = vipDao.sqlQuery(sql, new ArrayList<Object>() {{
            add(new Date(Long.parseLong(params.get("occurDate1").toString())));
            add(new Date(Long.parseLong(params.get("occurDate2").toString())));
            add(SecurityContext.getEmpId());
            if (StringUtils.isNotEmpty(company)) {
                add(company);
            }
        }});
        return o;
    }

    public void importData(final String company, String[] attachmentIds) {
        Assert.hasText(company, "导入失败!会员文交所不能为空!");
        final Logger logger = Logger.getLogger(VipServiceImpl.class);
        Assert.notEmpty(attachmentIds, "数据导入失败!数据文件不能为空，请重试!");
        final String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.0", "yyyy-MM-dd HH:mm:ss.SSS"};

        // 获取会员映射模板
        String mapping = mappingDao.getMapping(company, "1");
        Assert.hasText(mapping, "导入会员失败!该文交所还未配置映射模板!");
        final Map<String, String> groupMap = new HashMap<>(); // key：groupCode，value：groupName
        for (String id : attachmentIds) {
            AttachmentVo vo = AttachmentProvider.getInfo(id);
            Assert.notNull(vo, "附件已经不存在，请刷新后重试!");
            final File file = AttachmentHolder.newInstance().getTempFile(id);
            logger.info("准备导入数据：" + file.getAbsolutePath());
            logger.info("初始化导入引擎....");
            final long start = System.currentTimeMillis();

            // 初始化引擎
            Configuration configuration = new Configuration();
            configuration.setClazz(VipDTO.class);
            List<ColMapping> mappings = new ArrayList<>();
            for (String map : mapping.split("@")) {
                String values[] = map.split(":");
                ColMapping cp = new ColMapping();
                cp.setColName(values[0]);
                cp.setIndex(Integer.parseInt(values[1]) - 1);
                mappings.add(cp);
            }
            configuration.setMappings(mappings);
            configuration.setStartRow(1);
            String newFilePath = file.getAbsolutePath() + vo.getFileName().substring(vo.getFileName().lastIndexOf(".")); //获取路径
            try {
                FileUtils.copyFile(file, new File(newFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取session
            SessionFactory sessionFactory = (SessionFactory) SystemContainer.getInstance().getBean("sessionFactory");
            final Session session = sessionFactory.getCurrentSession();

            // 获取vip的编号
            @SuppressWarnings("unchecked") List<String> codeList = session.createQuery("select v.code from " + Vip.class.getName() + " v where v.orgId=? and v.company=?")
                    .setParameter(0, SecurityContext.getOrgId())
                    .setParameter(1, company)
                    .list();
            final Set<String> codes = new HashSet<>();
            codes.addAll(codeList);
            codeList.clear();

            configuration.setPath(newFilePath);
            configuration.setHandler(new Handler<VipDTO>() {
                @Override
                public void execute(VipDTO dto) {
                    Context context = RuntimeContext.get();
                    Vip vip = new Vip();
                    BeanUtils.copyProperties(dto, vip);
                    if (BeanCopyUtils.isEmpty(vip)) {
                        return;
                    }
                    logger.info(String.format("数据导入进度：%d/%d", RuntimeContext.get().getRowIndex(), RuntimeContext.get().getEndRow()));

                    // 导入团队
                    String groupId = dto.getGroupId();
                    if (StringUtils.isNotEmpty(groupId)) {  // 如果使用的是团队编号
                        String groupName = groupMap.get(groupId);
                        if (StringUtils.isEmpty(groupName)) {
                            Group g = groupDao.findByCode(groupId);
                            if (g == null) {
                                g = new Group();
                                g.setCompany(company);
                                g.setCode(groupId);
                                g.setName(groupId);
                                g.setDeleted(false);
                                groupDao.save(g);
                            }
                            groupMap.put(groupId, g.getName());
                        }
                        vip.setGroupId(groupId);
                        vip.setGroupName(groupName);
                    }
                    String groupName = dto.getGroupName();
                    if (StringUtils.isEmpty(groupId) && StringUtils.isEmpty(groupName)) {
                        groupName = "默认团队";
                    }
                    if (StringUtils.isNotEmpty(groupName)) {
                        String code = groupMap.get(groupName);
                        if (StringUtils.isEmpty(code)) {
                            code = groupDao.findCode(company, groupName);
                            if (StringUtils.isEmpty(code)) {
                                Group g = new Group();
                                g.setCode(groupName);
                                g.setName(groupName);
                                g.setCompany(company);
                                g.setDeleted(false);
                                groupDao.save(g);
                                code = groupName;
                            }
                            groupMap.put(code, groupName);
                        }
                        vip.setGroupId(groupName);
                        vip.setGroupName(groupName);
                    }

                    // 签约状态
                    String assignStatus = dto.getAssignStatus();
                    if (StringUtils.isEmpty(assignStatus)) {
                        assignStatus = "未签约";
                    }
                    if (StringUtils.include(assignStatus, "已绑定")) {
                        assignStatus = "已签约";
                    }
                    if (StringUtils.include(assignStatus, "未绑定")) {
                        assignStatus = "未签约";
                    }
                    ParameterContainer container = ParameterContainer.getInstance();
                    String assignStatusValue = container.getSysValue(Params.VIP_ASSIGN_STATUS, assignStatus);
                    Assert.hasText(assignStatusValue, "签约状态[" + assignStatus + "]在系统中不存在!");
                    vip.setAssignStatus(assignStatusValue);
                    // 状态
                    String status = dto.getStatus();
                    Assert.hasText(status, "会员状态不能为空!");
                    if (StringUtils.include(status, "已审核")) {
                        status = "正常";
                    }
                    String statusValue = container.getSysValue(Params.VIP_STATUS, status);
                    Assert.hasText(statusValue, "状态[" + assignStatus + "]在系统中不存在!");
                    vip.setStatus(statusValue);

                    // 创建时间
                    String date = dto.getDate();
                    Date occurDate = null;
                    if (StringUtils.isNotEmpty(date)) {
                        if (date.matches("\\d+")) {
                            occurDate = new Date(Long.parseLong(date));
                        } else {
                            try {
                                occurDate = DateUtils.parseDate(date, parsePatterns);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    vip.setOccurDate(occurDate);
                    vip.setCompany(company);
                    Assert.isTrue(!codes.contains(vip.getCode()), "会员编号[" + vip.getCompany() + "]重复!");
                    codes.add(vip.getCode());
                    session.save(vip);
                    if (context.getRowIndex() % 10 == 0) {
                        session.flush();
                        session.clear();
                    }
                }
            });
            logger.info("开始导入数据....");
            ImportEngine engine = new ImportEngine(configuration);
            try {
                engine.execute();
            } catch (Exception e) {
                Assert.isTrue(false, String.format("数据异常!发生在第%d行,%d列!原因:%s", RuntimeContext.get().getRowIndex(), RuntimeContext.get().getCellIndex(), e.getCause() == null ? e.getMessage() : e.getCause().getMessage()));
            } finally {
                new File(newFilePath).delete();
                session.clear();
                logger.info(String.format("执行GC操作之前：%d / %d...", Runtime.getRuntime().freeMemory() / 1000000, Runtime.getRuntime().maxMemory() / 1000000));
                System.gc();
                logger.info(String.format("执行GC操作之后：%d / %d...", Runtime.getRuntime().freeMemory() / 1000000, Runtime.getRuntime().maxMemory() / 1000000));
            }
            logger.info(String.format("导入数据成功,用时(%d)s....", (System.currentTimeMillis() - start) / 1000));
        }
    }

    @Override
    public void doCallback(Vip vip, VipVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();
        // 签约状态
        vo.setAssignStatusName(container.getSystemName(Params.VIP_ASSIGN_STATUS, vip.getAssignStatus()));

        // 状态
        vo.setStatusName(container.getSystemName(Params.VIP_STATUS, vip.getStatus()));

        vo.setCompanyName(companyDao.getName(vip.getCompany()));

    }
}
