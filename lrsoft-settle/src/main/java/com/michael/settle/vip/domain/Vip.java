package com.michael.settle.vip.domain;

import com.michael.common.CommonDomain;
import com.michael.docs.annotations.ApiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 会员
 *
 * @author Michael
 */
@Entity
@Table(name = "settle_vip")
public class Vip extends CommonDomain {
    @ApiField(value = "编号")
    @NotNull
    @Column(name = "code", nullable = false, unique = true, length = 40)
    private String code;

    @ApiField(value = "名称")
    @NotNull
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @ApiField(value = "团队编号")
    @Column(name = "groupId", length = 40)
    private String groupId;

    @ApiField(value = "团队名称")
    @Column(length = 40)
    private String groupName;

    @ApiField(value = "签约状态", desc = "参数:VIP_ASSIGN_STATUS")
    @NotNull
    @Column(name = "assignStatus", nullable = false, length = 40)
    private String assignStatus;

    @ApiField(value = "状态", desc = "参数:VIP_STATUS")
    @NotNull
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    @ApiField(value = "文交所", desc = "参数:VIP_COMPANY")
    @Column(name = "company", length = 40)
    private String company;

    @ApiField(value = "推荐人")
    @Column(name = "recommend", length = 40)
    private String recommend;

    @ApiField("发生时间")
    @Column
    private Date occurDate;

    @ApiField("备注")
    @Column(length = 1000)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
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

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getAssignStatus() {
        return this.assignStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend() {
        return this.recommend;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
