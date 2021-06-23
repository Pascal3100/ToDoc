package fr.plopez.todoc.data.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.plopez.todoc.data.model.Project;
import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.data.utils.FakeProjectsGenerator;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase {

    public abstract TasksDao tasksDao();
    public abstract ProjectsDao projectsDao();

    private static volatile TasksDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static Executor databaseWriteExecutor;

    public static TasksDatabase getDatabase(final Context context, Executor executor) {
        if (INSTANCE == null) {
            synchronized (TasksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TasksDatabase.class, "tasks_database")
                            .addCallback(roomDatabaseCallback)
                            .build();

                    databaseWriteExecutor = executor;
                }
            }
        }
        return INSTANCE;
    }

    // Build initial db state
    private final static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ProjectsDao dao = INSTANCE.projectsDao();
                dao.deleteAllProjects();

                FakeProjectsGenerator fakeProjectsGenerator = new FakeProjectsGenerator();
                for (Project project : fakeProjectsGenerator.generateFakeProjects()){
                    dao.insertProject(project);
                }
            });
        }
    };

}
