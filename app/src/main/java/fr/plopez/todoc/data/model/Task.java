package fr.plopez.todoc.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "task",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "project_id",
                childColumns = "project_id"))
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private long id;

    @NonNull
    @ColumnInfo(name = "task_name")
    private final String name;

    @ColumnInfo(name = "project_id")
    private long projectId;

    @ColumnInfo(name = "time_stamp")
    private final long creationTimestamp;



    public Task(@NonNull long projectId, @NonNull String name, long creationTimestamp) {
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
    @NonNull
    public long getProjectId() {
        return projectId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    // Generated code

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                projectId == task.projectId &&
                creationTimestamp == task.creationTimestamp &&
                name.equals(task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, name);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
