package com.michael.settle.vip.service.impl;

import com.michael.base.attachment.AttachmentProvider;
import com.michael.base.attachment.utils.AttachmentHolder;
import com.michael.base.attachment.vo.AttachmentVo;
import com.michael.base.parameter.service.ParameterContainer;
import com.michael.core.SystemContainer;
import com.michael.core.beans.BeanWrapBuilder;
import com.michael.core.beans.BeanWrapCallback;
import com.michael.core.hibernate.validator.ValidatorUtils;
import com.michael.core.pager.PageVo;
import com.michael.poi.core.Context;
import com.michael.poi.core.Handler;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.core.RuntimeContext;
import com.michael.poi.imp.cfg.ColMapping;
import com.michael.poi.imp.cfg.Configuration;
import com.michael.settle.mapping.dao.MappingDao;
import com.michael.settle.vip.bo.VipBo;
import com.michael.settle.vip.dao.VipDao;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("vipService")
public class VipServiceImpl implements VipService, BeanWrapCallback<Vip, VipVo> {
    @Resource
    private VipDao vipDao;

    @Resource
    private MappingDao mappingDao;

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


    public void importData(final String company, String[] attachmentIds) {
        Assert.hasText(company, "导入失败!会员所属文交所不能为空!");
        Logger logger = Logger.getLogger(VipServiceImpl.class);
        Assert.notEmpty(attachmentIds, "数据导入失败!数据文件不能为空，请重试!");
        final String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.0", "yyyy-MM-dd HH:mm:ss.SSS"};

        // 获取会员映射模板
        String mapping = mappingDao.getMapping(company, "1");
        Assert.hasText(mapping, "导入会员失败!该文交所还未配置映射模板!");
        for (String id : attachmentIds) {
            AttachmentVo vo = AttachmentProvider.getInfo(id);
            Assert.notNull(vo, "附件已经不存在，请刷新后重试!");
            final File file = AttachmentHolder.newInstance().getTempFile(id);
            logger.info("准备导入数据：" + file.getAbsolutePath());
            logger.info("初始化导入引擎....");
            long start = System.currentTimeMillis();

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

                    // 签约状态
                    String assignStatus = dto.getAssignStatus();
                    Assert.hasText(assignStatus, "签约状态不能为空!");
                    ParameterContainer container = ParameterContainer.getInstance();
                    String assignStatusValue = container.getSysValue(Params.VIP_ASSIGN_STATUS, assignStatus);
                    Assert.hasText(assignStatusValue, "签约状态[" + assignStatus + "]在系统中不存在!");
                    vip.setAssignStatus(assignStatusValue);
                    // 状态
                    String status = dto.getStatus();
                    Assert.hasText(status, "签约状态不能为空!");
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
                    validate(vip);
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
            }
            logger.info(String.format("导入数据成功,用时(%d)s....", (System.currentTimeMillis() - start) / 1000));
            new File(newFilePath).delete();

        }
    }

    @Override
    public void doCallback(Vip vip, VipVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();
        // 签约状态
        vo.setAssignStatusName(container.getSystemName(Params.VIP_ASSIGN_STATUS, vip.getAssignStatus()));

        // 状态
        vo.setStatusName(container.getSystemName(Params.VIP_STATUS, vip.getStatus()));

        // 所属文交所
        vo.setCompanyName(container.getSystemName(Params.COMPANY, vip.getCompany()));

    }
}
