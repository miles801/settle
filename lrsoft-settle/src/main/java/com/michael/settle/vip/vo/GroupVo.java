package com.michael.settle.vip.vo;

import com.michael.settle.vip.domain.Group;

/**
 * @author Michael
 */
public class GroupVo extends Group {
    // 所属文交所--参数名称
    private String companyName;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }
}
