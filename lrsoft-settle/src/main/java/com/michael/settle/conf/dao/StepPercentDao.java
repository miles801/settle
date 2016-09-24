package com.michael.settle.conf.dao;

import com.michael.settle.conf.bo.StepPercentBo;
import com.michael.settle.conf.domain.StepPercent;

import java.util.List;

/**
 * @author Michael
 */
public interface StepPercentDao {

    String save(StepPercent stepPercent);

    void update(StepPercent stepPercent);

    /**
     * 高级查询接口，不使用分页
     */
    List<StepPercent> query(StepPercentBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<StepPercent> pageQuery(StepPercentBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(StepPercentBo bo);

    StepPercent findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(StepPercent stepPercent);

    /**
     * 获取指定文交所指定的值的阶梯比例
     *
     * @param company 文交所
     * @param value   值
     * @return 比例
     */
    Double getPercent(String company, Double value);

    /**
     * 查询指定文交所的所有配置
     *
     * @param company 文交所
     */
    List<StepPercent> queryByCompany(String company);
}
