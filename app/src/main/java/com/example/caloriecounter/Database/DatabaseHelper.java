package com.example.caloriecounter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.caloriecounter.Database.FoodDatabaseContract.*;
import com.example.caloriecounter.Models.FoodEntity;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CalorieCounter.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_FOODLIST_TABLE = "CREATE TABLE " +
                FoodEntry.TABLE_NAME + " (" +
                FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FoodEntry.COLUMN_CARBOHYDRATES + " REAL NOT NULL, " +
                FoodEntry.COLUMN_FATS  + " REAL NOT NULL, " +
                FoodEntry.COLUMN_PROTEINS  + " REAL NOT NULL, " +
                FoodEntry.COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_DATE, " +
                FoodEntry.COLUMN_INGESTION_TIME  + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_FOODLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodEntry.TABLE_NAME);
        onCreate(db);
    }

    private void addFoodToDatabase(FoodEntity foodEntity){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodEntry.COLUMN_NAME, foodEntity.getName());
        contentValues.put(FoodEntry.COLUMN_PROTEINS, foodEntity.getProteins());
        contentValues.put(FoodEntry.COLUMN_CARBOHYDRATES, foodEntity.getCarbohydrates());
        contentValues.put(FoodEntry.COLUMN_FATS, foodEntity.getFats());
        contentValues.put(FoodEntry.COLUMN_INGESTION_TIME, foodEntity.getIngestionTime());
        db.insert(FoodEntry.TABLE_NAME,null,contentValues);
    }


}
