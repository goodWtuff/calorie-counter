package com.example.caloriecounter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Database.DatabaseHelper;
import com.example.caloriecounter.Database.FoodDatabaseContract;
import com.example.caloriecounter.Models.FoodEntity;
import com.example.caloriecounter.R;
import com.example.caloriecounter.ui.Adapters.IngestionListAdapter;

public class AddFoodActivity extends AppCompatActivity {
    //Views
    private TextView tv_dish_name;
    private TextView tv_calories;
    private TextView tv_fats;
    private TextView tv_carbohydrates;
    private TextView tv_proteins;
    private EditText et_portion_size;
    private Button b_addFood;

    //Food parameters
    private double calories;
    private double fats;
    private double proteins;
    private double carbohydrates;
    private String ingestionName;
    private String foodName;
    private double weightCoeff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //Food variables initialization
        initFoodVariables();

        //View initialization
        initViews();

        //Update statistics
        updateStatisticViews();
    }

    //View initialization
    private void initViews(){
        tv_calories = findViewById(R.id.tv_calories);
        tv_fats = findViewById(R.id.tv_fats);
        tv_carbohydrates = findViewById(R.id.tv_carbohydrates);
        tv_proteins = findViewById(R.id.tv_proteins);

        tv_dish_name = findViewById(R.id.tv_dish_name);
        //Make first letter capital and rest lowercase
        tv_dish_name.setText(foodName.substring(0, 1).toUpperCase() + foodName.substring(1).toLowerCase());

        //When user update weight, statistics and data also update
        et_portion_size = findViewById(R.id.et_portion_size);
        et_portion_size.setOnEditorActionListener(applyWeightListener);

        b_addFood = findViewById(R.id.b_add);
        b_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_portion_size.getText().toString().trim().length()<1){
                    Toast.makeText(getApplicationContext(),"Enter portion size",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(et_portion_size.getText().toString()) <= 0){
                    Toast.makeText(getApplicationContext(),"Portion cant be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                weightCoeff = Integer.parseInt(et_portion_size.getText().toString()) / 100;

                //Add to database
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.addFoodToDatabase(new FoodEntity(foodName,calories,fats,carbohydrates,proteins,ingestionName));

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    //Keyboard apply listener
    private TextView.OnEditorActionListener applyWeightListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            updateStatisticViews();
            return false;
        }
    };

    //Food variables initialization
    private void initFoodVariables(){
       calories = getIntent().getDoubleExtra(Constants.ADD_CALORIES,0);
       fats = getIntent().getDoubleExtra(Constants.ADD_FATS,0);
       proteins = getIntent().getDoubleExtra(Constants.ADD_PROTEINS,0);
       carbohydrates = getIntent().getDoubleExtra(Constants.ADD_CARBOHYDRATES,0);
       ingestionName = getIntent().getStringExtra(Constants.INGESTION_TIME);
       foodName = getIntent().getStringExtra(Constants.ADD_NAME);
    }

    private void updateStatisticViews(){
        //Numbers can be really big, so we round them to 3 places
        weightCoeff = Double.parseDouble(et_portion_size.getText().toString()) / 100;
        tv_calories.setText("Calories: " + round(calories*weightCoeff,1) + " kcal");
        tv_fats.setText("Fats: " + round(fats*weightCoeff,3) + "g");
        tv_carbohydrates.setText("Carbohydrates: " + round(carbohydrates*weightCoeff,3) + "g");
        tv_proteins.setText("Proteins: " + round(proteins*weightCoeff,3) + "g");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
