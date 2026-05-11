package com.sakthi_naren.thiranx.features.employee.reportee;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.features.employee.EmployeeListView;
import com.sakthi_naren.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class ReporteeListView {

    private final ReporteeListModel reporteeListModel;
    private final Scanner scanner;
    private final Employee manager;

    public ReporteeListView(Employee manager) {
        this.reporteeListModel = new ReporteeListModel(this);
        this.scanner           = ConsoleInput.getScanner();
        this.manager           = manager;
    }

    public void init() {
        System.out.println();
        System.out.println("My Reportees");
        Long managerId = (manager == null) ? null : manager.getId();
        List<Employee> reportees = reporteeListModel.getReportees(managerId);
        if (reportees.isEmpty()) {
            System.out.println("You have no reporting employees.");
        } else {
            System.out.println("#   Employee Id   Name                        Email                           Role      Status");
            for (int i = 0; i < reportees.size(); i++) {
                System.out.println(EmployeeListView.formatRow(i + 1, reportees.get(i)));
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }
}