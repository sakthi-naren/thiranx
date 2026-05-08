package com.sivasuryap.thiranx.features.task.team;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.util.ConsoleInput;
import com.sivasuryap.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TeamStatusView {

    private final TeamStatusModel teamStatusModel;
    private final Scanner scanner;
    private final Employee manager;

    public TeamStatusView(Employee manager) {
        this.teamStatusModel = new TeamStatusModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.manager = manager;
    }

    public void init() {
        System.out.println();
        System.out.println("Team Status");
        Long managerId = (manager == null) ? null : manager.getId();
        List<Employee> reports = teamStatusModel.getDirectReports(managerId);
        if (reports.isEmpty()) {
            System.out.println("You have no reporting employees.");
        } else {
            for (Employee report : reports) {
                System.out.println();
                System.out.println(report.getName() + " (" + report.getEmployeeId() + ")");
                List<Task> tasks = teamStatusModel.getTasksFor(report.getId());
                if (tasks.isEmpty()) {
                    System.out.println("  No tasks assigned");
                } else {
                    for (Task task : tasks) {
                        System.out.println("  - [" + nameOr(task.getPriority()) + "] "
                                + task.getTitle() + " [" + nameOr(task.getStatus()) + "] due "
                                + ParseHelper.formatDate(task.getDueDate()));
                    }
                }
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}
