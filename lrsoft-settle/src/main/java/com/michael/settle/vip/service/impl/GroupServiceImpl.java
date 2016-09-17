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
import com.michael.settle.vip.bo.GroupBo;
import com.michael.settle.vip.dao.GroupDao;
import com.michael.settle.vip.domain.Group;
import com.michael.settle.vip.dto.GroupDTO;
import com.michael.settle.vip.service.GroupService;
import com.michael.settle.vip.service.Params;
import com.michael.settle.vip.vo.GroupVo;
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
@Service("groupService")
public class GroupServiceImpl implements GroupService, BeanWrapCallback<Group, GroupVo> {
    @Resource
    private GroupDao groupDao;

    @Override
    public String save(Group group) {
        group.setDeleted(false);
        validate(group);
        String id = groupDao.save(group);
        return id;
    }

    @Override
    public void update(Group group) {
        validate(group);
        groupDao.update(group);
    }

    private void validate(Group group) {
        ValidatorUtils.validate(group);

        // 验证重复 - 编号
        boolean hasCode = groupDao.hasCode(group.getCode(), group.getId());
        Assert.isTrue(!hasCode, "操作失败!编号[" + group.getCode() + "]已经存在!");

    }

    @Override
    public PageVo pageQuery(GroupBo bo) {
        PageVo vo = new PageVo();
        Long total = groupDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Group> groupList = groupDao.pageQuery(bo);
        List<GroupVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(groupList, GroupVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public GroupVo findById(String id) {
        Group group = groupDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(group, GroupVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            groupDao.deleteById(id);
        }
    }

    @Override
    public List<GroupVo> query(GroupBo bo) {
        List<Group> groupList = groupDao.query(bo);
        List<GroupVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrapList(groupList, GroupVo.class);
        return vos;
    }

    @Override
    public void enable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Group group = groupDao.findById(id);
            Assert.notNull(group, "启用失败!数据不存在，请刷新后重试!");
            group.setDeleted(false);
        }
    }

    @Override
    public void disable(String[] ids) {
        Assert.notEmpty(ids, "启用失败!ID集合不能为空!");
        for (String id : ids) {
            Group group = groupDao.findById(id);
            Assert.notNull(group, "启用失败!数据不存在，请刷新后重试!");
            group.setDeleted(true);
        }
    }

    @Override
    public List<Group> queryValid(GroupBo bo) {
        if (bo == null) {
            bo = new GroupBo();
        }
        bo.setDeleted(false);
        return groupDao.query(bo);
    }


    @Override
    public GroupVo findByCode(String code) {
        Assert.hasText(code, "查询失败!团队编号不能为空!");
        Group group = groupDao.findByCode(code);
        return BeanWrapBuilder.newInstance()
                .setCallback(this)
                .wrap(group, GroupVo.class);
    }

    public void importData(final String company, String[] attachmentIds) {
        Assert.hasText(company, "团队导入失败!请指定团队所属文交所!");
        Logger logger = Logger.getLogger(GroupServiceImpl.class);
        Assert.notEmpty(attachmentIds, "数据导入失败!数据文件不能为空，请重试!");
        for (String id : attachmentIds) {
            AttachmentVo vo = AttachmentProvider.getInfo(id);
            Assert.notNull(vo, "附件已经不存在，请刷新后重试!");
            final File file = AttachmentHolder.newInstance().getTempFile(id);
            logger.info("准备导入数据：" + file.getAbsolutePath());
            logger.info("初始化导入引擎....");
            long start = System.currentTimeMillis();

            // 初始化引擎
            Configuration configuration = new AnnotationCfgAdapter(GroupDTO.class).parse();
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
            configuration.setHandler(new Handler<GroupDTO>() {
                @Override
                public void execute(GroupDTO dto) {
                    Context context = RuntimeContext.get();
                    Group group = new Group();
                    BeanUtils.copyProperties(dto, group);
                    if (BeanCopyUtils.isEmpty(group)) {
                        return;
                    }
                    group.setDeleted(false);
                    group.setCompany(company);
                    validate(group);
                    session.save(group);
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
    public void doCallback(Group group, GroupVo vo) {
        ParameterContainer container = ParameterContainer.getInstance();

        // 所属文交所
        vo.setCompanyName(container.getSystemName(Params.COMPANY, group.getCompany()));

    }
}
