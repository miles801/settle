package com.michael.settle.vip.dto;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

/**
 * @author Michael
 */
@ImportConfig(file = "", startRow = 1)
public class VipDTO implements DTO {
    // 编号
    @Col(index = 0)
    private String code;
    // 名称
    @Col(index = 1)
    private String name;
    // 所属团队
    @Col(index = 2)
    private String groupId;
    @Col(index = 2)
    private String groupName;
    // 推荐人
    @Col(index = 3)
    private String recommend;
    // 创建时间 这里需要进行转换（读取的是字符串）
    @Col(index = 4)
    private String date;
    // 签约状态
    @Col(index = 5)
    private String assignStatus;
    // 状态
    @Col(index = 6)
    private String status;
    // 备注
    @Col(index = 7)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommend() {
        return this.recommend;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
