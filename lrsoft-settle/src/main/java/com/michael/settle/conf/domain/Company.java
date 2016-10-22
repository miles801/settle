package com.michael.settle.conf.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 文交所
 *
 * @author Michael
 */
@Entity
@Table(name = "conf_company")
public class Company extends CommonDomain {
    @ApiField(value = "名称")
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ApiField(value = "描述")
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


}
