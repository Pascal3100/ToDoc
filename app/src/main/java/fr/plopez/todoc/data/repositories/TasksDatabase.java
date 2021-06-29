package fr.plopez.todoc.data.repositories;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;

import fr.plopez.todoc.R;
import fr.plopez.todoc.data.Dao.ProjectsDao;
import fr.plopez.todoc.data.Dao.TasksDao;
import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.utils.FakeProjectsGenerator;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase {

    public abstract TasksDao tasksDao();
    public abstract ProjectsDao projectsDao();

    private static volatile TasksDatabase INSTANCE;
    static Executor ioExecutor;

    public static TasksDatabase getDatabase(final Context context, Executor executor) {
        if (INSTANCE == null) {
            synchronized (TasksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TasksDatabase.class, context.getString(R.string.tasks_database))
                            .addCallback(new Callback(){
                                @SuppressLint("NewApi")
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // If you want to keep data through app restarts,
                                    // comment out the following block
                                    ioExecutor.execute(() -> {
                                        // Populate the database in the background.
                                        // If you want to start with more words, just add them.
                                        ProjectsDao dao = INSTANCE.projectsDao();
                                        dao.deleteAllProjects();
                                        dao.insertProject(new Project(
                                                context.getString(R.string.Awesome_Project),
                                                context.getColor(R.color.Awesome_Project_color)));
                                        dao.insertProject(new Project(
                                                context.getString(R.string.Miraculous_Actions),
                                                context.getColor(R.color.Miraculous_Actions_color)));
                                        dao.insertProject(new Project(
                                                context.getString(R.string.Circus_Project),
                                                context.getColor(R.color.Circus_Project_color)));
                                    });
                                }
                            })
                            .build();
                    ioExecutor = executor;
                }
            }
        }
        return INSTANCE;
    }
}
