package com.sakthi_naren.thiranx.features.task.team;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.Task;
import com.sakthi_naren.thiranx.data.repository.ThiranXDB;

import java.util.List;

class TeamStatusModel {

    private final TeamStatusView teamStatusView;

    TeamStatusModel(TeamStatusView teamStatusView) {
        this.teamStatusView = teamStatusView;
    }

    List<Employee> getDirectReports(Long managerId) {
        return ThiranXDB.getInstance().getDirectReports(managerId);
    }

    List<Task> getTasksFor(Long employeeId) {
        return ThiranXDB.getInstance().getTasksAssignedTo(employeeId);
    }
}
