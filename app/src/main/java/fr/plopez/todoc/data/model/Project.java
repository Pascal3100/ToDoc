package fr.plopez.todoc.data.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
