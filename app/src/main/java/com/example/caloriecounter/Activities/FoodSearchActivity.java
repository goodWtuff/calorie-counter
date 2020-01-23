package com.example.caloriecounter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.caloriecounter.AutoComplete.AutoCompleteLoading;
import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Models.FoodEntity;
import com.example.caloriecounter.R;
import com.example.caloriecounter.AutoComplete.AutocompleteAdapter;

public class FoodSearchActivity extends AppCompatActivity {
    private AutoCompleteLoading et_foodSearch;
    private AutocompleteAdapter foodSearchAdapter;
    private String ingestionTime;
    private ProgressBar pb_food_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        //Action bar settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Food search");

        //Get ingestion time
        ingestionTime = getIntent().getStringExtra(Constants.INGESTION_TIME);

        //View initialization;
        initAutoComplete();
    }

    private void initAutoComplete(){
        //Progress bar initialization
        pb_food_search = findViewById(R.id.pb_food_search);
        pb_food_search.setVisibility(View.INVISIBLE);

        et_foodSearch = findViewById(R.id.et_foodSearch);

        //Set progress bar to autocomplete
        et_foodSearch.setLoadingIndicator(pb_food_search);
        foodSearchAdapter = new AutocompleteAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line);
        et_foodSearch.setAdapter(foodSearchAdapter);

        et_foodSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodEntity entity = (FoodEntity) parent.getItemAtPosition(position);

                //If delete this command, some dummy text will be in edit text
                et_foodSearch.setText(entity.getName());

                //Start add food activity
                Intent intent = new Intent(getApplicationContext(),AddFoodActivity.class);
                intent.putExtra(Constants.ADD_CALORIES, entity.getCalories());
                intent.putExtra(Constants.ADD_PROTEINS, entity.getProteins());
                intent.putExtra(Constants.ADD_FATS, entity.getFats());
                intent.putExtra(Constants.ADD_NAME, entity.getName());
                intent.putExtra(Constants.ADD_CARBOHYDRATES, entity.getCarbohydrates());
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
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(FoodSearchActivity.this);
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
