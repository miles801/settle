package com.michael.settle.conf.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.conf.bo.CompanyConfBo;
import com.michael.settle.conf.domain.CompanyConf;
import com.michael.settle.conf.vo.CompanyConfVo;

import java.util.List;

/**
 * @author Michael
 */
public interface CompanyConfService {

    /**
     * 保存
     */
    String save(CompanyConf companyConf);

    /**
     * 更新
     */
    void update(CompanyConf companyConf);

    /**
     * 分页查询
     */
    PageVo pageQuery(CompanyConfBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<CompanyConfVo> query(CompanyConfBo bo);

    /**
     * 根据ID查询对象的信息
     */
    CompanyConfVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


}
