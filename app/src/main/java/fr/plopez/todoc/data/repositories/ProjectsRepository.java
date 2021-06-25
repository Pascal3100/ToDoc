package fr.plopez.todoc.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.plopez.todoc.data.Dao.ProjectsDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.relations.ProjectWithTasks;

public class ProjectsRepository {

    @NonNull
    private final ProjectsDao projectsDao;

    public ProjectsRepository(@NonNull ProjectsDao projectsDao){
        this.projectsDao = projectsDao;
    }

    public LiveData<List<Project>> getProjectListLiveData(){
        return projectsDao.getAllProjects();
    }

    public LiveData<List<ProjectWithTasks>> getProjectWithTasksLiveData(){
        return projectsDao.getAllProjectsWithTasks();
    }
}
