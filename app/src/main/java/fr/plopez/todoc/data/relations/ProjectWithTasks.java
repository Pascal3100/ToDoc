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
    private final Project project;

    @Relation(
            parentColumn = "project_id",
            entityColumn = "project_id"
    )
    private final List<Task> tasks;

    public ProjectWithTasks(Project project, List<Task> tasks) {
        this.project = project;
        this.tasks = tasks;
    }
    public Project getProject() {
        return project;
    }

    public List<Task> getTasks() {
        return tasks;
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
