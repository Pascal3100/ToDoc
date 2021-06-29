package fr.plopez.todoc.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import fr.plopez.todoc.data.Dao.TasksDao;
import fr.plopez.todoc.data.model.Task;

public class TasksRepository {

    private final LiveData<List<Task>> taskListLiveData;
    private final TasksDao tasksDao;
    private final Executor ioExecutor;

    public TasksRepository(@NonNull TasksDao tasksDao, @NonNull Executor ioExecutor){
        this.tasksDao = tasksDao;
        this.ioExecutor = ioExecutor;
        taskListLiveData = tasksDao.getAllTasks();
    }

    public LiveData<List<Task>> getTaskListLiveData(){
        return taskListLiveData;
    }

    public void addTask(@NonNull Task newTask){
        ioExecutor.execute(() -> tasksDao.insertTask(newTask));
    }

    public void deleteTask(long taskIdToDelete){
        ioExecutor.execute(() -> tasksDao.deleteTask(taskIdToDelete));
    }
}
