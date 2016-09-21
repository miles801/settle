package com.michael.settle.mapping.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.mapping.bo.MappingBo;
import com.michael.settle.mapping.domain.Mapping;
import com.michael.settle.mapping.vo.MappingVo;

import java.util.List;

/**
 * @author Michael
 */
public interface MappingService {

    /**
     * 保存
     */
    String save(Mapping mapping);

    /**
     * 更新
     */
    void update(Mapping mapping);

    /**
     * 分页查询
     */
    PageVo pageQuery(MappingBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<MappingVo> query(MappingBo bo);

    /**
     * 根据ID查询对象的信息
     */
    MappingVo findById(String id);

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
    List<Mapping> queryValid(MappingBo bo);


}
