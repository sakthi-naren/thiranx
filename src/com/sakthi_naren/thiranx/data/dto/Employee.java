package com.sivasuryap.thiranx.data.dto;

public class Employee {

    private Long id;
    private String employeeId;
    private String name;
    private String email;
    private String password;
    private String mobileNo;
    private Long dob;
    private Role role;
    private Long reportingTo;
    private EmployeeStatus status;
    private Long createdAt;

    public enum Role {
        MANAGER, EMPLOYEE
    }

    public enum EmployeeStatus {
        ACTIVE, INACTIVE
    }

    public Employee() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public Long getDob() { return dob; }
    public void setDob(Long dob) { this.dob = dob; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getReportingTo() { return reportingTo; }
    public void setReportingTo(Long reportingTo) { this.reportingTo = reportingTo; }

    public EmployeeStatus getStatus() { return status; }
    public void setStatus(EmployeeStatus status) { this.status = status; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
}