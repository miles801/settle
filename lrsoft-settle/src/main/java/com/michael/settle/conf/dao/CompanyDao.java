package com.michael.settle.conf.dao;

import com.michael.settle.conf.bo.CompanyBo;
import com.michael.settle.conf.domain.Company;

import java.util.List;

/**
 * @author Michael
 */
public interface CompanyDao {

    String save(Company company);

    void update(Company company);

    /**
     * 高级查询接口，不使用分页
     */
    List<Company> query(CompanyBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Company> pageQuery(CompanyBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(CompanyBo bo);

    Company findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Company company);

    /**
     * 根据ID获取名称（缓存的数据）
     *
     * @param id ID
     */
    String getName(String id);
}
