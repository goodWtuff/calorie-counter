package com.example.caloriecounter.Database;

import android.provider.BaseColumns;

public class FoodDatabaseContract {

    public FoodDatabaseContract() {}

    public static final class FoodColumns implements BaseColumns {
        public static final String TABLE_NAME = "foodList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FATS = "fats";
        public static final String COLUMN_CALORIES = "calories";
        public static final String COLUMN_PROTEINS = "proteins";
        public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_INGESTION_TIME = "ingestionTime";
    }

}
