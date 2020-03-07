package com.repella.groupproject;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View taskView = inflater.inflate(R.layout.task_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(taskView);
        Log.d("ADP", "onBindViewHolder: am i called?");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TasksAdapter.ViewHolder viewHolder, final int position) { //never called
        com.repella.groupproject.data.Task task = taskList.get(position);
        Log.d("ADP", "onBindViewHolder: " + taskList.get(position).getTask_name());
        TextView nameView = viewHolder.nameTaskView;
        TextView locationView = viewHolder.locationTaskView;
        nameView.setText(task.getTask_name());


        //Location loc = MainActivity.dbm.selectLocationByTaskLocationId(task.getLocation_id());
        //Location loc = MainActivity.dbm.selectLocationByTaskLocationId(0);


        /*dbm.insert(new Location("testing", 0, 0, 0));
        ArrayList<Location> locs = MainActivity.dbm.selectAllLocations();
        for(int i = 0;  i < locs.size(); i++)
            Log.d("last problem", "onBindViewHolder: " + locs.get(i).getId());*/
        //if(loc == null)
            //Log.d("last problem", "onBindViewHolder: NULL LOC.");
        //locationView.setText(loc.getName()); //last problem.
        locationView.setText("DEFAULT_LOC");



        //Log.d("last problem", "onBindViewHolder: " + task.getLocation_id());
        ImageButton delButton = viewHolder.delButton;
        final ImageButton modButton = viewHolder.modButton;
        final ImageButton completeButton = viewHolder.completeButton;
        final boolean complete = false;
        //TODO DELETE BUTTON HERE
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeButton.setColorFilter(Color.argb(255,255,255,0));
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
