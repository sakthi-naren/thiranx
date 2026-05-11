package com.sakthi_naren.thiranx.features.employee;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class EmployeeListView {

    private static final String LIST_HEADER =
            "#   Employee Id   Name                        Email                           Role      Status";

    private final EmployeeListModel employeeListModel;
    private final Scanner scanner;

    public EmployeeListView() {
        this.employeeListModel = new EmployeeListModel(this);
        this.scanner           = ConsoleInput.getScanner();
    }

    public void init() {
        List<Employee> employees = employeeListModel.getAllEmployees();
        System.out.println();
        System.out.println("All Employees");
        if (employees.isEmpty()) {
            System.out.println("No employees yet.");
        } else {
            System.out.println(LIST_HEADER);
            for (int i = 0; i < employees.size(); i++) {
                System.out.println(formatRow(i + 1, employees.get(i)));
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    public static String formatRow(int index, Employee e) {
        return String.format(
                "%-3d %-13s %-27s %-31s %-9s %s",
                index,
                safe(e.getEmployeeId()),
                truncate(safe(e.getName()), 27),
                truncate(safe(e.getEmail()), 31),
                e.getRole()   == null ? "-" : e.getRole().name(),
                e.getStatus() == null ? "-" : e.getStatus().name());
    }

    static String safe(String value) {
        return value == null ? "-" : value;
    }

    static String truncate(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";
    }
}