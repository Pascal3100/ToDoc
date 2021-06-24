package fr.plopez.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.App;
import fr.plopez.todoc.view.add_task.AddTaskViewModel;
import fr.plopez.todoc.view.main.MainActivityViewModel;

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

    private final ProjectsRepository projectsRepository = new ProjectsRepository(App.getApplication());
    private final TasksRepository tasksRepository = new TasksRepository(App.getApplication());
    private final FilterRepository filterRepository = new FilterRepository();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(projectsRepository, tasksRepository, filterRepository);
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(projectsRepository, tasksRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
