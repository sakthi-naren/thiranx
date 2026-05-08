package com.sivasuryap.thiranx.features.employee.reportee;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.repository.ThiranXDB;

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