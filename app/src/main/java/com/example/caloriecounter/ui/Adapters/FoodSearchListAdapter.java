package com.example.caloriecounter.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Activities.AddFoodActivity;
import com.example.caloriecounter.Activities.FoodSearchActivity;
import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Models.FoodEntity;
import com.example.caloriecounter.R;

import java.util.ArrayList;

public class FoodSearchListAdapter extends RecyclerView.Adapter<FoodSearchListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FoodEntity> foodEntities = new ArrayList<>();

    public FoodSearchListAdapter(Context context, ArrayList<FoodEntity> foodEntities) {
        this.context = context;
        this.foodEntities = foodEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_foodCalories.setText(foodEntities.get(position).getCalories() + " kcal");
        holder.tv_foodName.setText(foodEntities.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodEntity entity = foodEntities.get(position);

                //Start add food activity
                Intent intent = new Intent(context, AddFoodActivity.class);
                intent.putExtra(Constants.ADD_CALORIES, entity.getCalories());
                intent.putExtra(Constants.ADD_PROTEINS, entity.getProteins());
                intent.putExtra(Constants.ADD_FATS, entity.getFats());
                intent.putExtra(Constants.ADD_NAME, entity.getName());
                intent.putExtra(Constants.ADD_CARBOHYDRATES, entity.getCarbohydrates());
                intent.putExtra(Constants.INGESTION_TIME, FoodSearchActivity.getIngestionTime());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_foodName;
        private TextView tv_foodCalories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foodName = itemView.findViewById(R.id.tv_food_name);
            tv_foodCalories = itemView.findViewById(R.id.tv_food_calories);
        }
    }
}
