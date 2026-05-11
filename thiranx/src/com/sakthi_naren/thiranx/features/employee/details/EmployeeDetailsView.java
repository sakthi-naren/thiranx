package com.sakthi_naren.thiranx.features.employee.details;

import com.sakthi_naren.thiranx.data.repository.ThiranXDB;
import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.util.ConsoleInput;
import com.sakthi_naren.thiranx.util.ParseHelper;

import java.util.Scanner;

public class EmployeeDetailsView {

    private final EmployeeDetailsModel employeeDetailsModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public EmployeeDetailsView(Employee currentUser) {
        this.employeeDetailsModel = new EmployeeDetailsModel(this);
        this.scanner              = ConsoleInput.getScanner();
        this.currentUser          = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("Employee Details");
        System.out.print("Enter Employee ID (e.g. EMP00001) or numeric id: ");
        String input = scanner.nextLine().trim();

        Employee employee = resolveEmployee(input);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        printDetails(employee);
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private Employee resolveEmployee(String input) {
        if (input == null || input.isEmpty()) return null;
        // Try numeric id first
        Integer numericId = ParseHelper.parseNonNegativeInt(input);
        if (numericId != null && numericId > 0) {
            return employeeDetailsModel.getEmployeeById((long) numericId);
        }
        // Try EMP-format id by iterating all employees
        for (Employee e : ThiranXDB.getInstance().getEmployees()) {
            if (input.equalsIgnoreCase(e.getEmployeeId())) return e;
        }
        return null;
    }

    private void printDetails(Employee e) {
        System.out.println();
        System.out.println("Employee Id  : " + safe(e.getEmployeeId()));
        System.out.println("Name         : " + safe(e.getName()));
        System.out.println("Email        : " + safe(e.getEmail()));
        System.out.println("Mobile       : " + safe(e.getMobileNo()));
        System.out.println("Date of Birth: " + employeeDetailsModel.formatDob(e.getDob()));
        System.out.println("Role         : " + (e.getRole()   == null ? "-" : e.getRole().name()));
        System.out.println("Status       : " + (e.getStatus() == null ? "-" : e.getStatus().name()));
        System.out.println("Reports To   : " + employeeDetailsModel.getReportingManagerName(e.getReportingTo()));
        System.out.println("Joined At    : " + employeeDetailsModel.formatDateTime(e.getCreatedAt()));
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }
}