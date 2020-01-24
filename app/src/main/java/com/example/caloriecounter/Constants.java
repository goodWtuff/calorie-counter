package com.example.caloriecounter;

import android.provider.BaseColumns;

public class Constants {
    private Constants(){}
    public static final String VIEWPAGER_ITEM = "CalorieCounter.MainActivity.viewpager_item";
    public static final int CODE_LOGIN = 11;

    public static final String LOGIN_WEIGHT = "CalorieCounter.LoginActivity.weight";
    public static final String LOGIN_GENDER = "CalorieCounter.LoginActivity.gender";
    public static final String LOGIN_HEIGHT = "CalorieCounter.LoginActivity.height";
    public static final String LOGIN_AGE = "CalorieCounter.LoginActivity.age";
    public static final String LOGIN_IS_NEEDED = "CalorieCounter.LoginActivity.isNeeded";
    public static final String LOGIN_EXERCISE_COEFFICIENT = "CalorieCounter.LoginActivity.exerciseCoefficient";

    public static final String SHARED_RECOMMENDED_CALORIE = "CalorieCounter.MainActivity.SHARED_RECOMMENDED_CALORIE";

    public static final String RECOMMENDED_CALORIE_AMOUNT = "CalorieCounter.MainActivity.RECOMMENDED_CALORIE_AMOUNT";

    public static final String APP_ID= "a5cdd153";
    public static final String APP_KEY= "d346f2f93c4d110327277e34cbfc9446";
    public static final String FOOD_DB_PARSE_METHOD = "https://api.edamam.com/api/food-database/parser?";

    public static final String TAG = "CalorieCounter.tagg";

    public static final String ADD_CALORIES = "CalorieCounter.FoodSearchActivity.calories";
    public static final String ADD_NAME = "CalorieCounter.FoodSearchActivity.name";
    public static final String ADD_FATS = "CalorieCounter.FoodSearchActivity.fats";
    public static final String ADD_CARBOHYDRATES = "CalorieCounter.FoodSearchActivity.carbohydrates";
    public static final String ADD_PROTEINS= "CalorieCounter.FoodSearchActivity.proteins";
    public static final String INGESTION_TIME = "CalorieCounter.ingestionTime";

    public static final String DATE = "CalorieCounter.date";

}
