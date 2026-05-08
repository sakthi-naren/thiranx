package com.sivasuryap.thiranx.features.notification;

import com.sivasuryap.thiranx.data.dto.Notification;
import com.sivasuryap.thiranx.data.repository.ThiranXDB;

import java.util.List;

class NotificationModel {

    private final NotificationView notificationView;

    NotificationModel(NotificationView notificationView) {
        this.notificationView = notificationView;
    }

    List<Notification> getNotifications(Long employeeId) {
        return ThiranXDB.getInstance().getNotificationsFor(employeeId);
    }

    int markAllRead(Long employeeId) {
        return ThiranXDB.getInstance().markNotificationsRead(employeeId);
    }
}