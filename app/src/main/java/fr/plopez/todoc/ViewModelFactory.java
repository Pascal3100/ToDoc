package fr.plopez.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.view.main.MainActivityFragment;
import fr.plopez.todoc.view.main.TasksViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    private final ProjectsRepository projectsRepository = new ProjectsRepository();
    private final TasksRepository tasksRepository = new TasksRepository();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            return (T) new TasksViewModel(projectsRepository, tasksRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
