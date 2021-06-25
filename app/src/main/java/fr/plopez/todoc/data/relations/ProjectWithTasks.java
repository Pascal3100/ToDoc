package fr.plopez.todoc.data.relations;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;

public class ProjectWithTasks {
    @Embedded
    private Project project;

    @Relation(
            parentColumn = "project_id",
            entityColumn = "project_id"
    )
    private List<Task> tasks;

    public Project getProject() {
        return project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectWithTasks that = (ProjectWithTasks) o;
        return Objects.equals(project, that.project) &&
                Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, tasks);
    }

    @NonNull
    @Override
    public String toString() {
        return "ProjectWithTasks{" +
                "project=" + project +
                ", tasks=" + tasks +
                '}';
    }
}
