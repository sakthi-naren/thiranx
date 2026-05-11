package com.sakthi_naren.thiranx.data.dto;

public class Task {

    private Long id;
    private String title;
    private String description;
    private Long assignedBy;
    private Long assignedTo;
    private Priority priority;
    private Long createdTime;
    private Long dueDate;
    private Long updatedTime;
    private Long completedTime;
    private String remarks;
    private TaskStatus status;

    public enum Priority {
        P1, P2, P3
    }

    public enum TaskStatus {
        OPEN, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED, REOPENED
    }

    public Task() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getAssignedBy() { return assignedBy; }
    public void setAssignedBy(Long assignedBy) { this.assignedBy = assignedBy; }

    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Long getCreatedTime() { return createdTime; }
    public void setCreatedTime(Long createdTime) { this.createdTime = createdTime; }

    public Long getDueDate() { return dueDate; }
    public void setDueDate(Long dueDate) { this.dueDate = dueDate; }

    public Long getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(Long updatedTime) { this.updatedTime = updatedTime; }

    public Long getCompletedTime() { return completedTime; }
    public void setCompletedTime(Long completedTime) { this.completedTime = completedTime; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}