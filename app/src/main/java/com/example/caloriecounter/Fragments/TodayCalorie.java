package com.example.caloriecounter.Fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.R;

public class TodayCalorie extends Fragment {
    private int recommendedCalorieAmount;
    private int calorieAmount = 0;
    private TextView tv_calorieProgress;

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
        recommendedCalorieAmount = getArguments().getInt(Constants.RECOMMENDED_CALORIE_AMOUNT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_today_calorie, container, false);
        tv_calorieProgress = v.findViewById(R.id.tv_caloriesProgress);
        tv_calorieProgress.setText(calorieAmount+ " out of " + recommendedCalorieAmount);
        return v;
    }


}
