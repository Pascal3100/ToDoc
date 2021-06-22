package fr.plopez.todoc.data.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Task;

/**
 * <p>Tasks Repository to hold available Tasks</p>
 *
 * @author Pascal Lopez
 */
public class TasksRepository {

    private final LiveData<List<Task>> taskListLiveData;
    private final LiveData<Integer> numberOfTaskLiveData;
    private final TasksDao tasksDao;

    /**
     * Instantiates a new TasksRepository.
     *
     */
    public TasksRepository(Application application){
        TasksDatabase database = TasksDatabase.getDatabase(application);
        tasksDao = database.tasksDao();
        taskListLiveData = tasksDao.getAllTasks();
        numberOfTaskLiveData = tasksDao.getNumberOfTasks();
    }

    /**
     * Returns the current task List.
     *
     * @return taskListLiveData.
     */
    public LiveData<List<Task>> getTaskListLiveData(){
        return taskListLiveData;
    }

    /**
     * Returns the current Number Of Tasks.
     *
     * @return numberOfTaskLiveData.
     */
    public LiveData<Integer> getNumberOfTaskLiveData(){
        return numberOfTaskLiveData;
    }

    /**
     * Add a new task.
     *
     */
    public void addTask(@NonNull Task newTask){
        TasksDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tasksDao.insertTask(newTask);
            }
        });
    }
    /**
     * Deletes an existing task.
     *
     */
    public void deleteTask(long taskIdToDelete){
        TasksDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tasksDao.deleteTask(taskIdToDelete);
            }
        });
    }
}
