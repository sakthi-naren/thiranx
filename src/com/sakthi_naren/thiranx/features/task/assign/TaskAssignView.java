package com.sivasuryap.thiranx.features.task.assign;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.util.ConsoleInput;
import com.sivasuryap.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskAssignView {

    private final TaskAssignModel taskAssignModel;
    private final Scanner scanner;
    private final Employee currentUser;
    private final AssignMode mode;
    private final Task preselectedTask;

    public TaskAssignView(Employee currentUser, AssignMode mode) {
        this.taskAssignModel  = new TaskAssignModel(this);
        this.scanner          = ConsoleInput.getScanner();
        this.currentUser      = currentUser;
        this.mode             = mode;
        this.preselectedTask  = null;
    }

    public TaskAssignView(Employee currentUser, Task preselectedTask) {
        this.taskAssignModel  = new TaskAssignModel(this);
        this.scanner          = ConsoleInput.getScanner();
        this.currentUser      = currentUser;
        this.mode             = AssignMode.MANAGER_ASSIGN;
        this.preselectedTask  = preselectedTask;
    }

    public void init() {
        System.out.println();
        Task task = (preselectedTask != null) ? preselectedTask : pickTask();
        if (task == null) return;

        List<Employee> assignees = taskAssignModel.listAssignees(currentUser, mode);
        if (assignees.isEmpty()) {
            System.out.println("No employees available to assign.");
            return;
        }

        Employee assignee = pickAssignee(assignees);
        if (assignee == null) return;

        System.out.print("Confirm assigning task '" + task.getTitle()
                + "' to " + assignee.getName() + "? (Y/N): ");
        if (!ParseHelper.isYes(scanner.nextLine())) {
            System.out.println("Assignment cancelled.");
            return;
        }
        taskAssignModel.assign(task, assignee.getId());
    }

    private Task pickTask() {
        List<Task> tasks = taskAssignModel.listAssignableTasks(mode, currentUser);
        if (tasks.isEmpty()) {
            if (mode == AssignMode.MANAGER_ASSIGN) {
                System.out.println("No unassigned tasks to assign.");
            } else {
                System.out.println("You have no tasks to reassign.");
            }
            return null;
        }
        String header = (mode == AssignMode.MANAGER_ASSIGN)
                ? "Select a task to assign:"
                : "Select a task to reassign:";
        for (;;) {
            System.out.println(header);
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle()
                        + " [" + nameOr(t.getPriority())
                        + ", " + nameOr(t.getStatus()) + "]");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= tasks.size()) {
                return tasks.get(index - 1);
            }
            System.out.println("Select a valid option.");
        }
    }

    private Employee pickAssignee(List<Employee> assignees) {
        for (;;) {
            System.out.println("Select an employee:");
            for (int i = 0; i < assignees.size(); i++) {
                Employee e = assignees.get(i);
                System.out.println((i + 1) + ". " + e.getName()
                        + " (" + e.getEmployeeId()
                        + ", " + nameOr(e.getRole()) + ")");
            }
            System.out.print("Choose an option: ");
            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= assignees.size()) {
                return assignees.get(index - 1);
            }
            System.out.println("Select a valid option.");
        }
    }

    void onAssignSuccessful(Task task) {
        System.out.println("Task assigned successfully.");
    }

    void onAssignFailed(String message) {
        System.out.println(message);
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}