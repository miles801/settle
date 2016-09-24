package com.michael.settle.report.vo;

import com.michael.settle.report.domain.GroupVip;

/**
 * @author Michael
 */
public class GroupVipVo extends GroupVip {
    // 所属文交所--参数名称
    private String companyName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }
}
