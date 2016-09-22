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
import com.michael.poi.adapter.AnnotationCfgAdapter;
import com.michael.poi.core.Context;
import com.michael.poi.core.Handler;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.core.RuntimeContext;
import com.michael.poi.imp.cfg.Configuration;
import com.michael.settle.vip.bo.ContactBo;
import com.michael.settle.vip.dao.ContactDao;
import com.michael.settle.vip.domain.Contact;
import com.michael.settle.vip.dto.ContactDTO;
import com.michael.settle.vip.service.ContactService;
import com.michael.settle.vip.service.Params;
import com.michael.settle.vip.vo.ContactVo;
import com.michael.utils.beans.BeanCopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Michael
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService, BeanWrapCallback<Contact, ContactVo> {
    @Resource
    private ContactDao contactDao;

    @Override
    public String save(Contact contact) {
        contact.setDeleted(false);
        validate(contact);
        String id = contactDao.save(contact);
        return id;
    }

    @Override
    public void update(Contact contact) {
        validate(contact);
        contactDao.update(contact);
    }

    private void validate(Contact contact) {
        ValidatorUtils.validate(contact);
    }

    @Override
    public PageVo pageQuery(ContactBo bo) {
        PageVo vo = new PageVo();
        Long total = contactDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Contact> contactList = contactDao.pageQuery(bo);
        List<ContactVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(contactList, ContactVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public ContactVo findById(String id) {
        Contact contact = contactDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(contact, ContactVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            contactDao.deleteById(id);
        }
    }

    @Override
    public List<ContactVo> query(ContactBo bo) {
        List<Contact> contactList = contactDao.query(bo);
        List<ContactVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(contactList, ContactVo.class);
        return vos;
    }

    @Override
    public void enable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Contact contact = contactDao.findById(id);
            Assert.notNull(contact, "启用失败!数据不存在，请刷新后重试!");
            contact.setDeleted(false);
        }
    }

    @Override
    public void disable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Contact contact = contactDao.findById(id);
            Assert.notNull(contact, "启用失败!数据不存在，请刷新后重试!");
            contact.setDeleted(true);
        }
    }

    @Override
    public List<Contact> queryValid(ContactBo bo) {
        if (bo == null) {
            bo = new ContactBo();
        }
        bo.setDeleted(false);
        return contactDao.query(bo);
    }


    public void importData(final String company, String[] attachmentIds) {
        Logger logger = Logger.getLogger(ContactServiceImpl.class);
        Assert.notEmpty(attachmentIds, "数据导入失败!数据文件不能为空，请重试!");
        Assert.hasText(company, "导入失败!文交所不能为空!");
        for (String id : attachmentIds) {
            AttachmentVo vo = AttachmentProvider.getInfo(id);
            Assert.notNull(vo, "附件已经不存在，请刷新后重试!");
            final File file = AttachmentHolder.newInstance().getTempFile(id);
            logger.info("准备导入数据：" + file.getAbsolutePath());
            logger.info("初始化导入引擎....");
            long start = System.currentTimeMillis();

            // 初始化引擎
            Configuration configuration = new AnnotationCfgAdapter(ContactDTO.class).parse();
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
            configuration.setHandler(new Handler<ContactDTO>() {
                @Override
                public void execute(ContactDTO dto) {
                    Context context = RuntimeContext.get();
                    Contact contact = new Contact();
                    BeanUtils.copyProperties(dto, contact);
                    if (BeanCopyUtils.isEmpty(contact)) {
                        return;
                    }
                    // 银行
                    String bank = dto.getBank();
                    Assert.hasText(bank, "银行不能为空!");
                    ParameterContainer container = ParameterContainer.getInstance();
                    String bankValue = container.getSysValue(Params.BANK, bank);
                    Assert.hasText(bankValue, "银行[" + bank + "]在系统中不存在!");
                    contact.setBank(bankValue);
                    contact.setDeleted(false);
                    // 文交所
                    contact.setCompany(company);
                    validate(contact);
                    session.save(contact);
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
    public void doCallback(Contact contact, ContactVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 所属文交所
        vo.setCompanyName(container.getSystemName(Params.COMPANY, contact.getCompany()));

        // 返佣银行
        vo.setBankName(container.getSystemName(Params.BANK, contact.getBank()));

    }
}
