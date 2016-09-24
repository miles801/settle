package com.michael.settle.report.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 团队会员
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_group_vip")
public class GroupVip extends CommonDomain {
    @ApiField(value = "团队编号")
    @NotNull
    @Column(name = "groupCode", nullable = false, length = 20)
    private String groupCode;

    @ApiField(value = "团队名称")
    @NotNull
    @Column(name = "groupName", nullable = false, length = 20)
    private String groupName;

    @ApiField(value = "交易商数量")
    @NotNull
    @Column(name = "vipCounts", nullable = false)
    private Integer vipCounts;

    @ApiField(value = "正常数量")
    @NotNull
    @Column(name = "normalCounts", nullable = false)
    private Integer normalCounts;

    @ApiField(value = "其他数量")
    @NotNull
    @Column(name = "otherCounts", nullable = false)
    private Integer otherCounts;

    @ApiField(value = "已签约数量")
    @NotNull
    @Column(name = "assignCounts", nullable = false)
    private Integer assignCounts;

    @ApiField(value = "未签约数量")
    @NotNull
    @Column(name = "notAssignCounts", nullable = false)
    private Integer notAssignCounts;

    @ApiField(value = "所属文交所", desc = "参数:VIP_COMPANY")
    @NotNull
    @Column(name = "company", nullable = false, length = 40)
    private String company;

    @ApiField(value = "统计时间")
    @NotNull
    @Column(name = "occurDate", nullable = false)
    private Date occurDate;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setVipCounts(Integer vipCounts) {
        this.vipCounts = vipCounts;
    }

    public Integer getVipCounts() {
        return this.vipCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getNormalCounts() {
        return this.normalCounts;
    }

    public void setOtherCounts(Integer otherCounts) {
        this.otherCounts = otherCounts;
    }

    public Integer getOtherCounts() {
        return this.otherCounts;
    }

    public void setAssignCounts(Integer assignCounts) {
        this.assignCounts = assignCounts;
    }

    public Integer getAssignCounts() {
        return this.assignCounts;
    }

    public void setNotAssignCounts(Integer notAssignCounts) {
        this.notAssignCounts = notAssignCounts;
    }

    public Integer getNotAssignCounts() {
        return this.notAssignCounts;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Date getOccurDate() {
        return this.occurDate;
    }


}
