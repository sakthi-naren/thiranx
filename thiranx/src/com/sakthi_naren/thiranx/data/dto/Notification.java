package com.sakthi_naren.thiranx.data.dto;

public class Notification {

    private Long id;
    private Long employeeId;
    private Long taskId;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private Long createdTime;

    public enum NotificationType {
        TASK_ASSIGNED, STATUS_UPDATED, DUE_REMINDER
    }

    public Notification() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public Long getCreatedTime() { return createdTime; }
    public void setCreatedTime(Long createdTime) { this.createdTime = createdTime; }
}