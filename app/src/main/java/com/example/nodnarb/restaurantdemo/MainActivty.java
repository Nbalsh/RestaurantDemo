package com.example.nodnarb.restaurantdemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivty extends AppCompatActivity {
    RestaurantReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activty);
        mDbHelper = new RestaurantReaderDbHelper(getApplicationContext());


        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // ID'S

        int id = 1;
        String title = "Bob's burgers";
        String location = "1414 Alberta St";

        // END ID'S

    // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(FeedReaderContract.RestaurantEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.RestaurantEntry.COLUMN_NAME_LOCATION, location);

    // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.RestaurantEntry.TABLE_NAME,
                null,
                values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
