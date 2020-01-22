package com.example.caloriecounter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.caloriecounter.Constants;
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
                FoodColumns.TABLE_NAME + " (" +
                FoodColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FoodColumns.COLUMN_NAME + " TEXT NOT NULL, " +
                FoodColumns.COLUMN_CALORIES + " REAL NOT NULL, " +
                FoodColumns.COLUMN_CARBOHYDRATES + " REAL NOT NULL, " +
                FoodColumns.COLUMN_FATS  + " REAL NOT NULL, " +
                FoodColumns.COLUMN_PROTEINS  + " REAL NOT NULL, " +
                FoodColumns.COLUMN_DATE + " TEXT NOT NULL, " +
                FoodColumns.COLUMN_INGESTION_TIME  + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_FOODLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodColumns.TABLE_NAME);
        onCreate(db);
    }

    public void addFoodToDatabase(FoodEntity foodEntity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodColumns.COLUMN_NAME, foodEntity.getName());
        contentValues.put(FoodColumns.COLUMN_CALORIES, foodEntity.getCalories());
        contentValues.put(FoodColumns.COLUMN_PROTEINS, foodEntity.getProteins());
        contentValues.put(FoodColumns.COLUMN_CARBOHYDRATES, foodEntity.getCarbohydrates());
        contentValues.put(FoodColumns.COLUMN_FATS, foodEntity.getFats());
        contentValues.put(FoodColumns.COLUMN_DATE, getDate());
        contentValues.put(FoodColumns.COLUMN_INGESTION_TIME, foodEntity.getIngestionName());

        db.insert(FoodColumns.TABLE_NAME,null,contentValues);
    }

    public void deleteFoodFromDatabase(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FoodColumns.TABLE_NAME,
                FoodColumns._ID + "=" + id, null);
        db.close();
    }


    //Get current date and time
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
