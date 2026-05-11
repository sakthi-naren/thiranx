package com.sakthi_naren.thiranx.features.signin;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.LoginRequest;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;

import java.util.regex.Pattern;

class SignInModel {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final SignInView signInView;

    SignInModel(SignInView signInView) {
        this.signInView = signInView;
    }

    String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) return "Email cannot be empty";
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) return "Enter a valid email address";
        return null;
    }

    String validatePassword(String password) {
        if (password == null || password.isEmpty()) return "Password cannot be empty";
        return null;
    }

    void authenticate(LoginRequest request) {
        if (request == null) {
            signInView.onSignInFailed("Invalid email or password");
            return;
        }
        String emailError = validateEmail(request.getEmail());
        if (emailError != null) {
            signInView.onSignInFailed(emailError);
            return;
        }
        String passwordError = validatePassword(request.getPassword());
        if (passwordError != null) {
            signInView.onSignInFailed(passwordError);
            return;
        }
        Employee employee = ThiranXDB.getInstance().authenticateEmployee(
                request.getEmail(), request.getPassword());
        if (employee == null) {
            signInView.onSignInFailed("Invalid email or password");
            return;
        }
        if (employee.getStatus() == Employee.EmployeeStatus.INACTIVE) {
            signInView.onSignInFailed("Your account is not active. Contact your administrator.");
            return;
        }
        signInView.onSignInSuccessful(employee);
    }
}