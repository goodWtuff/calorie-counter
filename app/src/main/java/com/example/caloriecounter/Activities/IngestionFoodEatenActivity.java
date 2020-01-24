package com.example.caloriecounter.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Database.DatabaseHelper;
import com.example.caloriecounter.Database.FoodDatabaseContract;
import com.example.caloriecounter.Fragments.TodayCalorie;
import com.example.caloriecounter.R;
import com.example.caloriecounter.ui.Adapters.IngestionFoodEatenAdapter;

public class IngestionFoodEatenActivity extends AppCompatActivity {
    //Recycler view
    private String ingestionTime;
    private RecyclerView rv_ingestionFoodEaten;
    private IngestionFoodEatenAdapter rv_adapter;

    //Database
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingestion_food_eaten);

        date = TodayCalorie.getDate();

        //initialize database
        databaseHelper = new DatabaseHelper(getApplicationContext());
        database = databaseHelper.getWritableDatabase();

        //initialize ingestion time
        ingestionTime = getIntent().getStringExtra(Constants.INGESTION_TIME);

        //app bar settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ingestionTime);

        //Recycler view initialization
        initRecyclerView();
    }

    private void initRecyclerView(){
        rv_ingestionFoodEaten = findViewById(R.id.rv_ingestionFoodEaten);
        rv_ingestionFoodEaten.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_adapter = new IngestionFoodEatenAdapter(getFoodByIngestionAndDate(date, ingestionTime), getApplicationContext());
        rv_adapter.swapCursor(getFoodByIngestionAndDate(date, ingestionTime));
        rv_ingestionFoodEaten.setAdapter(rv_adapter);


        //If item swiped, delete it
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Delete from database, change cursor and update stats
                databaseHelper.deleteFoodFromDatabase((long) viewHolder.itemView.getTag());
                rv_adapter.swapCursor(getFoodByIngestionAndDate(date, ingestionTime));
            }
        }).attachToRecyclerView(rv_ingestionFoodEaten);
    }


    //Parse data from database
    private Cursor getFoodByIngestionAndDate(String date, String ingestionTime){
       String selection = FoodDatabaseContract.FoodColumns.COLUMN_INGESTION_TIME + " like '%" + ingestionTime + "%'" +
               " and " + FoodDatabaseContract.FoodColumns.COLUMN_DATE + " like '%" + date + "%'";
       return database.query(FoodDatabaseContract.FoodColumns.TABLE_NAME,null,
               selection,
               null,
               null,
               null,
               null);
    }

}
