package com.michael.settle.vip.dto;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

/**
 * @author Michael
 */
@ImportConfig(file = "", startRow = 1)
public class GroupDTO implements DTO {
    // 编号
    @Col(index = 0)
    private String code;
    // 名称
    @Col(index = 1)
    private String name;
    // 所属文交所
    private String company;
    // 备注
    @Col(index = 2)
    private String description;

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

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
