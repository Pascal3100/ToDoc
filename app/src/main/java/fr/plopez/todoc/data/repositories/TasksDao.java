package fr.plopez.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.plopez.todoc.data.model.Task;

@Dao
public interface TasksDao {

    // Insert a new task
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(Task task);

    // Delete an existing task
    @Query("DELETE FROM task WHERE task_id = :taskId")
    void deleteTask(long taskId);

    // Query for getting all tasks
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

    // Query for getting all tasks
    @Query("SELECT COUNT(task_id) FROM task")
    LiveData<Integer> getNumberOfTasks();
}
