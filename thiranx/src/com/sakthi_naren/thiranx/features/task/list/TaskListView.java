package com.sakthi_naren.thiranx.features.task.list;

import com.sakthi_naren.thiranx.data.dto.Employee;
import com.sakthi_naren.thiranx.data.dto.Task;
import com.sakthi_naren.thiranx.util.ConsoleInput;
import com.sakthi_naren.thiranx.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class TaskListView {

    private final TaskListModel taskListModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskListView(Employee currentUser) {
        this.taskListModel = new TaskListModel(this);
        this.scanner       = ConsoleInput.getScanner();
        this.currentUser   = currentUser;
    }

    public void init() {
        System.out.println();
        System.out.println("My Tasks");
        Long userId = (currentUser == null) ? null : currentUser.getId();
        List<Task> tasks = taskListModel.getMyTasks(userId);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks assigned.");
        } else {
            System.out.println("#   Id   Title                          Priority  Status         Due Date     Assigned By");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                String row = String.format(
                        "%-3d %-4s %-30s %-9s %-14s %-12s %s",
                        (i + 1),
                        t.getId() == null ? "-" : String.valueOf(t.getId()),
                        truncate(safe(t.getTitle()), 30),
                        nameOr(t.getPriority()),
                        nameOr(t.getStatus()),
                        ParseHelper.formatDate(t.getDueDate()),
                        taskListModel.getEmployeeName(t.getAssignedBy()));
                System.out.println(row);
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String truncate(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}