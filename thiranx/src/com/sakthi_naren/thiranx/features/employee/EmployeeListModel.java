package com.sakthi_naren.thiranx.features.employee;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;

import java.util.List;

class EmployeeListModel {

    private final EmployeeListView employeeListView;

    EmployeeListModel(EmployeeListView employeeListView) {
        this.employeeListView = employeeListView;
    }

    List<Employee> getAllEmployees() {
        return ThiranXDB.getInstance().getEmployees();
    }
}