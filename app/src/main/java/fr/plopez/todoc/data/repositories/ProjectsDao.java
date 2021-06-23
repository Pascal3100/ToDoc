package fr.plopez.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.ProjectWithTasks;

@Dao
public interface ProjectsDao {

    // Insert a new project
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProject(Project project);

    // Delete an existing project
    @Delete
    void deleteProject(Project project);

    // Query for getting all projects
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();

    // Query for deleting all projects
    @Query("DELETE FROM project")
    void deleteAllProjects();

    @Transaction
    @Query("SELECT * FROM project")
    LiveData<List<ProjectWithTasks>> getAllProjectsWithTasks();
}
