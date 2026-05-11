package com.sakthi_naren.thiranx.features.employee.details;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;
import com.sakthi_naren.thiranx.util.ParseHelper;

class EmployeeDetailsModel {

    private final EmployeeDetailsView employeeDetailsView;

    EmployeeDetailsModel(EmployeeDetailsView employeeDetailsView) {
        this.employeeDetailsView = employeeDetailsView;
    }

    Employee getEmployeeById(Long id) {
        return ThiranXDB.getInstance().getEmployeeById(id);
    }

    String getReportingManagerName(Long managerId) {
        if (managerId == null) return "-";
        Employee manager = ThiranXDB.getInstance().getEmployeeById(managerId);
        return (manager == null || manager.getName() == null) ? "-" : manager.getName();
    }

    String formatDob(Long millis) {
        return ParseHelper.formatDate(millis);
    }

    String formatDateTime(Long millis) {
        return ParseHelper.formatDateTime(millis);
    }
}