package com.michael.settle.report.service;

import com.michael.core.pager.PageVo;
import com.michael.settle.report.bo.BusinessLogBo;
import com.michael.settle.report.domain.BusinessLog;
import com.michael.settle.report.vo.BusinessLogVo;

import java.util.List;

/**
 * @author Michael
 */
public interface BusinessLogService {

    /**
     * 保存
     */
    String save(BusinessLog businessLog);

    /**
     * 更新
     */
    void update(BusinessLog businessLog);

    /**
     * 分页查询
     */
    PageVo pageQuery(BusinessLogBo bo);

    /**
     * 不进行分页，常用于对外提供的查询接口
     */
    List<BusinessLogVo> query(BusinessLogBo bo);

    /**
     * 根据ID查询对象的信息
     */
    BusinessLogVo findById(String id);

    /**
     * 强制删除
     */
    void deleteByIds(String[] ids);


}
