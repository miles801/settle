package com.michael.settle.report.dao;

import com.michael.settle.report.bo.BusinessLogBo;
import com.michael.settle.report.domain.BusinessLog;

import java.util.List;

/**
 * @author Michael
 */
public interface BusinessLogDao {

    String save(BusinessLog businessLog);

    void update(BusinessLog businessLog);

    /**
     * 高级查询接口，不使用分页
     */
    List<BusinessLog> query(BusinessLogBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<BusinessLog> pageQuery(BusinessLogBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(BusinessLogBo bo);

    BusinessLog findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(BusinessLog businessLog);

}
