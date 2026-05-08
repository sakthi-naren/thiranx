package com.sivasuryap.thiranx.features.task.create;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.data.repository.ThiranXDB;
import com.sivasuryap.thiranx.util.ParseHelper;

class TaskCreateModel {

    private static final int MIN_TITLE       = 3;
    private static final int MAX_TITLE       = 100;
    private static final int MAX_DESCRIPTION = 500;

    private final TaskCreateView taskCreateView;

    TaskCreateModel(TaskCreateView taskCreateView) {
        this.taskCreateView = taskCreateView;
    }

    String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) return "Title cannot be empty";
        String trimmed = title.trim();
        if (trimmed.length() < MIN_TITLE || trimmed.length() > MAX_TITLE) {
            return "Title must be between " + MIN_TITLE + " and " + MAX_TITLE + " characters";
        }
        return null;
    }

    String validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) return "Description cannot be empty";
        if (description.trim().length() > MAX_DESCRIPTION) {
            return "Description cannot exceed " + MAX_DESCRIPTION + " characters";
        }
        return null;
    }

    Task.Priority parsePriority(String choice) {
        if (choice == null) return null;
        String c = choice.trim();
        if (c.equals("1") || c.equalsIgnoreCase("P1")) return Task.Priority.P1;
        if (c.equals("2") || c.equalsIgnoreCase("P2")) return Task.Priority.P2;
        if (c.equals("3") || c.equalsIgnoreCase("P3")) return Task.Priority.P3;
        return null;
    }

    Long parseDueDate(String dueDateText) {
        Long dueDate = ParseHelper.parseDate(dueDateText);
        if (dueDate == null) return null;
        if (!ParseHelper.isTodayOrFuture(dueDate)) return null;
        return dueDate;
    }

    void createTask(Employee currentUser, String title, String description,
                    Task.Priority priority, Long dueDate) {
        Task task = new Task();
        task.setTitle(title.trim());
        task.setDescription(description.trim());
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setAssignedBy(currentUser == null ? null : currentUser.getId());
        task.setAssignedTo(null);
        task.setStatus(Task.TaskStatus.OPEN);

        Task saved = ThiranXDB.getInstance().addTask(task);
        if (saved == null) {
            taskCreateView.onTaskCreateFailed("Could not create task. Please try again.");
            return;
        }
        taskCreateView.onTaskCreated(saved);
    }
}