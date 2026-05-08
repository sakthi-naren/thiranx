package com.sivasuryap.thiranx.features.home;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.features.employee.EmployeeListView;
import com.sivasuryap.thiranx.features.employee.add.EmployeeAddView;
import com.sivasuryap.thiranx.features.employee.reportee.ReporteeListView;
import com.sivasuryap.thiranx.features.notification.NotificationView;
import com.sivasuryap.thiranx.features.report.ReportView;
import com.sivasuryap.thiranx.features.task.assign.AssignMode;
import com.sivasuryap.thiranx.features.task.assign.TaskAssignView;
import com.sivasuryap.thiranx.features.task.create.TaskCreateView;
import com.sivasuryap.thiranx.features.task.detail.TaskDetailView;
import com.sivasuryap.thiranx.features.task.list.TaskListView;
import com.sivasuryap.thiranx.features.task.status.TaskStatusUpdateView;
import com.sivasuryap.thiranx.features.task.team.TeamStatusView;
import com.sivasuryap.thiranx.util.ConsoleInput;

import java.util.Scanner;

public class HomeView {

    private final HomeModel homeModel;
    private final Employee employee;
    private final Scanner scanner;

    public HomeView(Employee employee) {
        this.homeModel = new HomeModel(this);
        this.employee  = employee;
        this.scanner   = ConsoleInput.getScanner();
    }

    public void init() {
        homeModel.init(employee);
    }

    void showUnauthorized() {
        System.out.println("Your account role is not set. Contact your administrator.");
    }

    void showManagerMenu() {
        for (;;) {
            int unread = homeModel.getUnreadCount(employee == null ? null : employee.getId());
            String notifLabel = unread > 0
                    ? "9.  Notifications (" + unread + " unread)"
                    : "9.  Notifications";

            System.out.println();
            System.out.println("Manager Home");
            System.out.println("1.  View all employees");
            System.out.println("2.  View reportees");
            System.out.println("3.  Add employee");
            System.out.println("4.  Add new task");
            System.out.println("5.  Assign a task");
            System.out.println("6.  View team status");
            System.out.println("7.  Update my task status");
            System.out.println("8.  View task details");
            System.out.println(notifLabel);
            System.out.println("10. View reports");
            System.out.println("11. Sign out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new EmployeeListView().init();
                    break;
                case "2":
                    new ReporteeListView(employee).init();
                    break;
                case "3":
                    new EmployeeAddView(employee).init();
                    break;
                case "4":
                    new TaskCreateView(employee).init();
                    break;
                case "5":
                    new TaskAssignView(employee, AssignMode.MANAGER_ASSIGN).init();
                    break;
                case "6":
                    new TeamStatusView(employee).init();
                    break;
                case "7":
                    new TaskStatusUpdateView(employee).init();
                    break;
                case "8":
                    new TaskDetailView(employee).init();
                    break;
                case "9":
                    new NotificationView(employee).init();
                    break;
                case "10":
                    new ReportView(employee).init();
                    break;
                case "11":
                    System.out.println("You have been signed out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    void showEmployeeMenu() {
        for (;;) {
            int unread = homeModel.getUnreadCount(employee == null ? null : employee.getId());
            String notifLabel = unread > 0
                    ? "5. Notifications (" + unread + " unread)"
                    : "5. Notifications";

            System.out.println();
            System.out.println("Employee Home");
            System.out.println("1. My tasks");
            System.out.println("2. Update task status");
            System.out.println("3. Reassign a task");
            System.out.println("4. View task details");
            System.out.println(notifLabel);
            System.out.println("6. Sign out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new TaskListView(employee).init();
                    break;
                case "2":
                    new TaskStatusUpdateView(employee).init();
                    break;
                case "3":
                    new TaskAssignView(employee, AssignMode.EMPLOYEE_REASSIGN).init();
                    break;
                case "4":
                    new TaskDetailView(employee).init();
                    break;
                case "5":
                    new NotificationView(employee).init();
                    break;
                case "6":
                    System.out.println("You have been signed out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}