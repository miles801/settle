package com.michael.settle.vip.vo;

import com.michael.settle.vip.domain.Vip;

/**
 * @author Michael
 */
public class VipVo extends Vip {
    // 签约状态--参数名称
    private String assignStatusName;
    // 状态--参数名称
    private String statusName;
    // 所属文交所--参数名称
    private String companyName;

    public void setAssignStatusName(String assignStatusName) {
        this.assignStatusName = assignStatusName;
    }

    public String getAssignStatusName() {
        return this.assignStatusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }
}
