package com.example.caloriecounter.ui.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Database.DatabaseHelper;
import com.example.caloriecounter.Database.FoodDatabaseContract;
import com.example.caloriecounter.Fragments.FoodInfoDialog;
import com.example.caloriecounter.Models.FoodEntity;
import com.example.caloriecounter.R;

public class TodayFoodEatenAdapter extends RecyclerView.Adapter<TodayFoodEatenAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public TodayFoodEatenAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.food_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        //Parse data from database
        final String name = cursor.getString(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_NAME));
        final String ingestionTime = cursor.getString(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_INGESTION_TIME));
        final double calories = cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_CALORIES));
        final double fats = cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_FATS));
        final double carbohydrates = cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_CARBOHYDRATES));
        final double proteins = cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_PROTEINS));
        long id  = cursor.getLong(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns._ID));

        //Make first letter capital and rest lowercase
        holder.tv_food_name.setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
        holder.tv_food_calories.setText(calories + " kcal");
        holder.itemView.setTag(id);


        //If item clicked, show dialog with food info
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodEntity foodEntity = new FoodEntity(name,calories,fats,carbohydrates,proteins,ingestionTime);
                showInfoDialog(foodEntity);
            }
        });

    }

    //Shows dialog with food info
    private void showInfoDialog(FoodEntity entity){
        FoodInfoDialog dialog = new FoodInfoDialog(entity);
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),Constants.TAG);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_food_name;
        private TextView tv_food_calories;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_food_name = itemView.findViewById(R.id.tv_food_name);
            tv_food_calories = itemView.findViewById(R.id.tv_food_calories);
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
