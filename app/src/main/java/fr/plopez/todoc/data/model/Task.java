package fr.plopez.todoc.data.model;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY modified by Pascal Lopez
 */
public class Task {
    /**
     * The unique identifier of the task
     */
    private final long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @NonNull
    private final Project project;

    /**
     * The name of the task
     */
    @NonNull
    private final String name;

    /**
     * The timestamp when the task has been created
     */
    private final long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param id                the unique identifier of the task to set
     * @param project         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */

    public Task(long id, @NonNull Project project, @NonNull String name, long creationTimestamp) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    @NonNull
    public Project getProject() {
        return project;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                creationTimestamp == task.creationTimestamp &&
                project.equals(task.project) &&
                name.equals(task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, name);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
