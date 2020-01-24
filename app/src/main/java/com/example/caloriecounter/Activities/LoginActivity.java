package com.example.caloriecounter.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriecounter.Constants;
import com.example.caloriecounter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText et_enter_height;
    private EditText et_enter_weight;
    private EditText et_enter_age;
    private Spinner spinner;
    private String spinnerSelectedItem;
    private ArrayAdapter<CharSequence> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Registration");

        et_enter_height = findViewById(R.id.et_enter_height);
        et_enter_weight = findViewById(R.id.et_enter_weight);
        et_enter_age = findViewById(R.id.et_enter_age);
        radioGroup = findViewById(R.id.radioGroup);
        initSpinner();

        //Apply all variables and go to main
        FloatingActionButton b_login = findViewById(R.id.b_login);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if fields are empty
                if (et_enter_height.getText().toString().trim().length() < 1 || et_enter_height.getText().toString().trim().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Enter all data", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if height is wrong
                if (Integer.parseInt(et_enter_height.getText().toString()) < 100 || Integer.parseInt(et_enter_height.getText().toString()) > 230) {
                    Toast.makeText(getApplicationContext(), "Wrong height", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if weight is wrong
                if (Integer.parseInt(et_enter_weight.getText().toString()) < 30 || Integer.parseInt(et_enter_weight.getText().toString()) > 300) {
                    Toast.makeText(getApplicationContext(), "Wrong weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check if age is wrong
                if (Integer.parseInt(et_enter_age.getText().toString()) < 6 || Integer.parseInt(et_enter_age.getText().toString()) > 100) {
                    Toast.makeText(getApplicationContext(), "Wrong age", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Check wat gender was selected
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                //Initializing variables
                String gender = radioButton.getText().toString();
                int weight = Integer.parseInt(et_enter_weight.getText().toString());
                int height = Integer.parseInt(et_enter_height.getText().toString());
                int age = Integer.parseInt(et_enter_age.getText().toString());

                //Setting result
                Intent result = new Intent();
                result.putExtra(Constants.LOGIN_GENDER, gender);
                result.putExtra(Constants.LOGIN_HEIGHT, height);
                result.putExtra(Constants.LOGIN_WEIGHT, weight);
                result.putExtra(Constants.LOGIN_AGE, age);
                result.putExtra(Constants.LOGIN_EXERCISE_COEFFICIENT, calculateExerciseCoefficient(spinnerSelectedItem));

                setResult(RESULT_OK, result);

                //Sets, that's login is no more needed
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor edit = prefs.edit();
                edit.putBoolean(Constants.LOGIN_IS_NEEDED, Boolean.FALSE);
                edit.apply();


                finish();


            }
        });
    }

    private void initSpinner(){
        spinner = findViewById(R.id.spinner_exercise);
        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.exercise_answers,R.layout.spinner_exercise_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_exercise_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(spinnerAdapter);
    }
    private double calculateExerciseCoefficient(String spinnerSelectedItem){
        String[] spinnerItems = getResources().getStringArray(R.array.exercise_answers);
        double exerciseCoefficient = 1.2;

        if(spinnerSelectedItem.equals(spinnerItems[0])){
            exerciseCoefficient = 1.2;
        } else if(spinnerSelectedItem.equals(spinnerItems[1])){
            exerciseCoefficient = 1.375;
        } else if(spinnerSelectedItem.equals(spinnerItems[2])){
            exerciseCoefficient = 1.55;
        } else if(spinnerSelectedItem.equals(spinnerItems[3])){
            exerciseCoefficient = 1.725;
        } else if(spinnerSelectedItem.equals(spinnerItems[4])){
            exerciseCoefficient = 1.9;
        }
        return exerciseCoefficient;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelectedItem = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
