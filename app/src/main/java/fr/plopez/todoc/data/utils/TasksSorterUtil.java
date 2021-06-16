package fr.plopez.todoc.data.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.plopez.todoc.data.model.Task;
import fr.plopez.todoc.view.main.PossibleSortMethods;

/**
 * Tasks sort util.
 *
 */
public class TasksSorterUtil {

    public static List<Task> sortBy(PossibleSortMethods sortMethod, List<Task> tasks) {
        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new TaskOldComparator());
                break;
        }

        return tasks;
    }

    /**
     * Comparator to sort task from A to Z
     */
    private static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.getName().compareTo(right.getName());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    private static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.getName().compareTo(left.getName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    private static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.getId() - left.getId());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    private static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.getId() - right.getId());
        }
    }
}
