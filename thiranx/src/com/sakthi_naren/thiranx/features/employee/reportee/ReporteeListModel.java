package com.sakthi_naren.thiranx.features.employee.reportee;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;

import java.util.List;

class ReporteeListModel {

    private final ReporteeListView reporteeListView;

    ReporteeListModel(ReporteeListView reporteeListView) {
        this.reporteeListView = reporteeListView;
    }

    List<Employee> getReportees(Long managerId) {
        return ThiranXDB.getInstance().getDirectReports(managerId);
    }
}