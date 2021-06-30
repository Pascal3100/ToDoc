package fr.plopez.todoc.data.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "project")
public class Project {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "project_id")
    private long id;

    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    @ColorInt
    @ColumnInfo(name = "color")
    private final int color;

     public Project(@NonNull String name, @ColorInt int color) {
         this.name = name;
         this.color = color;
     }

     public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    @NonNull
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                color == project.color &&
                name.equals(project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }
}
