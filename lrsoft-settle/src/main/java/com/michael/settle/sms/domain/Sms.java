package com.michael.settle.sms.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 短信
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_sms")
public class Sms extends CommonDomain {
    @ApiField(value = "总数量")
    @NotNull
    @Column(name = "counts", nullable = false)
    private Integer counts;


    @NotNull
    @Column
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }
}
