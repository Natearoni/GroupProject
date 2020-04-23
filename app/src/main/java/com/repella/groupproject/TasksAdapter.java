package com.repella.groupproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static com.repella.groupproject.MainActivity.dbm;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View taskView = inflater.inflate(R.layout.task_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(taskView);
        //Log.d("ADP", "onBindViewHolder: am i called?");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TasksAdapter.ViewHolder viewHolder, final int position) { //never called
        com.repella.groupproject.data.Task task = taskList.get(position);
        //Log.d("ADP", "onBindViewHolder: " + taskList.get(position).getTask_name());
        TextView nameView = viewHolder.nameTaskView;
        TextView locationView = viewHolder.locationTaskView;
        nameView.setText(task.getTask_name());
        locationView.setText(task.getLocation_name());

        ImageButton delButton = viewHolder.delButton;
        ImageButton modButton = viewHolder.modButton;
        final ImageButton completeButton = viewHolder.completeButton;

        //Delete Button
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbm.delete(viewHolder.nameTaskView.getText().toString(), "Task");
                taskList.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        //Complete Button
        completeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (completeButton.isSelected()){
                    completeButton.setSelected(false);
                    completeButton.getBackground().setColorFilter(Color.argb(100, 255, 255, 255), PorterDuff.Mode.SRC_OVER);

                } else {
                    completeButton.setSelected(true);
                    completeButton.getBackground().setColorFilter(Color.argb(100, 0, 255, 0), PorterDuff.Mode.SRC_OVER);
                }
            }
        });
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
        public ArrayList<com.repella.groupproject.data.Task> taskList;

        public TasksAdapter(ArrayList<com.repella.groupproject.data.Task> tasks) {
            taskList = tasks;
        }
    }
