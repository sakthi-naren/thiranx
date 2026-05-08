package com.sivasuryap.thiranx.features.signup;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.repository.ThiranXDB;
import com.sivasuryap.thiranx.util.ParseHelper;

import java.util.List;
import java.util.regex.Pattern;

class SignUpModel {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d).{8,}$");

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH  = 50;
    private static final int MIN_AGE_YEARS    = 18;

    private final SignUpView signUpView;

    SignUpModel(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    String validateName(String name) {
        if (name == null || name.trim().isEmpty()) return "Name cannot be empty";
        String trimmed = name.trim();
        if (trimmed.length() < MIN_NAME_LENGTH || trimmed.length() > MAX_NAME_LENGTH) {
            return "Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters";
        }
        return null;
    }

    String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) return "Email cannot be empty";
        String trimmed = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) return "Enter a valid email address";
        if (ThiranXDB.getInstance().getEmployeeByEmail(trimmed) != null) {
            return "This email is already registered";
        }
        return null;
    }

    String validatePassword(String password) {
        if (password == null || password.isEmpty()) return "Password cannot be empty";
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return "Password must be at least 8 characters and contain letters and numbers";
        }
        return null;
    }

    String validateConfirmPassword(String password, String confirmPassword) {
        if (confirmPassword == null || !confirmPassword.equals(password)) {
            return "Passwords do not match";
        }
        return null;
    }

    String validateMobile(String mobile) {
        if (mobile == null || mobile.trim().isEmpty()) return "Mobile number cannot be empty";
        if (!MOBILE_PATTERN.matcher(mobile.trim()).matches()) {
            return "Enter a valid 10 digit mobile number";
        }
        return null;
    }

    Long parseDateOfBirth(String dobText) {
        Long dobMillis = ParseHelper.parseDate(dobText);
        if (dobMillis == null) return null;
        if (dobMillis >= System.currentTimeMillis()) return null;
        if (ParseHelper.calculateAgeYears(dobMillis) < MIN_AGE_YEARS) return null;
        return dobMillis;
    }

    Employee.Role parseRole(String choice) {
        if (choice == null) return null;
        String c = choice.trim();
        if (c.equals("1") || c.equalsIgnoreCase("Manager")) return Employee.Role.MANAGER;
        if (c.equals("2") || c.equalsIgnoreCase("Employee")) return Employee.Role.EMPLOYEE;
        return null;
    }

    boolean isFirstEmployee() {
        return ThiranXDB.getInstance().getEmployees().isEmpty();
    }

    List<Employee> getActiveManagers() {
        return ThiranXDB.getInstance().getActiveManagers();
    }

    void registerEmployee(String name, String email, String password,
                          String mobile, Long dob, Employee.Role role, Long reportingTo) {
        Employee employee = new Employee();
        employee.setName(name.trim());
        employee.setEmail(email.trim());
        employee.setPassword(password);
        employee.setMobileNo(mobile.trim());
        employee.setDob(dob);
        employee.setRole(role);
        employee.setReportingTo(reportingTo);
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);

        Employee saved = ThiranXDB.getInstance().addEmployee(employee);
        if (saved == null) {
            signUpView.showErrorMessage("Could not create account. Please try again.");
            return;
        }
        signUpView.onSignUpSuccessful(saved);
    }
}