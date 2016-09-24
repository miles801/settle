package com.michael.settle.conf.dao;

import com.michael.settle.conf.bo.CompanyConfBo;
import com.michael.settle.conf.domain.CompanyConf;

import java.util.List;

/**
 * @author Michael
 */
public interface CompanyConfDao {

    String save(CompanyConf companyConf);

    void update(CompanyConf companyConf);

    /**
     * 高级查询接口，不使用分页
     */
    List<CompanyConf> query(CompanyConfBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<CompanyConf> pageQuery(CompanyConfBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(CompanyConfBo bo);

    CompanyConf findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(CompanyConf companyConf);

    /**
     * 根据文交所查询对应的配置
     *
     * @param company 文交所编号
     * @return 配置信息
     */
    CompanyConf findByCompany(String company);
}
