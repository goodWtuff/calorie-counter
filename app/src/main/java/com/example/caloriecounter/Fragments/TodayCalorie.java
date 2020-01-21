package com.example.caloriecounter.Fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.R;

import io.feeeei.circleseekbar.CircleSeekBar;

public class TodayCalorie extends Fragment {
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

        pb_calories = v.findViewById(R.id.pb_calorie);
        pb_fats = v.findViewById(R.id.pb_fats);
        pb_carbohydrates = v.findViewById(R.id.pb_carbohydrates);
        pb_proteins = v.findViewById(R.id.pb_proteins);

        rv_foodTodayEaten = v.findViewById(R.id.rv_todayFoodEaten);

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
        tv_calorieProgress.setText(calorieAmount+ " out of " + recommendedCalorieAmount);
        tv_carbohydratesProgress.setText(carbohydratesAmount+ " out of " + recommendedCarbohydratesAmount);
        tv_proteinsProgress.setText(proteinsAmount + " out of " + recommendedProteinsAmount);
        tv_fatsProgress.setText(fatsAmount+ " out of " +recommendedFatsAmount);

        pb_proteins.setCurProcess(proteinsAmount);
        pb_carbohydrates.setCurProcess(carbohydratesAmount);
        pb_fats.setCurProcess(fatsAmount);
        pb_calories.setCurProcess(calorieAmount);
    }

}
