package com.michael.settle.mapping.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 映射
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_mapping")
public class Mapping extends CommonDomain {
    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @NotNull
    @Column(name = "company", nullable = false, length = 40)
    private String company;

    @ApiField(value = "表名称", desc = "参数:TABLE_NAME")
    @NotNull
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @ApiField(value = "内容")
    @NotNull
    @Lob
    private String content;


    @NotNull
    @Column
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }


}
