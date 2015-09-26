package com.example.nodnarb.restaurantdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FinalSelectionActivity extends AppCompatActivity {

    private ListView finalSelectionListView;
    private ArrayAdapter<String> listAdapter ;
    private RestaurantReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_selection);

        InsertData();

        // Set the ArrayAdapter as the ListView's adapter.
        finalSelectionListView.setAdapter(listAdapter);
    }

    private void InsertData()
    {
        // Find the ListView resource.
        finalSelectionListView = (ListView) findViewById( R.id.finalSelectionListView );

        String[] restaurants = GetDataFromDB();

        ArrayList<String> restaurantNames = new ArrayList<String>();
        restaurantNames.addAll(Arrays.asList(restaurants));

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, restaurantNames);
    }


    private String[] GetDataFromDB()
    {
        mDbHelper = new RestaurantReaderDbHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(RestaurantReaderContract.RestaurantEntry.TABLE_NAME, null, null);

        // ID'S
        int id = 1;
        String title = "Jeb's Pizzas";
        String location = "3535 Montreabola St";
        int anotherId = id++;
        String anotherTitle = "Bob's Burgers";
        String anotherLocation = "1414 Alberta St";

        // END ID'S

        // Create a new map of value, where column names are the keys

        ContentValues value = PutValues(id, title, location);
        ContentValues anotherValue = PutValues(anotherId, anotherTitle, anotherLocation);

        // Insert the new row, returning the primary key value of the new row
        db.insert(
                RestaurantReaderContract.RestaurantEntry.TABLE_NAME,
                null,
                value);
        db.insert(
                RestaurantReaderContract.RestaurantEntry.TABLE_NAME,
                null,
                anotherValue);


        // READ FROM DB
        SQLiteDatabase dbReader = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                RestaurantReaderContract.RestaurantEntry._ID,
                RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID + " DESC";

        String select = "SELECT DISTINCT " + RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE +
                " FROM " + RestaurantReaderContract.RestaurantEntry.TABLE_NAME;
        Cursor c = dbReader.rawQuery(select, null);

        int columnIndex = c.getColumnIndex(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE);
        String[] restaurantNames = new String[c.getCount()];
        int j = 0;
        for (c.moveToFirst(); j < c.getCount(); c.moveToNext())
        {
            restaurantNames[j] = c.getString(columnIndex);
            j++;
        }
        c.close();

        return restaurantNames;
    }

    private ContentValues PutValues(int id, String title, String location, ContentValues values)
    {
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE, title);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_LOCATION, location);
        return values;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_selection, menu);
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

    private ContentValues PutValues(int id, String title, String location)
    {
        ContentValues values = new ContentValues();
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE, title);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_LOCATION, location);
        return values;
    }
}
