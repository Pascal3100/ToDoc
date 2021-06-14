package fr.plopez.todoc.view.main;

/**
 * Listener for deleting tasks
 */
public interface DeleteTaskListener {
    /**
     * Called when a task needs to be deleted.
     *
     * @param taskIdToDelete the task that needs to be deleted
     */
    void onDeleteTask(long taskIdToDelete);
}
