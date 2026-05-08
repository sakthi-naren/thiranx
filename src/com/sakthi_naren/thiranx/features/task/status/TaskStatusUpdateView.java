package com.sivasuryap.thiranx.features.task.status;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.util.ConsoleInput;
import com.sivasuryap.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskStatusUpdateView {

    private final TaskStatusUpdateModel taskStatusUpdateModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskStatusUpdateView(Employee currentUser) {
        this.taskStatusUpdateModel = new TaskStatusUpdateModel(this);
        this.scanner               = ConsoleInput.getScanner();
        this.currentUser           = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("Update task status");
        List<Task> tasks = taskStatusUpdateModel.getMyTasks(currentUser);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to update.");
            return;
        }

        Task task = pickTask(tasks);
        if (task == null) return;

        System.out.println("Current status: " + nameOr(task.getStatus()));
        Task.TaskStatus newStatus = pickStatus();
        if (newStatus == null) return;

        String remarks = promptRemarks();
        taskStatusUpdateModel.updateStatus(task, newStatus, remarks, currentUser);
    }

    private Task pickTask(List<Task> tasks) {
        for (;;) {
            System.out.println("Select a task:");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle() + " [" + nameOr(t.getStatus()) + "]");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= tasks.size()) {
                return tasks.get(index - 1);
            }
            System.out.println("Select a valid option.");
        }
    }

    private Task.TaskStatus pickStatus() {
        for (;;) {
            System.out.println("Select new status:");
            System.out.println("1. OPEN");
            System.out.println("2. IN_PROGRESS");
            System.out.println("3. COMPLETED");
            System.out.println("4. ON_HOLD");
            System.out.println("5. CANCELLED");
            System.out.println("6. REOPENED");
            System.out.print("Choose an option: ");
            Task.TaskStatus status = taskStatusUpdateModel.parseStatus(scanner.nextLine());
            if (status != null) return status;
            System.out.println("Select a valid status.");
        }
    }

    private String promptRemarks() {
        for (;;) {
            System.out.print("Enter remarks: ");
            String input = scanner.nextLine();
            String error = taskStatusUpdateModel.validateRemarks(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    void onUpdateSuccessful(Task task, Task.TaskStatus oldStatus) {
        System.out.println("Status updated from " + nameOr(oldStatus) + " to " + nameOr(task.getStatus()) + ".");
    }

    void onUpdateFailed(String message) {
        System.out.println(message);
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}