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
import com.michael.settle.vip.bo.BusinessBo;
import com.michael.settle.vip.dao.BusinessDao;
import com.michael.settle.vip.domain.Business;
import com.michael.settle.vip.dto.BusinessDTO;
import com.michael.settle.vip.service.BusinessService;
import com.michael.settle.vip.vo.BusinessVo;
import com.michael.utils.beans.BeanCopyUtils;
import com.michael.utils.string.RandomUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService, BeanWrapCallback<Business, BusinessVo> {
    @Resource
    private BusinessDao businessDao;

    @Resource
    private MappingDao mappingDao;

    @Override
    public String save(Business business) {
        business.setDeleted(false);
        validate(business);
        String id = businessDao.save(business);
        return id;
    }

    @Override
    public void update(Business business) {
        validate(business);
        businessDao.update(business);
    }

    private void validate(Business business) {
        ValidatorUtils.validate(business);
    }

    @Override
    public PageVo pageQuery(BusinessBo bo) {
        PageVo vo = new PageVo();
        Long total = businessDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Business> businessList = businessDao.pageQuery(bo);
        List<BusinessVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(businessList, BusinessVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public BusinessVo findById(String id) {
        Business business = businessDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(business, BusinessVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            businessDao.deleteById(id);
        }
    }

    @Override
    public List<BusinessVo> query(BusinessBo bo) {
        List<Business> businessList = businessDao.query(bo);
        List<BusinessVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(businessList, BusinessVo.class);
        return vos;
    }

    @Override
    public void enable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Business business = businessDao.findById(id);
            Assert.notNull(business, "启用失败!数据不存在，请刷新后重试!");
            business.setDeleted(false);
        }
    }

    @Override
    public void disable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Business business = businessDao.findById(id);
            Assert.notNull(business, "启用失败!数据不存在，请刷新后重试!");
            business.setDeleted(true);
        }
    }

    @Override
    public List<Business> queryValid(BusinessBo bo) {
        if (bo == null) {
            bo = new BusinessBo();
        }
        bo.setDeleted(false);
        return businessDao.query(bo);
    }


    public void importData(final String company, final Long date, String[] attachmentIds) {
        Assert.hasText(company, "数据导入失败!请选择文交所!");
        Logger logger = Logger.getLogger(BusinessServiceImpl.class);
        Assert.notEmpty(attachmentIds, "数据导入失败!数据文件不能为空，请重试!");

        // 获取会员映射模板
        String mapping = mappingDao.getMapping(company, "3");
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
            configuration.setClazz(BusinessDTO.class);
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
            final String batchNo = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "-" + RandomUtils.generate(3);
            configuration.setHandler(new Handler<BusinessDTO>() {
                @Override
                public void execute(BusinessDTO dto) {
                    Context context = RuntimeContext.get();
                    Business business = new Business();
                    BeanUtils.copyProperties(dto, business);
                    if (BeanCopyUtils.isEmpty(business)) {
                        return;
                    }
                    // 交易时间
                    if (date != null) {
                        business.setBusinessTime(new Date(date));
                    } else {
                        business.setBusinessTime(DateUtils.addDays(new Date(), -1));
                    }

                    // 所属文交所
                    business.setCompany(company);
                    // 批次号
                    business.setBatchNo(batchNo);
                    business.setDeleted(false);

                    validate(business);
                    session.save(business);
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
    public void doCallback(Business business, BusinessVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 所属文交所
        vo.setCompanyName(container.getSystemName("VIP_COMPANY", business.getCompany()));

    }
}
