package com.repella.groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View taskView = inflater.inflate(R.layout.task_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(taskView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TasksAdapter.ViewHolder viewHolder, int position) {
        Task task = taskList.get(position);

        TextView nameView = viewHolder.nameTaskView;
        nameView.setText(task.getName());
        TextView locationView = viewHolder.locationTaskView;
        locationView.setText(task.getLocation());

        ImageButton delButton = viewHolder.delButton;
        ImageButton modButton = viewHolder.modButton;
        ImageButton completeButton = viewHolder.completeButton;
        //TODO DELETE BUTTON HERE
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTaskView;
        public TextView locationTaskView;
        public ImageButton delButton;
        public ImageButton modButton;
        public ImageButton completeButton;

        public ViewHolder(View itemView) {

            super(itemView);

            locationTaskView = (TextView) itemView.findViewById(R.id.location_name);
            nameTaskView = (TextView) itemView.findViewById(R.id.task_name);
            delButton = (ImageButton) itemView.findViewById(R.id.delete_button);
            modButton = (ImageButton) itemView.findViewById(R.id.modify_button);
            completeButton = (ImageButton) itemView.findViewById(R.id.complete_button);
        }
    }
        private List<Task> taskList;

        public TasksAdapter(List<Task> tasks) {
            taskList = tasks;
        }
    }
