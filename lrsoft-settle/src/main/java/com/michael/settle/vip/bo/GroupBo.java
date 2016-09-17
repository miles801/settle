package com.michael.settle.vip.bo;

import com.michael.core.hibernate.criteria.BO;
import com.michael.core.hibernate.criteria.Condition;

/**
 * @author Michael
 */
public class GroupBo implements BO {
    // 编号
    @Condition
    private String code;

    // 名称
    @Condition
    private String name;

    // 所属文交所
    @Condition
    private String company;

    // 备注
    @Condition
    private String description;


    @Condition
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
