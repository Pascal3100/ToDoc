package fr.plopez.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.plopez.todoc.data.repositories.FilterRepository;
import fr.plopez.todoc.data.repositories.ProjectsRepository;
import fr.plopez.todoc.data.repositories.TasksDatabase;
import fr.plopez.todoc.data.repositories.TasksRepository;
import fr.plopez.todoc.utils.App;
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

    private static final int NUMBER_OF_THREADS = 4;

    private final ProjectsRepository projectsRepository;
    private final TasksRepository tasksRepository;
    private final FilterRepository filterRepository = new FilterRepository();

    private ViewModelFactory() {
        Executor ioExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        TasksDatabase appDatabase = TasksDatabase.getDatabase(App.getApplication(), ioExecutor);

        projectsRepository = new ProjectsRepository(
            appDatabase.projectsDao()
        );
        tasksRepository = new TasksRepository(
            appDatabase.tasksDao(),
            ioExecutor
        );
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            return (T) new TasksViewModel(projectsRepository, tasksRepository, filterRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
