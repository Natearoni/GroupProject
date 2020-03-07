package com.repella.groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.repella.groupproject.data.Task;

import java.util.ArrayList;

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
    public void onBindViewHolder(final TasksAdapter.ViewHolder viewHolder, final int position) {
        com.repella.groupproject.data.Task task = taskList.get(position);

        TextView nameView = viewHolder.nameTaskView;
        TextView locationView = viewHolder.locationTaskView;
        nameView.setText(task.getTask_name());
        locationView.setText(task.getLocation_id());

        ImageButton delButton = viewHolder.delButton;
        ImageButton modButton = viewHolder.modButton;
        ImageButton completeButton = viewHolder.completeButton;
        //TODO DELETE BUTTON HERE
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
        public ArrayList<com.repella.groupproject.data.Task> taskList;

        public TasksAdapter(ArrayList<com.repella.groupproject.data.Task> tasks) {
            taskList = tasks;
        }
    }
