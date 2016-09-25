package com.michael.settle.vip.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 团队
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_group")
public class Group extends CommonDomain {
    @ApiField(value = "编号")
    @NotNull
    @Column(name = "code", nullable = false, unique = true, length = 40)
    private String code;

    @ApiField(value = "名称")
    @NotNull
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @Column(name = "company", length = 40)
    private String company;

    @ApiField(value = "备注")
    @Column(name = "description", length = 1000)
    private String description;


    @NotNull
    @Column
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
