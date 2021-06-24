package fr.plopez.todoc.view.add_task;

import androidx.annotation.Nullable;

public enum AddTaskViewAction {
    TASK_OK(null),
    DISPLAY_TASK_EMPTY_MESSAGE("Task name can't be empty."),
    DISPLAY_PROJECT_EMPTY_MESSAGE("Project name can't be empty.");

    @Nullable
    private final String message;

    AddTaskViewAction(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
