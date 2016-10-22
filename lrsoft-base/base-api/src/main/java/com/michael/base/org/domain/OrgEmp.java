package com.michael.base.org.domain;

import com.michael.common.CommonDomain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机构员工,用于保存机构和员工的关联关系
 *
 * @author Michael
 */
@Entity
@Table(name = "sys_org_emp")
public class OrgEmp extends CommonDomain {

    // 员工ID
    private String empId;

    public OrgEmp() {
    }

    public OrgEmp(String orgId, String empId) {
        setOrgId(orgId);
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
