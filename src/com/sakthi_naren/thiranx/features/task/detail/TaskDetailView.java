package com.sivasuryap.thiranx.features.task.detail;

import com.sivasuryap.thiranx.data.dto.Employee;
import com.sivasuryap.thiranx.data.dto.Task;
import com.sivasuryap.thiranx.data.dto.TaskStatusHistory;
import com.sivasuryap.thiranx.util.ConsoleInput;
import com.sivasuryap.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskDetailView {

    private final TaskDetailModel taskDetailModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskDetailView(Employee currentUser) {
        this.taskDetailModel = new TaskDetailModel(this);
        this.scanner         = ConsoleInput.getScanner();
        this.currentUser     = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("Task details");
        List<Task> tasks = taskDetailModel.getVisibleTasks(currentUser);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to view.");
            return;
        }

        Task task = pickTask(tasks);
        if (task == null) return;

        printTask(task);
        printHistory(task);

        System.out.print("Press Enter to return: ");
        scanner.nextLine();
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

    private void printTask(Task task) {
        System.out.println();
        System.out.println("Id           : " + task.getId());
        System.out.println("Title        : " + task.getTitle());
        System.out.println("Description  : " + task.getDescription());
        System.out.println("Priority     : " + nameOr(task.getPriority()));
        System.out.println("Status       : " + nameOr(task.getStatus()));
        System.out.println("Assigned by  : " + taskDetailModel.getEmployeeName(task.getAssignedBy()));
        System.out.println("Assigned to  : " + taskDetailModel.getEmployeeName(task.getAssignedTo()));
        System.out.println("Due date     : " + ParseHelper.formatDate(task.getDueDate()));
        System.out.println("Created at   : " + ParseHelper.formatDateTime(task.getCreatedTime()));
        System.out.println("Updated at   : " + ParseHelper.formatDateTime(task.getUpdatedTime()));
        System.out.println("Completed at : " + ParseHelper.formatDateTime(task.getCompletedTime()));
    }

    private void printHistory(Task task) {
        System.out.println();
        System.out.println("Status history");
        List<TaskStatusHistory> history = taskDetailModel.getHistoryFor(task.getId());
        if (history.isEmpty()) {
            System.out.println("No status history yet.");
            return;
        }
        for (TaskStatusHistory entry : history) {
            System.out.println(ParseHelper.formatDateTime(entry.getChangedTime())
                    + " | " + taskDetailModel.getEmployeeName(entry.getChangedBy())
                    + " | " + nameOr(entry.getOldStatus()) + " -> " + nameOr(entry.getNewStatus())
                    + " | " + (entry.getRemarks() == null ? "" : entry.getRemarks()));
        }
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}