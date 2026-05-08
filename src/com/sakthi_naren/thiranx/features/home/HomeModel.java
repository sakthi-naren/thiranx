package com.sivasuryap.thiranx.features.home;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.repository.ThiranXDB;

class HomeModel {

    private final HomeView homeView;

    HomeModel(HomeView homeView) {
        this.homeView = homeView;
    }

    void init(Employee employee) {
        if (employee == null || employee.getRole() == null) {
            homeView.showUnauthorized();
            return;
        }
        if (employee.getRole() == Employee.Role.MANAGER) {
            homeView.showManagerMenu();
        } else {
            homeView.showEmployeeMenu();
        }
    }

    int getUnreadCount(Long employeeId) {
        return ThiranXDB.getInstance().getUnreadNotificationCount(employeeId);
    }
}