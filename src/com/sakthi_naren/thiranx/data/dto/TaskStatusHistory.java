package com.sivasuryap.thiranx.data.dto;

public class TaskStatusHistory {

    private Long id;
    private Long taskId;
    private Long changedBy;
    private Task.TaskStatus oldStatus;
    private Task.TaskStatus newStatus;
    private String remarks;
    private Long changedTime;

    public TaskStatusHistory() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Long getChangedBy() { return changedBy; }
    public void setChangedBy(Long changedBy) { this.changedBy = changedBy; }

    public Task.TaskStatus getOldStatus() { return oldStatus; }
    public void setOldStatus(Task.TaskStatus oldStatus) { this.oldStatus = oldStatus; }

    public Task.TaskStatus getNewStatus() { return newStatus; }
    public void setNewStatus(Task.TaskStatus newStatus) { this.newStatus = newStatus; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getChangedTime() { return changedTime; }
    public void setChangedTime(Long changedTime) { this.changedTime = changedTime; }
}