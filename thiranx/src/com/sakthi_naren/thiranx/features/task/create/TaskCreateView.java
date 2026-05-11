package com.sakthi_naren.thiranx.features.task.create;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.Task;
import com.sakthi_naren.thiranx.features.task.assign.TaskAssignView;
import com.sakthi_naren.thiranx.util.ConsoleInput;
import com.sakthi_naren.thiranx.util.ParseHelper;

import java.util.Scanner;

public class TaskCreateView {

    private final TaskCreateModel taskCreateModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskCreateView(Employee currentUser) {
        this.taskCreateModel = new TaskCreateModel(this);
        this.scanner         = ConsoleInput.getScanner();
        this.currentUser     = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("Add a new task");

        String        title       = promptTitle();
        String        description = promptDescription();
        Task.Priority priority    = promptPriority();
        Long          dueDate     = promptDueDate();

        taskCreateModel.createTask(currentUser, title, description, priority, dueDate);
    }

    private String promptTitle() {
        for (;;) {
            System.out.print("Enter task title: ");
            String input = scanner.nextLine();
            String error = taskCreateModel.validateTitle(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    private String promptDescription() {
        for (;;) {
            System.out.print("Enter task description: ");
            String input = scanner.nextLine();
            String error = taskCreateModel.validateDescription(input);
            if (error == null) return input.trim();
            System.out.println(error);
        }
    }

    private Task.Priority promptPriority() {
        for (;;) {
            System.out.println("Select priority:");
            System.out.println("1. P1");
            System.out.println("2. P2");
            System.out.println("3. P3");
            System.out.print("Choose an option: ");
            Task.Priority priority = taskCreateModel.parsePriority(scanner.nextLine());
            if (priority != null) return priority;
            System.out.println("Select a valid priority.");
        }
    }

    private Long promptDueDate() {
        for (;;) {
            System.out.print("Enter due date (dd-MM-yyyy): ");
            Long dueDate = taskCreateModel.parseDueDate(scanner.nextLine());
            if (dueDate != null) return dueDate;
            System.out.println("Enter a valid date. Due date must be today or later.");
        }
    }

    void onTaskCreated(Task task) {
        System.out.println();
        System.out.println("Task created successfully.");
        System.out.println("Task id: " + task.getId());
        System.out.print("Do you want to assign this task now? (Y/N): ");
        if (ParseHelper.isYes(scanner.nextLine())) {
            new TaskAssignView(currentUser, task).init();
        } else {
            System.out.println("Task saved without an assignee. Use Assign a task later.");
        }
    }

    void onTaskCreateFailed(String message) {
        System.out.println(message);
    }
}