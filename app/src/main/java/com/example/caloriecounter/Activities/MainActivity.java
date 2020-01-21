package com.example.caloriecounter.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Helpers.SharedPreferenceHelper;
import com.example.caloriecounter.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecounter.ui.Adapters.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabs;

    private int recommendedCalorieAmount;

    private static final String TAG = "MainActivity.tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loading data from shared prefs
        try{
            recommendedCalorieAmount = SharedPreferenceHelper.getSharedPreferenceInt(getApplicationContext(),Constants.SHARED_RECOMMENDED_CALORIE, 0);
        }catch (NullPointerException e){

        }

        //View pager initialization
        initViewPager();
    }

    //View pager initialization
    private void initViewPager(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), recommendedCalorieAmount);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    //Counts recommended calorie amount
    private int countRecommendedCalorieAmount(String gender, int height, int weight, int age){
        double recommendedCalorie = 0;
        //If user male
        if(gender.equals("Male")){
            recommendedCalorie = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        }

        //If user female or probably female
        else if(gender.equals("Female") || gender.equals("Other")){
          recommendedCalorie = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
        return (int) Math.round(recommendedCalorie);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get variables from login activity
        if(requestCode == Constants.CODE_LOGIN){
            if(resultCode == RESULT_OK){
                String gender = data.getStringExtra(Constants.LOGIN_GENDER);
                int height = data.getIntExtra(Constants.LOGIN_HEIGHT, 0);
                int weight = data.getIntExtra(Constants.LOGIN_WEIGHT,0);
                int age = data.getIntExtra(Constants.LOGIN_AGE, 0);

                Log.d(TAG, "weight " + weight + " height " + height + " age " + age);

                //Set recommended calorie amount
                recommendedCalorieAmount = countRecommendedCalorieAmount(gender,height,weight, age);
                SharedPreferenceHelper.setSharedPreferenceInt(getApplicationContext(),Constants.SHARED_RECOMMENDED_CALORIE,recommendedCalorieAmount);

                //Update view pager
                initViewPager();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //If user isn't registered, start login activity
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean needToLogin = prefs.getBoolean(Constants.LOGIN_IS_NEEDED, true);
        if(needToLogin) {
            startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), Constants.CODE_LOGIN);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        //Save view pager position
        bundle.putInt(Constants.VIEWPAGER_ITEM, viewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Restore view pager position
        viewPager.setCurrentItem(savedInstanceState.getInt(Constants.VIEWPAGER_ITEM));
    }

}