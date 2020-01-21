package com.example.caloriecounter.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Activities.FoodSearchActivity;
import com.example.caloriecounter.Constants;
import com.example.caloriecounter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class IngestionListAdapter extends RecyclerView.Adapter<IngestionListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> ingestionList = new ArrayList<>();

    public IngestionListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ingestionList.add("Breakfast");
        ingestionList.add("Morning snack");
        ingestionList.add("Lunch");
        ingestionList.add("Afternoon snack");
        ingestionList.add("Dinner");
        ingestionList.add("Second dinner");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingestion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_name.setText(ingestionList.get(position));
        holder.b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open food search activity with ingestion title
                Intent intent = new Intent(context, FoodSearchActivity.class);
                intent.putExtra(Constants.INGESTION_TIME, ingestionList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        FloatingActionButton b_add;
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b_add = itemView.findViewById(R.id.b_add_ingestion);
            tv_name = itemView.findViewById(R.id.tv_name_ingestion);
        }
    }
}
