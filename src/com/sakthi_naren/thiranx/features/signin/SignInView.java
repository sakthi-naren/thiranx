package com.sivasuryap.thiranx.features.signin;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.LoginRequest;
import com.sivasuryap.thiranx.features.home.HomeView;
import com.sivasuryap.thiranx.features.signup.SignUpView;
import com.sivasuryap.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class SignInView {

    private final SignInModel signInModel;
    private final Scanner scanner;
    private boolean authenticated;

    public SignInView() {
        this.signInModel = new SignInModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.authenticated = false;
    }

    public void init() {
        System.out.println();
        System.out.println("Sign in to ThiranX");

        for (;;) {
            promptAndAuthenticate();
            if (authenticated) return;
            if (!promptPostFailureAction()) return;
        }
    }

    private void promptAndAuthenticate() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        LoginRequest request = new LoginRequest();
        request.setEmail(email == null ? null : email.trim());
        request.setPassword(password);

        signInModel.authenticate(request);
    }

    private boolean promptPostFailureAction() {
        for (;;) {
            System.out.println();
            System.out.println("1. Retry");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    return true;
                case "2":
                    new SignUpView().init();
                    return false;
                case "3":
                    System.out.println("Thank you for using ThiranX");
                    System.exit(0);
                    return false;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    void onSignInSuccessful(Employee employee) {
        authenticated = true;
        System.out.println("Welcome, " + employee.getName());
        new HomeView(employee).init();
    }

    void onSignInFailed(String message) {
        System.out.println(message);
    }

    void showErrorMessage(String message) {
        System.out.println(message);
    }
}