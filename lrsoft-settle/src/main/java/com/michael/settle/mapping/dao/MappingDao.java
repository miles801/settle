package com.michael.settle.mapping.dao;

import com.michael.settle.mapping.bo.MappingBo;
import com.michael.settle.mapping.domain.Mapping;

import java.util.List;

/**
 * @author Michael
 */
public interface MappingDao {

    String save(Mapping mapping);

    void update(Mapping mapping);

    /**
     * 高级查询接口，不使用分页
     */
    List<Mapping> query(MappingBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Mapping> pageQuery(MappingBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(MappingBo bo);

    Mapping findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Mapping mapping);

    /**
     * 判断指定的文交所是否已经包含了指定的设置
     *
     * @param company 文交所
     * @param name    表名称
     * @return true存在
     */
    boolean exist(String company, String name, String id);

    /**
     * 获取指定文交所指定表格的映射内容
     *
     * @param company 文交所
     * @param name    表名称
     */
    String getMapping(String company, String name);
}
