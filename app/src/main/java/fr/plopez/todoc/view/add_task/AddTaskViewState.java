package fr.plopez.todoc.view.add_task;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AddTaskViewState {

    private final long projectId;

    private final String projectName;

    public AddTaskViewState(long projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskViewState that = (AddTaskViewState) o;
        return projectId == that.projectId &&
            Objects.equals(projectName, that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName);
    }

    @NonNull
    @Override
    public String toString() {
        return "AddTaskViewState{" +
            "projectId=" + projectId +
            ", projectName='" + projectName + '\'' +
            '}';
    }
}
