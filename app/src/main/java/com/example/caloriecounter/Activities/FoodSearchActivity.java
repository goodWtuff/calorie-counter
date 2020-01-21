package com.example.caloriecounter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.R;
import com.example.caloriecounter.ui.Adapters.AutocompleteAdapter;

public class FoodSearchActivity extends AppCompatActivity {
    private AutoCompleteTextView et_foodSearch;
    private AutocompleteAdapter foodSearchAdapter;
    private String ingestionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        //Action bar settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add food");

        //Get ingestion time
        ingestionTime = getIntent().getStringExtra(Constants.INGESTION_TIME);

        //View initialization;
        initViews();
    }

    private void initViews(){
        et_foodSearch = findViewById(R.id.et_foodSearch);
        foodSearchAdapter = new AutocompleteAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line);
        et_foodSearch.setAdapter(foodSearchAdapter);

        et_foodSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Start add food activity
                Intent intent = new Intent(getApplicationContext(),FoodSearchActivity.class);
                intent.putExtra(Constants.ADD_CALORIES, 0);
                intent.putExtra(Constants.ADD_PROTEINS, 0);
                intent.putExtra(Constants.ADD_FATS, 0);
                intent.putExtra(Constants.ADD_NAME, 0);
                intent.putExtra(Constants.ADD_CARBOHYDRATES, 0);
                intent.putExtra(Constants.INGESTION_TIME, ingestionTime);

                startActivity(intent);


            }
        });

        et_foodSearch.setOnEditorActionListener(b_searchListener);
    }

    //Search button on keyboard listener
    private TextView.OnEditorActionListener b_searchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEARCH:
                    Toast.makeText(getApplicationContext(),"SEARCH",Toast.LENGTH_SHORT).show();
                    hideKeyboard(FoodSearchActivity.this);
                  break;
            }
            return true;
        }
    };

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
