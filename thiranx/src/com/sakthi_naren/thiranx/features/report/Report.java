package com.sakthi_naren.thiranx.features.report;

public class Report {

    private int totalEmployees;
    private int totalManagers;
    private int totalEmployeesOnly;
    private int totalTasks;
    private int openTasks;
    private int inProgressTasks;
    private int completedTasks;
    private int onHoldTasks;
    private int cancelledTasks;
    private int reopenedTasks;
    private int unassignedTasks;
    private int p1Tasks;
    private int p2Tasks;
    private int p3Tasks;
    private int overdueTasks;

    public int getTotalEmployees()     { return totalEmployees; }
    public void setTotalEmployees(int totalEmployees) { this.totalEmployees = totalEmployees; }

    public int getTotalManagers()      { return totalManagers; }
    public void setTotalManagers(int totalManagers) { this.totalManagers = totalManagers; }

    public int getTotalEmployeesOnly() { return totalEmployeesOnly; }
    public void setTotalEmployeesOnly(int totalEmployeesOnly) { this.totalEmployeesOnly = totalEmployeesOnly; }

    public int getTotalTasks()         { return totalTasks; }
    public void setTotalTasks(int totalTasks) { this.totalTasks = totalTasks; }

    public int getOpenTasks()          { return openTasks; }
    public void setOpenTasks(int openTasks) { this.openTasks = openTasks; }

    public int getInProgressTasks()    { return inProgressTasks; }
    public void setInProgressTasks(int inProgressTasks) { this.inProgressTasks = inProgressTasks; }

    public int getCompletedTasks()     { return completedTasks; }
    public void setCompletedTasks(int completedTasks) { this.completedTasks = completedTasks; }

    public int getOnHoldTasks()        { return onHoldTasks; }
    public void setOnHoldTasks(int onHoldTasks) { this.onHoldTasks = onHoldTasks; }

    public int getCancelledTasks()     { return cancelledTasks; }
    public void setCancelledTasks(int cancelledTasks) { this.cancelledTasks = cancelledTasks; }

    public int getReopenedTasks()      { return reopenedTasks; }
    public void setReopenedTasks(int reopenedTasks) { this.reopenedTasks = reopenedTasks; }

    public int getUnassignedTasks()    { return unassignedTasks; }
    public void setUnassignedTasks(int unassignedTasks) { this.unassignedTasks = unassignedTasks; }

    public int getP1Tasks()            { return p1Tasks; }
    public void setP1Tasks(int p1Tasks) { this.p1Tasks = p1Tasks; }

    public int getP2Tasks()            { return p2Tasks; }
    public void setP2Tasks(int p2Tasks) { this.p2Tasks = p2Tasks; }

    public int getP3Tasks()            { return p3Tasks; }
    public void setP3Tasks(int p3Tasks) { this.p3Tasks = p3Tasks; }

    public int getOverdueTasks()       { return overdueTasks; }
    public void setOverdueTasks(int overdueTasks) { this.overdueTasks = overdueTasks; }
}