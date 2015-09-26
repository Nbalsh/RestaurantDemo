package com.example.nodnarb.restaurantdemo;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivty extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    RestaurantReaderDbHelper mDbHelper;
    private static final String TAG = "MyActivity";
    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

//        setContentView(R.layout.activity_main_activty);
        mDbHelper = new RestaurantReaderDbHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // ID'S

        int id = 1;
        String title = "Bob's Burgers";
        String location = "1414 Alberta St";
        String anotherTitle = "Jeb's Pizzas";
        String anotherLocation = "3535 Montreabola St";

        // END ID'S

    // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE, title);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_LOCATION, location);

        id++;
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE, anotherTitle);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_LOCATION, anotherLocation);

    // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                RestaurantReaderContract.RestaurantEntry.TABLE_NAME,
                null,
                values);
        
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

        String selecet = "SELECT DISTINCT " + RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE +
                " FROM " + RestaurantReaderContract.RestaurantEntry.TABLE_NAME;
        Cursor c = db.rawQuery(selecet, null);

        int columnIndex = c.getColumnIndex(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE);
        String[] restaurantNames = new String[c.getCount()];
        int j = 0;
        for (c.moveToFirst(); j < c.getCount(); c.moveToNext()){
            restaurantNames[j] = c.getString(columnIndex);
            j++;
        }
        c.close();
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
