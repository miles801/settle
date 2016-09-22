package com.michael.settle.vip.dao;

import com.michael.settle.vip.bo.BusinessBo;
import com.michael.settle.vip.domain.Business;

import java.util.List;

/**
 * @author Michael
 */
public interface BusinessDao {

    String save(Business business);

    void update(Business business);

    /**
     * 高级查询接口，不使用分页
     */
    List<Business> query(BusinessBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Business> pageQuery(BusinessBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(BusinessBo bo);

    Business findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Business business);

}
