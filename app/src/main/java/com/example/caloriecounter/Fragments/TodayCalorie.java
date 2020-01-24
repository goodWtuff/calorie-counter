package com.example.caloriecounter.Fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Database.DatabaseHelper;
import com.example.caloriecounter.Database.FoodDatabaseContract;
import com.example.caloriecounter.R;
import com.example.caloriecounter.ui.Adapters.TodayFoodEatenAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.feeeei.circleseekbar.CircleSeekBar;

public class TodayCalorie extends Fragment{
    //Calorie, fats, proteins, carbohydrates variables
    private int recommendedCalorieAmount;
    private int calorieAmount = 0;
    private int recommendedFatsAmount;
    private int fatsAmount = 0;
    private int recommendedProteinsAmount;
    private int proteinsAmount = 0;
    private int recommendedCarbohydratesAmount;
    private int carbohydratesAmount = 0;

    //Calorie, fats, proteins, carbohydrates views
    private TextView tv_fatsProgress;
    private TextView tv_calorieProgress;
    private TextView tv_proteinsProgress;
    private TextView tv_carbohydratesProgress;

    //Calorie, fats, proteins, carbohydrates progressBars
    private CircleSeekBar pb_calories;
    private CircleSeekBar pb_carbohydrates;
    private CircleSeekBar pb_fats;
    private CircleSeekBar pb_proteins;

    //Recycler view
    private RecyclerView rv_foodTodayEaten;
    private TodayFoodEatenAdapter foodListAdapter;

    //Database
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static String date;


    private Button b_setDate;

    //Fragment new instance helper
    public static TodayCalorie newInstance(int recommendedCalorieAmount) {
        TodayCalorie fragment = new TodayCalorie();
        Bundle args = new Bundle();
        args.putInt(Constants.RECOMMENDED_CALORIE_AMOUNT, recommendedCalorieAmount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Database initialization
        date = getTodayDate();
        Log.d(Constants.TAG, "onCreate: " + date);
        databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Calculate nutrients recommended amount
        calculateNutrientsRecommended();

        //Update statistic
        updateStatisticViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View elements initialization
        View v = inflater.inflate(R.layout.fragment_today_calorie, container, false);

        //View initialization
        initializeViews(v);

        //Calculate nutrients recommended amount
        calculateNutrientsRecommended();

        //Update statistic
        updateStatisticViews();

        return v;
    }

    //View initialization
    private void initializeViews(View v){
        tv_calorieProgress = v.findViewById(R.id.tv_caloriesProgress);
        tv_fatsProgress = v.findViewById(R.id.tv_fatsProgress);
        tv_proteinsProgress = v.findViewById(R.id.tv_proteinsProgress);
        tv_carbohydratesProgress = v.findViewById(R.id.tv_carbohydratesProgress);

        b_setDate = v.findViewById(R.id.b_setDate);
        b_setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        b_setDate.setText(date);

        pb_calories = v.findViewById(R.id.pb_calorie);
        pb_fats = v.findViewById(R.id.pb_fats);
        pb_carbohydrates = v.findViewById(R.id.pb_carbohydrates);
        pb_proteins = v.findViewById(R.id.pb_proteins);

        rv_foodTodayEaten = v.findViewById(R.id.rv_todayFoodEaten);
        //Recycler view initialization
        initRecyclerView();
    }

    //Calculate nutrients recommended amount
    private void calculateNutrientsRecommended(){
        recommendedCalorieAmount = getArguments().getInt(Constants.RECOMMENDED_CALORIE_AMOUNT);
        recommendedCarbohydratesAmount = (int) (recommendedCalorieAmount * 0.5);
        recommendedFatsAmount = (int) (recommendedCalorieAmount * 0.20);
        recommendedProteinsAmount = (int) (recommendedCalorieAmount * 0.30);

        pb_calories.setMaxProcess(recommendedCalorieAmount);
        pb_carbohydrates.setMaxProcess(recommendedCarbohydratesAmount);
        pb_fats.setMaxProcess(recommendedFatsAmount);
        pb_proteins.setMaxProcess(recommendedProteinsAmount);
    }

    //Update statistic
    private void updateStatisticViews(){
        //Calculates current nutrients amount according to information from database
        calculateCurrentNutrients(getFoodByDate(date));

        tv_calorieProgress.setText(calorieAmount+ " out of " + recommendedCalorieAmount);
        tv_carbohydratesProgress.setText(carbohydratesAmount+ " out of " + recommendedCarbohydratesAmount);
        tv_proteinsProgress.setText(proteinsAmount + " out of " + recommendedProteinsAmount);
        tv_fatsProgress.setText(fatsAmount+ " out of " +recommendedFatsAmount);

        pb_proteins.setCurProcess(proteinsAmount);
        pb_carbohydrates.setCurProcess(carbohydratesAmount);
        pb_fats.setCurProcess(fatsAmount);
        pb_calories.setCurProcess(calorieAmount);

        foodListAdapter.swapCursor(getFoodByDate(date));
    }

    //Recycler view initialization
    private void initRecyclerView(){
        rv_foodTodayEaten.setLayoutManager(new LinearLayoutManager(getContext()));
        foodListAdapter = new TodayFoodEatenAdapter(getContext(),getFoodByDate(date));
        foodListAdapter.swapCursor(getFoodByDate(date));
        rv_foodTodayEaten.setAdapter(foodListAdapter);

        //If item swiped, delete it
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Delete from database, change cursor and update stats
               removeItem(viewHolder);
            }
        }).attachToRecyclerView(rv_foodTodayEaten);
    }

    //Delete from database, change cursor and update stats
    private void removeItem(RecyclerView.ViewHolder viewHolder){
        databaseHelper.deleteFoodFromDatabase((long) viewHolder.itemView.getTag());
        foodListAdapter.swapCursor(getFoodByDate(date));
        updateStatisticViews();
    }

    //Get current date and time
    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy.MM.dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Get food from database by date
    private Cursor getFoodByDate(String date){
        String selection = FoodDatabaseContract.FoodColumns.COLUMN_DATE + " like '%" + date + "%'";
        return database.query(FoodDatabaseContract.FoodColumns.TABLE_NAME,null,
                selection,
                null,
                null,
                null,
                null);
    }

    //Calculates current nutrients amount according to information from database
    private void calculateCurrentNutrients(Cursor cursor){
        //Its important to make vars null
        proteinsAmount = 0;
        fatsAmount = 0;
        calorieAmount = 0;
        carbohydratesAmount = 0;

        while (cursor.moveToNext()){
            proteinsAmount += (int) Math.round(cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_PROTEINS)));
            fatsAmount += (int) Math.round(cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_FATS)));
            calorieAmount += (int) Math.round(cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_CALORIES)));
            carbohydratesAmount += (int) Math.round(cursor.getDouble(cursor.getColumnIndex(FoodDatabaseContract.FoodColumns.COLUMN_CARBOHYDRATES)));
        }

    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        TodayCalorie.date = date;
    }

    private void showDatePickerDialog(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDateFromString(date));

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), R.style.Theme_AppCompat_Light_Dialog,
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1;
            String monthString = String.valueOf(month);
            if(monthString.length()== 1){
                monthString = "0" + monthString;
            }
            date = year + "." + monthString + "." +dayOfMonth;
            b_setDate.setText(date);
            updateStatisticViews();
        }
    };

    private Date parseDateFromString(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        Date currentDate = null;
        try {
            currentDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate;
    }
}
