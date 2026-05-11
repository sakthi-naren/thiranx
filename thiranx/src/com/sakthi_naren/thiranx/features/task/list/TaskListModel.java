package com.sakthi_naren.thiranx.features.task.list;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.Task;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;

import java.util.List;

class TaskListModel {

    private final TaskListView taskListView;

    TaskListModel(TaskListView taskListView) {
        this.taskListView = taskListView;
    }

    List<Task> getMyTasks(Long employeeId) {
        return ThiranXDB.getInstance().getTasksAssignedTo(employeeId);
    }

    String getEmployeeName(Long id) {
        if (id == null) return "-";
        Employee employee = ThiranXDB.getInstance().getEmployeeById(id);
        return (employee == null || employee.getName() == null) ? "-" : employee.getName();
    }
}