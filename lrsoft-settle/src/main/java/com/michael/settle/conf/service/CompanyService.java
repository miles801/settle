package com.michael.settle.conf.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.conf.bo.CompanyBo;
import com.michael.settle.conf.domain.Company;
import com.michael.settle.conf.vo.CompanyVo;

import java.util.List;

/**
 * @author Michael
 */
public interface CompanyService {

    /**
     * 保存
     */
    String save(Company company);

    /**
     * 更新
     */
    void update(Company company);

    /**
     * 分页查询
     */
    PageVo pageQuery(CompanyBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<CompanyVo> query(CompanyBo bo);

    /**
     * 根据ID查询对象的信息
     */
    CompanyVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


    /**
     * 批量启用
     */
    void enable(String[] ids);

    /**
     * 批量禁用
     */
    void disable(String[] ids);

    /**
     * 查询所有有效的数据（不使用分页）
     *
     * @param bo 可选的查询条件
     */
    List<Company> queryValid(CompanyBo bo);


}
