package fr.plopez.todoc.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Task;

/**
 * <p>Tasks Repository to hold available Tasks</p>
 *
 * @author Pascal Lopez
 */
public class TasksRepository {

    private final MutableLiveData<List<Task>> taskListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> numberOfTaskLiveData = new MutableLiveData<>();
    private final List<Task> taskList = new ArrayList<>();
    private long taskId = 0;

    /**
     * Instantiates a new TasksRepository.
     *
     */
    public TasksRepository(){
        updateTaskListLiveData();
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
        // TODO : peut etre prévoir ici quelques controles???
        taskList.add(newTask);
        updateTaskListLiveData();
    }
    /**
     * Deletes an existing task.
     *
     */
    public void deleteTask(@NonNull long taskIdToDelete){
        for (Task task : taskList){
            if (task.getId() == taskIdToDelete){
                taskList.remove(task);
                break;
            }
        }
        updateTaskListLiveData();
    }
    /**
     * Updates tasks list
     *
     */
    private void updateTaskListLiveData() {
        taskListLiveData.setValue(taskList);
        numberOfTaskLiveData.setValue(taskList.size());
    }

    /**
     * Generates tasks ids
     *
     */
    public long generateTaskId() {
        return taskId++;
    }
}
