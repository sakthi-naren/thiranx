package com.sakthi_naren.thiranx.features.report;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.Task;
import com.sakthi_naren.thiranx.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class ReportView {

    private final ReportModel reportModel;
    private final Scanner scanner;
    private final Employee currentManager;

    public ReportView(Employee currentManager) {
        this.reportModel     = new ReportModel(this);
        this.scanner         = ConsoleInput.getScanner();
        this.currentManager  = currentManager;
    }

    public void init() {
        for (;;) {
            System.out.println();
            System.out.println("Reports");
            System.out.println("1. System summary");
            System.out.println("2. My team summary");
            System.out.println("3. Team breakdown by member");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    showSystemSummary();
                    break;
                case "2":
                    showManagerSummary();
                    break;
                case "3":
                    showTeamBreakdown();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ── Report screens ────────────────────────────────────────────────────────

    private void showSystemSummary() {
        Report r = reportModel.buildSystemReport();
        System.out.println();
        System.out.println("=== System Summary ===");
        printEmployeeStats(r);
        printTaskStats(r);
        pause();
    }

    private void showManagerSummary() {
        Long managerId = currentManager == null ? null : currentManager.getId();
        Report r = reportModel.buildManagerReport(managerId);
        System.out.println();
        System.out.println("=== My Team Summary ===");
        System.out.println("Direct reports    : " + r.getTotalEmployees());
        printTaskStats(r);
        pause();
    }

    private void showTeamBreakdown() {
        Long managerId = currentManager == null ? null : currentManager.getId();
        List<Employee> reports = reportModel.getDirectReports(managerId);
        System.out.println();
        System.out.println("=== Team Breakdown by Member ===");
        if (reports.isEmpty()) {
            System.out.println("You have no direct reports.");
            pause();
            return;
        }
        System.out.println();
        for (Employee member : reports) {
            List<Task> tasks = reportModel.getTasksFor(member.getId());
            int open = 0, inProgress = 0, completed = 0, onHold = 0,
                    cancelled = 0, overdue = 0;
            long now = System.currentTimeMillis();
            for (Task t : tasks) {
                Task.TaskStatus s = t.getStatus();
                if (s == Task.TaskStatus.OPEN)           open++;
                else if (s == Task.TaskStatus.IN_PROGRESS) inProgress++;
                else if (s == Task.TaskStatus.COMPLETED)   completed++;
                else if (s == Task.TaskStatus.ON_HOLD)     onHold++;
                else if (s == Task.TaskStatus.CANCELLED)   cancelled++;
                if (t.getDueDate() != null && t.getDueDate() < now
                        && s != Task.TaskStatus.COMPLETED
                        && s != Task.TaskStatus.CANCELLED) {
                    overdue++;
                }
            }
            System.out.println(member.getName() + " (" + member.getEmployeeId() + ")");
            System.out.println("  Total: " + tasks.size()
                    + "  Open: " + open
                    + "  In-Progress: " + inProgress
                    + "  Completed: " + completed
                    + "  On-Hold: " + onHold
                    + "  Cancelled: " + cancelled
                    + "  Overdue: " + overdue);
        }
        pause();
    }

    // ── Print helpers ─────────────────────────────────────────────────────────

    private void printEmployeeStats(Report r) {
        System.out.println("Total employees   : " + r.getTotalEmployees());
        System.out.println("  Managers        : " + r.getTotalManagers());
        System.out.println("  Employees       : " + r.getTotalEmployeesOnly());
    }

    private void printTaskStats(Report r) {
        System.out.println("Total tasks       : " + r.getTotalTasks());
        System.out.println("  Open            : " + r.getOpenTasks());
        System.out.println("  In Progress     : " + r.getInProgressTasks());
        System.out.println("  Completed       : " + r.getCompletedTasks());
        System.out.println("  On Hold         : " + r.getOnHoldTasks());
        System.out.println("  Cancelled       : " + r.getCancelledTasks());
        System.out.println("  Reopened        : " + r.getReopenedTasks());
        System.out.println("  Unassigned      : " + r.getUnassignedTasks());
        System.out.println("  Overdue         : " + r.getOverdueTasks());
        System.out.println("By priority:");
        System.out.println("  P1              : " + r.getP1Tasks());
        System.out.println("  P2              : " + r.getP2Tasks());
        System.out.println("  P3              : " + r.getP3Tasks());
    }

    private void pause() {
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }
}