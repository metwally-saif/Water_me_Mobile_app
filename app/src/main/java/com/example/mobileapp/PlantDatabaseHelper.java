package com.example.mobileapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlantDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "plants.db";
    private static final int DATABASE_VERSION = 4;

    // Define table name and columns
    public static final String TABLE_PLANTS = "plants";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INTERVAL = "interval";
    public static final String COLUMN_LAST_WATERED = "last_watered";


    public PlantDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the plants table
        String createTableQuery = "CREATE TABLE " + TABLE_PLANTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_INTERVAL + " TEXT, " +
                COLUMN_LAST_WATERED + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades
        // Drop existing table and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
        onCreate(db);
    }

    public void insertPlant(Plant plant) {
        // Get a writable database
        SQLiteDatabase db = getWritableDatabase();

        // Insert the plant into the database
        String insertQuery = "INSERT INTO " + TABLE_PLANTS + " (" +
                COLUMN_NAME + ", " + COLUMN_INTERVAL + ", " + COLUMN_LAST_WATERED + ") VALUES ('" +
                plant.PlantName() + "', '" + plant.Interval() + "', '" + plant.LastWatered() + "')";
        db.execSQL(insertQuery);

        // Close the database connection
        db.close();
    }

    public void updateWateredDate(Plant plant) {
        // Get a writable database
        SQLiteDatabase db = getWritableDatabase();

        plant.setLastWatered(new java.util.Date().toString());

        // Update the last watered date for the plant
        String updateQuery = "UPDATE " + TABLE_PLANTS + " SET " +
                COLUMN_LAST_WATERED + " = '" + plant.LastWatered() + "' WHERE " +
                COLUMN_NAME + " = '" + plant.PlantName() + "'";
        db.execSQL(updateQuery);

        // Close the database connection
        db.close();
    }

    public void deletePlant(Plant plant) {
        // Get a writable database
        SQLiteDatabase db = getWritableDatabase();

        // Delete the plant from the database
        String deleteQuery = "DELETE FROM " + TABLE_PLANTS + " WHERE " +
                COLUMN_NAME + " = '" + plant.PlantName() + "'";
        db.execSQL(deleteQuery);

        // Close the database connection
        db.close();
    }
}
