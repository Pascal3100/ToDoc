package fr.plopez.todoc.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import fr.plopez.todoc.data.model.Task;

public class TasksRepository {

    private final MutableLiveData<List<Task>> taskListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> numberOfTaskLiveData = new MutableLiveData<>();
    private final List<Task> taskList = new ArrayList<>();


    public TasksRepository(){
        updateTaskListLiveData();
    }


    public LiveData<List<Task>> getTaskListLiveData(){
        return taskListLiveData;
    }
    public LiveData<Integer> getNumberOfTaskLiveData(){
        return numberOfTaskLiveData;
    }

    public void addTask(@NonNull Task newTask){
        // TODO : peut etre prévoir ici quelques controles (ex: la tache existe deja?)
        taskList.add(newTask);
        updateTaskListLiveData();
    }

    public void deleteTask(@NonNull long taskIdToDelete){
        for (Task task : taskList){
            if (task.getId() == taskIdToDelete){
                taskList.remove(task);
                break;
            }
        }
        updateTaskListLiveData();
    }

    private void updateTaskListLiveData() {
        taskListLiveData.setValue(taskList);
        numberOfTaskLiveData.setValue(taskList.size());
    }

}
