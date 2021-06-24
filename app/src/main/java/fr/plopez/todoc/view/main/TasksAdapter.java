package fr.plopez.todoc.view.main;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import fr.plopez.todoc.R;
import fr.plopez.todoc.view.model.TaskViewState;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY modified by Pascal Lopez
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private final List<TaskViewState> tasksViewStateList = new ArrayList<>();

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     */
    TasksAdapter(@NonNull final DeleteTaskListener deleteTaskListener) {
        this.deleteTaskListener = deleteTaskListener;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasksViewStateListUpdated the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull List<TaskViewState> tasksViewStateListUpdated) {
        tasksViewStateList.clear();
        tasksViewStateList.addAll(tasksViewStateListUpdated);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(tasksViewStateList.get(position));
    }

    @Override
    public int getItemCount() {
        return tasksViewStateList.size();
    }


    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY modified by Pascal Lopez
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        private long taskId;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView,
                       @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(view -> deleteTaskListener.onDeleteTask(taskId));
        }

        /**
         * Binds a task to the item view.
         *
         * @param taskViewState the task to bind in the item view
         */
        void bind(TaskViewState taskViewState) {
            taskId = taskViewState.getTaskId();
            lblTaskName.setText(taskViewState.getTaskName());
            imgDelete.setTag(taskViewState);
            imgProject.setImageTintList(ColorStateList.valueOf(taskViewState.getProjectColor()));
            lblProjectName.setText(taskViewState.getProjectName());
        }
    }
}
