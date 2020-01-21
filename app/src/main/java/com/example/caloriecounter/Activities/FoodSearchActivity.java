package com.example.caloriecounter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.caloriecounter.R;

public class FoodSearchActivity extends AppCompatActivity {
    private AutoCompleteTextView et_foodSearch;
 //   private EditText rv_foodSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        //Action bar settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add food");

        //View initialization
        initViews();
    }

    private void initViews(){
        et_foodSearch = findViewById(R.id.et_foodSearch);
//        rv_foodSearch = findViewById(R.id.rv_foodSearch);
    }
}
