package fr.plopez.todoc.utils;

import android.app.Application;

public class App extends Application {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}