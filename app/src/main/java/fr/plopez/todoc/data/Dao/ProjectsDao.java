package fr.plopez.todoc.data.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.relations.ProjectWithTasks;

@Dao
public interface ProjectsDao {

    // Insert a new project
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProject(Project project);

    // Query for getting all projects
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();

    // Query for deleting all projects
    @Query("DELETE FROM project")
    void deleteAllProjects();

    // Query for requesting all projects
    @Transaction
    @Query("SELECT * FROM project")
    LiveData<List<ProjectWithTasks>> getAllProjectsWithTasks();


}
