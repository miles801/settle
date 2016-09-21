package com.michael.settle.mapping.vo;

import com.michael.settle.mapping.domain.Mapping;

/**
 * @author Michael
 */
public class MappingVo extends Mapping {
    // 文交所--参数名称
    private String companyName;
    // 表名称--参数名称
    private String nameName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setNameName(String nameName) {
        this.nameName = nameName;
    }

    public String getNameName() {
        return this.nameName;
    }
}
