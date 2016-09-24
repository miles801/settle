package com.michael.settle.vip.dao;

import com.michael.settle.vip.bo.VipBo;
import com.michael.settle.vip.domain.Vip;

import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
public interface VipDao {

    String save(Vip vip);

    void update(Vip vip);

    /**
     * 高级查询接口，不使用分页
     */
    List<Vip> query(VipBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Vip> pageQuery(VipBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(VipBo bo);

    Vip findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Vip vip);

    /**
     * 判断是否具有重复的项
     *
     * @param code 编号
     * @param id   排除自身
     * @return true：存在
     */
    boolean hasCode(String code, String id);

    /**
     * 删除指定人创建的会员数据
     *
     * @param empId 员工ID
     */
    void clear(String empId);

    /**
     * sql查询
     *
     * @param sql    sql语句
     * @param params 参数
     * @return map
     */
    List<Map<String, Object>> sqlQuery(String sql, List<Object> params);

    /**
     * 根据编号查询名称
     *
     * @param vipCode 编号
     * @return 名称
     */
    String findName(String vipCode);

    /**
     * 根据编号查找
     *
     * @param vipCode 编号
     */
    Vip findByCode(String vipCode);
}
