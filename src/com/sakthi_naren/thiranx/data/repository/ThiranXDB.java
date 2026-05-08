package com.sivasuryap.thiranx.data.repository;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Notification;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.data.dto.TaskStatusHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThiranXDB {

    private static ThiranXDB thiranXDB = null;

    private ThiranXDB() {
    }

    public static ThiranXDB getInstance() {
        if (thiranXDB == null) {
            thiranXDB = new ThiranXDB();
        }
        return thiranXDB;
    }

    private final List<Employee>          employees            = new ArrayList<>();
    private final List<Task>              tasks                = new ArrayList<>();
    private final List<TaskStatusHistory> taskStatusHistories  = new ArrayList<>();
    private final List<Notification>      notifications        = new ArrayList<>();

    private long employeePk          = 0L;
    private long taskPk               = 0L;
    private long taskStatusHistoryPk  = 0L;
    private long notificationPk       = 0L;

    // ── Employee ─────────────────────────────────────────────────────────────

    public Employee addEmployee(Employee employee) {
        if (employee == null) return null;
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) return null;

        employeePk++;
        employee.setId(employeePk);
        employee.setEmployeeId(String.format(Locale.ROOT, "EMP%05d", employeePk));
        if (employee.getCreatedAt() == null) {
            employee.setCreatedAt(System.currentTimeMillis());
        }
        if (employee.getStatus() == null) {
            employee.setStatus(Employee.EmployeeStatus.ACTIVE);
        }
        if (employee.getRole() == null) {
            employee.setRole(Employee.Role.EMPLOYEE);
        }
        employees.add(employee);
        return employee;
    }

    public Employee getEmployeeByEmail(String email) {
        if (email == null) return null;
        String key = email.trim().toLowerCase(Locale.ROOT);
        if (key.isEmpty()) return null;
        for (Employee current : employees) {
            if (current.getEmail() != null
                    && current.getEmail().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return current;
            }
        }
        return null;
    }

    public Employee authenticateEmployee(String email, String password) {
        Employee employee = getEmployeeByEmail(email);
        if (employee == null) return null;
        if (password == null || !password.equals(employee.getPassword())) return null;
        return employee;
    }

    public Employee getEmployeeById(Long id) {
        if (id == null) return null;
        for (Employee current : employees) {
            if (id.equals(current.getId())) return current;
        }
        return null;
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public List<Employee> getEmployeesExcept(Long excludeId) {
        List<Employee> result = new ArrayList<>();
        for (Employee current : employees) {
            if (current.getStatus() != Employee.EmployeeStatus.ACTIVE) continue;
            if (excludeId != null && excludeId.equals(current.getId())) continue;
            result.add(current);
        }
        return result;
    }

    public List<Employee> getActiveManagers() {
        List<Employee> result = new ArrayList<>();
        for (Employee current : employees) {
            if (current.getRole() == Employee.Role.MANAGER
                    && current.getStatus() == Employee.EmployeeStatus.ACTIVE) {
                result.add(current);
            }
        }
        return result;
    }

    public List<Employee> getDirectReports(Long managerId) {
        List<Employee> result = new ArrayList<>();
        if (managerId == null) return result;
        for (Employee current : employees) {
            if (managerId.equals(current.getReportingTo())) result.add(current);
        }
        return result;
    }

    // ── Task ─────────────────────────────────────────────────────────────────

    public Task addTask(Task task) {
        if (task == null) return null;
        taskPk++;
        task.setId(taskPk);
        long now = System.currentTimeMillis();
        if (task.getCreatedTime() == null) task.setCreatedTime(now);
        task.setUpdatedTime(now);
        if (task.getStatus() == null) task.setStatus(Task.TaskStatus.OPEN);
        tasks.add(task);
        return task;
    }

    public Task updateTask(Task task) {
        if (task == null || task.getId() == null) return null;
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId().equals(tasks.get(i).getId())) {
                task.setUpdatedTime(System.currentTimeMillis());
                tasks.set(i, task);
                return task;
            }
        }
        return null;
    }

    public List<Task> getTasksAssignedTo(Long employeeId) {
        List<Task> result = new ArrayList<>();
        if (employeeId == null) return result;
        for (Task current : tasks) {
            if (employeeId.equals(current.getAssignedTo())) result.add(current);
        }
        return result;
    }

    public List<Task> getTasksCreatedBy(Long assignedById) {
        List<Task> result = new ArrayList<>();
        if (assignedById == null) return result;
        for (Task current : tasks) {
            if (assignedById.equals(current.getAssignedBy())) result.add(current);
        }
        return result;
    }

    public List<Task> getUnassignedTasksCreatedBy(Long managerId) {
        List<Task> result = new ArrayList<>();
        if (managerId == null) return result;
        for (Task current : tasks) {
            if (current.getAssignedTo() == null
                    && managerId.equals(current.getAssignedBy())) {
                result.add(current);
            }
        }
        return result;
    }

    // ── Task Status History ───────────────────────────────────────────────────

    public TaskStatusHistory addStatusHistory(TaskStatusHistory history) {
        if (history == null || history.getTaskId() == null) return null;
        taskStatusHistoryPk++;
        history.setId(taskStatusHistoryPk);
        if (history.getChangedTime() == null) {
            history.setChangedTime(System.currentTimeMillis());
        }
        taskStatusHistories.add(history);
        return history;
    }

    public List<TaskStatusHistory> getStatusHistoryForTask(Long taskId) {
        List<TaskStatusHistory> result = new ArrayList<>();
        if (taskId == null) return result;
        for (TaskStatusHistory current : taskStatusHistories) {
            if (taskId.equals(current.getTaskId())) result.add(current);
        }
        return result;
    }

    // ── Notification ─────────────────────────────────────────────────────────

    public Notification addNotification(Notification notification) {
        if (notification == null || notification.getEmployeeId() == null) return null;
        notificationPk++;
        notification.setId(notificationPk);
        if (notification.getCreatedTime() == null) {
            notification.setCreatedTime(System.currentTimeMillis());
        }
        if (notification.getIsRead() == null) {
            notification.setIsRead(Boolean.FALSE);
        }
        notifications.add(notification);
        return notification;
    }

    public List<Notification> getNotificationsFor(Long employeeId) {
        List<Notification> result = new ArrayList<>();
        if (employeeId == null) return result;
        for (Notification current : notifications) {
            if (employeeId.equals(current.getEmployeeId())) result.add(current);
        }
        return result;
    }

    public int getUnreadNotificationCount(Long employeeId) {
        if (employeeId == null) return 0;
        int count = 0;
        for (Notification current : notifications) {
            if (employeeId.equals(current.getEmployeeId())
                    && !Boolean.TRUE.equals(current.getIsRead())) {
                count++;
            }
        }
        return count;
    }

    public int markNotificationsRead(Long employeeId) {
        if (employeeId == null) return 0;
        int count = 0;
        for (Notification current : notifications) {
            if (employeeId.equals(current.getEmployeeId())
                    && !Boolean.TRUE.equals(current.getIsRead())) {
                current.setIsRead(Boolean.TRUE);
                count++;
            }
        }
        return count;
    }
}