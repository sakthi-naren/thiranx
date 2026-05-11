package com.sakthi_naren.thiranx.features.report;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.Task;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;

import java.util.List;

class ReportModel {

    private final ReportView reportView;

    ReportModel(ReportView reportView) {
        this.reportView = reportView;
    }

    /** Builds a system-wide summary report (used by Managers). */
    Report buildSystemReport() {
        List<Employee> employees = ThiranXDB.getInstance().getEmployees();
        List<Task> allTasks = getAllTasks();

        Report report = new Report();

        int managers = 0;
        int empOnly  = 0;
        for (Employee e : employees) {
            if (e.getRole() == Employee.Role.MANAGER) managers++;
            else empOnly++;
        }
        report.setTotalEmployees(employees.size());
        report.setTotalManagers(managers);
        report.setTotalEmployeesOnly(empOnly);

        applyTaskStats(report, allTasks);
        return report;
    }

    /** Builds a report scoped to the tasks created by a specific manager. */
    Report buildManagerReport(Long managerId) {
        List<Employee> reportees = ThiranXDB.getInstance().getDirectReports(managerId);
        List<Task> managerTasks = ThiranXDB.getInstance().getTasksCreatedBy(managerId);

        Report report = new Report();
        report.setTotalEmployees(reportees.size());
        report.setTotalManagers(0);
        report.setTotalEmployeesOnly(reportees.size());

        applyTaskStats(report, managerTasks);
        return report;
    }

    /** Returns direct reportees of the given manager with their task counts. */
    List<Employee> getDirectReports(Long managerId) {
        return ThiranXDB.getInstance().getDirectReports(managerId);
    }

    List<Task> getTasksFor(Long employeeId) {
        return ThiranXDB.getInstance().getTasksAssignedTo(employeeId);
    }

    String getEmployeeName(Long id) {
        if (id == null) return "-";
        Employee e = ThiranXDB.getInstance().getEmployeeById(id);
        return (e == null || e.getName() == null) ? "-" : e.getName();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private List<Task> getAllTasks() {
        // Collect all tasks from all employees (created-by covers every task)
        List<Employee> all = ThiranXDB.getInstance().getEmployees();
        java.util.Set<Long> seen = new java.util.HashSet<>();
        List<Task> result = new java.util.ArrayList<>();
        for (Employee e : all) {
            for (Task t : ThiranXDB.getInstance().getTasksCreatedBy(e.getId())) {
                if (t.getId() != null && seen.add(t.getId())) result.add(t);
            }
            for (Task t : ThiranXDB.getInstance().getTasksAssignedTo(e.getId())) {
                if (t.getId() != null && seen.add(t.getId())) result.add(t);
            }
        }
        return result;
    }

    private void applyTaskStats(Report report, List<Task> tasks) {
        long now = System.currentTimeMillis();
        int open = 0, inProgress = 0, completed = 0, onHold = 0,
                cancelled = 0, reopened = 0, unassigned = 0,
                p1 = 0, p2 = 0, p3 = 0, overdue = 0;

        for (Task t : tasks) {
            Task.TaskStatus status = t.getStatus();
            if (status == Task.TaskStatus.OPEN)        open++;
            else if (status == Task.TaskStatus.IN_PROGRESS) inProgress++;
            else if (status == Task.TaskStatus.COMPLETED)   completed++;
            else if (status == Task.TaskStatus.ON_HOLD)     onHold++;
            else if (status == Task.TaskStatus.CANCELLED)   cancelled++;
            else if (status == Task.TaskStatus.REOPENED)    reopened++;

            if (t.getAssignedTo() == null) unassigned++;

            Task.Priority priority = t.getPriority();
            if (priority == Task.Priority.P1) p1++;
            else if (priority == Task.Priority.P2) p2++;
            else if (priority == Task.Priority.P3) p3++;

            if (t.getDueDate() != null && t.getDueDate() < now
                    && status != Task.TaskStatus.COMPLETED
                    && status != Task.TaskStatus.CANCELLED) {
                overdue++;
            }
        }

        report.setTotalTasks(tasks.size());
        report.setOpenTasks(open);
        report.setInProgressTasks(inProgress);
        report.setCompletedTasks(completed);
        report.setOnHoldTasks(onHold);
        report.setCancelledTasks(cancelled);
        report.setReopenedTasks(reopened);
        report.setUnassignedTasks(unassigned);
        report.setP1Tasks(p1);
        report.setP2Tasks(p2);
        report.setP3Tasks(p3);
        report.setOverdueTasks(overdue);
    }
}