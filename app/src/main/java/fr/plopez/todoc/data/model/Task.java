package fr.plopez.todoc.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class Task {
    /**
     * The unique identifier of the task
     */
    @NonNull
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
    @NonNull
    private final long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param id                the unique identifier of the task to set
     * @param project         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */

    public Task(long id, Project project, @NonNull String name, long creationTimestamp) {
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
    @NonNull
    public long getId() {
        return id;
    }

    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    @Nullable
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

    /**
     * Returns the time stamp of the task.
     *
     * @return the creationTimestamp of the task
     */
    @NonNull
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
