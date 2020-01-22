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
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Database.DatabaseHelper;
import com.example.caloriecounter.Database.FoodDatabaseContract;
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

        String name = cursor.getString(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_NAME));
        final double calories = cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_CALORIES));
        final long id  = cursor.getLong(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns._ID));


        Log.d(Constants.TAG, "onBindViewHolder: " + cursor.getString(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_DATE)));

        //Make first letter capital and rest lowercase
        holder.tv_food_name.setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
        holder.tv_food_calories.setText(calories + " kcal");

        holder.b_food_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.TAG, "onClick: ");

                //Remove from database
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteFoodFromDatabase(id);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_food_name;
        private ImageButton b_food_delete;
        private TextView tv_food_calories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_food_name = itemView.findViewById(R.id.tv_food_name);
            tv_food_calories = itemView.findViewById(R.id.tv_food_calories);
            b_food_delete = itemView.findViewById(R.id.b_food_delete);
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
