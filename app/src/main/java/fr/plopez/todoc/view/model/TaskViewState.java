package fr.plopez.todoc.view.model;

public class TaskViewState {

    private final long taskId;
    private final String taskName;
    private final int projectColor;
    private final String projectName;

    public TaskViewState(long taskId, String taskName, int projectColor, String projectName) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.projectColor = projectColor;
        this.projectName = projectName;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getProjectColor() {
        return projectColor;
    }

    public String getProjectName() {
        return projectName;
    }
}
