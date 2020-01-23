package com.example.caloriecounter.ui.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Database.FoodDatabaseContract;
import com.example.caloriecounter.R;

public class IngestionFoodEatenAdapter extends RecyclerView.Adapter<IngestionFoodEatenAdapter.ViewHolder> {
    private Cursor cursor;
    private Context context;

    public IngestionFoodEatenAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_NAME));
        double calories = cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_CALORIES));
        long id = cursor.getLong(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns._ID));

        //Make first letter capital and rest lowercase
        holder.tv_foodName.setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
        holder.tv_foodCalories.setText(calories + " kcal");
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_foodName;
        private TextView tv_foodCalories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_foodCalories = itemView.findViewById(R.id.tv_food_calories);
            tv_foodName = itemView.findViewById(R.id.tv_food_name);
        }
    }

    public void swapCursor(Cursor newCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor = newCursor;

        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }
}
