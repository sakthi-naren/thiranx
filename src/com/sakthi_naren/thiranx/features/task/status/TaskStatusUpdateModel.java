package com.sivasuryap.thiranx.features.task.status;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Notification;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.data.dto.TaskStatusHistory;
import com.sivasuryap.thiranx.data.repository.ThiranXDB;

import java.util.List;

class TaskStatusUpdateModel {

    private static final int MAX_REMARKS = 500;

    private final TaskStatusUpdateView taskStatusUpdateView;

    TaskStatusUpdateModel(TaskStatusUpdateView taskStatusUpdateView) {
        this.taskStatusUpdateView = taskStatusUpdateView;
    }

    List<Task> getMyTasks(Employee currentUser) {
        Long id = (currentUser == null) ? null : currentUser.getId();
        return ThiranXDB.getInstance().getTasksAssignedTo(id);
    }

    Task.TaskStatus parseStatus(String choice) {
        if (choice == null) return null;
        String c = choice.trim();
        if (c.equals("1") || c.equalsIgnoreCase("OPEN"))        return Task.TaskStatus.OPEN;
        if (c.equals("2") || c.equalsIgnoreCase("IN_PROGRESS")) return Task.TaskStatus.IN_PROGRESS;
        if (c.equals("3") || c.equalsIgnoreCase("COMPLETED"))   return Task.TaskStatus.COMPLETED;
        if (c.equals("4") || c.equalsIgnoreCase("ON_HOLD"))     return Task.TaskStatus.ON_HOLD;
        if (c.equals("5") || c.equalsIgnoreCase("CANCELLED"))   return Task.TaskStatus.CANCELLED;
        if (c.equals("6") || c.equalsIgnoreCase("REOPENED"))    return Task.TaskStatus.REOPENED;
        return null;
    }

    String validateRemarks(String remarks) {
        if (remarks == null || remarks.trim().isEmpty()) return "Remarks cannot be empty";
        if (remarks.trim().length() > MAX_REMARKS) {
            return "Remarks cannot exceed " + MAX_REMARKS + " characters";
        }
        return null;
    }

    void updateStatus(Task task, Task.TaskStatus newStatus, String remarks, Employee changedBy) {
        if (task == null || newStatus == null || changedBy == null) {
            taskStatusUpdateView.onUpdateFailed("Could not update status. Please try again.");
            return;
        }
        if (newStatus == task.getStatus()) {
            taskStatusUpdateView.onUpdateFailed("New status must differ from current status.");
            return;
        }

        Task.TaskStatus oldStatus = task.getStatus();

        TaskStatusHistory history = new TaskStatusHistory();
        history.setTaskId(task.getId());
        history.setChangedBy(changedBy.getId());
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setRemarks(remarks == null ? "" : remarks.trim());
        ThiranXDB.getInstance().addStatusHistory(history);

        task.setStatus(newStatus);
        if (newStatus == Task.TaskStatus.COMPLETED) {
            task.setCompletedTime(System.currentTimeMillis());
        } else if (newStatus == Task.TaskStatus.REOPENED) {
            task.setCompletedTime(null);
        }
        Task updated = ThiranXDB.getInstance().updateTask(task);
        if (updated == null) {
            taskStatusUpdateView.onUpdateFailed("Could not update status. Please try again.");
            return;
        }
        notifyCreator(updated, oldStatus, newStatus, changedBy);
        taskStatusUpdateView.onUpdateSuccessful(updated, oldStatus);
    }

    private void notifyCreator(Task task, Task.TaskStatus oldStatus,
                               Task.TaskStatus newStatus, Employee changedBy) {
        Long creatorId = task.getAssignedBy();
        if (creatorId == null) return;
        if (changedBy != null && creatorId.equals(changedBy.getId())) return;

        Notification notification = new Notification();
        notification.setEmployeeId(creatorId);
        notification.setTaskId(task.getId());
        notification.setType(Notification.NotificationType.STATUS_UPDATED);
        String who = (changedBy == null || changedBy.getName() == null) ? "an employee" : changedBy.getName();
        notification.setMessage("Task '" + task.getTitle() + "' status changed from "
                + oldStatus + " to " + newStatus + " by " + who);
        ThiranXDB.getInstance().addNotification(notification);
    }
}