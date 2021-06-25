package fr.plopez.todoc.data.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import fr.plopez.todoc.data.Dao.TasksDao;
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
    private final Executor ioExecutor;

    /**
     * Instantiates a new TasksRepository.
     *
     */
    public TasksRepository(@NonNull TasksDao tasksDao, @NonNull Executor ioExecutor){
        this.tasksDao = tasksDao;
        this.ioExecutor = ioExecutor;
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
        ioExecutor.execute(new Runnable() {
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
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                tasksDao.deleteTask(taskIdToDelete);
            }
        });
    }
}
