package fr.plopez.todoc.data.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.plopez.todoc.view.main.PossibleSortMethods;
import fr.plopez.todoc.view.model.TaskViewState;

/**
 * Tasks sort util.
 *
 */
public class TasksSorterUtil {

    public static List<TaskViewState> sortBy(
            PossibleSortMethods sortMethod,
            List<TaskViewState> taskViewStateList) {

        if (taskViewStateList == null) {
            return new ArrayList<>();
        }

        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(taskViewStateList, new TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(taskViewStateList, new TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(taskViewStateList, new TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(taskViewStateList, new TaskOldComparator());
                break;
            case BY_PROJECT:
                Collections.sort(taskViewStateList, new TaskProjectComparator());
                break;
        }

        return taskViewStateList;
    }

    /**
     * Comparator to sort task from A to Z
     */
    private static class TaskAZComparator implements Comparator<TaskViewState> {
        @Override
        public int compare(TaskViewState left, TaskViewState right) {
            return left.getTaskName().compareTo(right.getTaskName());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    private static class TaskZAComparator implements Comparator<TaskViewState> {
        @Override
        public int compare(TaskViewState left, TaskViewState right) {
            return right.getTaskName().compareTo(left.getTaskName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    private static class TaskRecentComparator implements Comparator<TaskViewState> {
        @Override
        public int compare(TaskViewState left, TaskViewState right) {
            return (int) (right.getTaskId() - left.getTaskId());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    private static class TaskOldComparator implements Comparator<TaskViewState> {
        @Override
        public int compare(TaskViewState left, TaskViewState right) {
            return (int) (left.getTaskId() - right.getTaskId());
        }
    }

    /**
     * Comparator to sort task by project
     */
    private static class TaskProjectComparator implements Comparator<TaskViewState> {
        @Override
        public int compare(TaskViewState left, TaskViewState right) {
            return left.getProjectName().compareTo(right.getProjectName());
        }
    }
}
