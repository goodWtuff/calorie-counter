package com.example.caloriecounter.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.caloriecounter.Database.FoodContract.*;

import androidx.annotation.Nullable;

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
                FoodEntry.COLUMN_CARBOHYDRATES + " INTEGER NOT NULL, " +
                FoodEntry.COLUMN_FATS  + " INTEGER NOT NULL, " +
                FoodEntry.COLUMN_PROTEINS  + " INTEGER NOT NULL, " +
                FoodEntry.COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                FoodEntry.COLUMN_INGESTION_TIME  + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_FOODLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodEntry.TABLE_NAME);
        onCreate(db);
    }
}
