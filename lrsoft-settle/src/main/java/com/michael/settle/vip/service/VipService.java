package com.michael.settle.vip.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.vip.bo.VipBo;
import com.michael.settle.vip.domain.Vip;
import com.michael.settle.vip.vo.VipVo;

import java.util.List;

/**
 * @author Michael
 */
public interface VipService {

    /**
     * 保存
     */
    String save(Vip vip);

    /**
     * 更新
     */
    void update(Vip vip);

    /**
     * 分页查询
     */
    PageVo pageQuery(VipBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<VipVo> query(VipBo bo);

    /**
     * 根据ID查询对象的信息
     */
    VipVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


    /**
     * 导入数据
     *
     * @param attachmentIds 上传的附件列表
     */
    void importData(String company, String[] attachmentIds);
}