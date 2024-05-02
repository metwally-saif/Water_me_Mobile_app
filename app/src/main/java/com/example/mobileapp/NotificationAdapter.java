package com.example.mobileapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final Context context;
    private final ArrayList<Plant> data;
    private final PlantDatabaseHelper databaseHelper;

    public NotificationAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        this.databaseHelper = new PlantDatabaseHelper(context);
        loadDataFromDatabase(); // Load data from SQLite database
    }

    private void loadDataFromDatabase() {
        // Retrieve plant data from the database using the database helper
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                PlantDatabaseHelper.COLUMN_ID,
                PlantDatabaseHelper.COLUMN_NAME,
                PlantDatabaseHelper.COLUMN_INTERVAL,
                PlantDatabaseHelper.COLUMN_LAST_WATERED
        };
        Cursor cursor = db.query(
                PlantDatabaseHelper.TABLE_PLANTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(PlantDatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(PlantDatabaseHelper.COLUMN_NAME));
                String interval = cursor.getString(cursor.getColumnIndexOrThrow(PlantDatabaseHelper.COLUMN_INTERVAL));
                String lastWatered = cursor.getString(cursor.getColumnIndexOrThrow(PlantDatabaseHelper.COLUMN_LAST_WATERED));
                Plant plant = new ConcretePlant(name, interval, lastWatered);
                plant.setId(id);
                System.out.println(plant.LastWatered());
                data.add(plant);
            }
            cursor.close();
        }
        // Close the database
        db.close();
    }

    @NonNull
    @NotNull
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.plant_card_view, viewGroup, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.ViewHolder viewHolder, int i) {
        Plant plant = data.get(i);
        // based on date now and the last watered date check if the plant needs to be watered if so send a notification and update the last watered date



        viewHolder.plantName.setText(plant.PlantName());
        viewHolder.timeForWatering.setText(plant.LastWatered());


    }

    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView plantName, timeForWatering;

        ViewHolder(View view) {
            super(view);
            plantName = view.findViewById(R.id.textView);
            timeForWatering = view.findViewById(R.id.textView2);
        }
    }
}
