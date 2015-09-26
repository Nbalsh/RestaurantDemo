package com.example.nodnarb.restaurantdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivty extends Activity {

  private ListView mainListView ;
  private ArrayAdapter<String> listAdapter ;
  private RestaurantReaderDbHelper mDbHelper;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main_activty);
      InsertData();

    // Set the ArrayAdapter as the ListView's adapter.
      mainListView.setAdapter(listAdapter);

    // React to user clicks on item
      SetOnClickListener();
  }

    private void InsertData()
    {
        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.mainListView );

        String[] restaurants = GetDataFromDB();
        
        ArrayList<String>restaurantNames = new ArrayList<String>();
        restaurantNames.addAll(Arrays.asList(restaurants));

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, restaurantNames);
    }

    private String[] GetDataFromDB()
    {
//        setContentView(R.layout.activity_main_activty);
        mDbHelper = new RestaurantReaderDbHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(RestaurantReaderContract.RestaurantEntry.TABLE_NAME, null, null);

        // ID'S
        int id = 1;
        String title = "Bob's Burgers";
        String location = "1414 Alberta St";
        int anotherId = id++;
        String anotherTitle = "Jeb's Pizzas";
        String anotherLocation = "3535 Montreabola St";

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

        String selecet = "SELECT DISTINCT " + RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE +
                " FROM " + RestaurantReaderContract.RestaurantEntry.TABLE_NAME;
        Cursor c = db.rawQuery(selecet, null);

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

    private ContentValues PutValues(int id, String title, String location)
    {
        ContentValues values = new ContentValues();
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_TITLE, title);
        values.put(RestaurantReaderContract.RestaurantEntry.COLUMN_NAME_LOCATION, location);
        return values;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // We know that each row in the adapter is a Map
        String item =  listAdapter.getItem(aInfo.position);

        menu.setHeaderTitle("Options for " + item);
        menu.add(1, 1, 1, "Details");
        menu.add(1, 2, 2, "Delete");
    }

    private void SetOnClickListener()
    {
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, final int position,
                                    final long id) {
                // We know the View is a TextView so we can cast it
                final TextView clickedView = (TextView) view;
                registerForContextMenu(mainListView);
//                Toast.makeText(MainActivty.this, "Item with id [" + id + "] - Position [" + position + "] - Restaurant [" + clickedView.getText() + "]", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivty.this)
                        .setTitle("Likey Like")
                        .setMessage("Add " + clickedView.getText() + " to your liked restaurants?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivty.this, "id:" + "[" + position + "]", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public void startFinalSelectionActivity(View view){
        Intent intent = new Intent(this, FinalSelectionActivity.class);
        startActivity(intent);
    }
}