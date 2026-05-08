package com.sivasuryap.thiranx.features.employee.add;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class EmployeeAddView {

    private final EmployeeAddModel employeeAddModel;
    private final Scanner scanner;
    private final Employee currentManager;

    public EmployeeAddView(Employee currentManager) {
        this.employeeAddModel = new EmployeeAddModel(this);
        this.scanner          = ConsoleInput.getScanner();
        this.currentManager   = currentManager;
    }

    public void init() {
        System.out.println();
        System.out.println("Add a new employee");

        boolean firstEmployee = employeeAddModel.isFirstEmployee();

        String name     = promptName();
        String email    = promptEmail();
        String password = promptPassword();
        String mobile   = promptMobile();
        Long   dob      = promptDob();

        Employee.Role role;
        Long reportingTo;
        if (firstEmployee) {
            System.out.println("As the first user in the system, this employee will be registered as a Manager.");
            role        = Employee.Role.MANAGER;
            reportingTo = null;
        } else {
            role        = promptRole();
            reportingTo = employeeAddModel.needsReportingManager(role)
                    ? (currentManager == null ? null : currentManager.getId())
                    : null;
        }

        employeeAddModel.addEmployee(name, email, password, mobile, dob, role, reportingTo);
    }

    private String promptName() {
        for (;;) {
            System.out.print("Enter full name: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateName(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    private String promptEmail() {
        for (;;) {
            System.out.print("Enter email: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateEmail(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    private String promptPassword() {
        for (;;) {
            System.out.print("Enter password (minimum 8 characters with letters and numbers): ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validatePassword(input);
            if (error != null) {
                System.out.println(error);
                continue;
            }
            System.out.print("Confirm password: ");
            String confirm      = scanner.nextLine();
            String confirmError = employeeAddModel.validateConfirmPassword(input, confirm);
            if (confirmError != null) {
                System.out.println(confirmError);
                continue;
            }
            return input;
        }
    }

    private String promptMobile() {
        for (;;) {
            System.out.print("Enter 10 digit mobile number: ");
            String input = scanner.nextLine();
            String error = employeeAddModel.validateMobile(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    private Long promptDob() {
        for (;;) {
            System.out.print("Enter date of birth (dd-MM-yyyy): ");
            Long dob = employeeAddModel.parseDateOfBirth(scanner.nextLine());
            if (dob != null) return dob;
            System.out.println("Enter a valid date. Employee must be at least 18 years old.");
        }
    }

    private Employee.Role promptRole() {
        for (;;) {
            System.out.println("Select role:");
            System.out.println("1. Manager");
            System.out.println("2. Employee");
            System.out.print("Choose an option: ");
            Employee.Role role = employeeAddModel.parseRole(scanner.nextLine());
            if (role != null) return role;
            System.out.println("Select a valid role.");
        }
    }

    void onEmployeeAdded(Employee employee) {
        System.out.println();
        System.out.println("Employee added successfully.");
        System.out.println("Employee id: " + employee.getEmployeeId());
    }

    void onEmployeeAddFailed(String message) {
        System.out.println(message);
    }
}