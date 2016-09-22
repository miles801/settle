package com.michael.settle.vip.dao;

import com.michael.settle.vip.bo.ContactBo;
import com.michael.settle.vip.domain.Contact;

import java.util.List;

/**
 * @author Michael
 */
public interface ContactDao {

    String save(Contact contact);

    void update(Contact contact);

    /**
     * 高级查询接口，不使用分页
     */
    List<Contact> query(ContactBo bo);

    /**
     * 高级查询接口，使用分页
     */
    List<Contact> pageQuery(ContactBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(ContactBo bo);

    Contact findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(Contact contact);

}
