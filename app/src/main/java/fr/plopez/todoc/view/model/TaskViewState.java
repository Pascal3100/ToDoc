package fr.plopez.todoc.view.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "TaskViewState{" +
                "taskName='" + taskName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewState that = (TaskViewState) o;
        return taskId == that.taskId &&
                projectColor == that.projectColor &&
                Objects.equals(taskName, that.taskName) &&
                Objects.equals(projectName, that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskName, projectColor, projectName);
    }
}
