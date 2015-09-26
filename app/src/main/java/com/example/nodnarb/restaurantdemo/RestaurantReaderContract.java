package com.example.nodnarb.restaurantdemo;

import android.provider.BaseColumns;

/**
 * Created by Nodnarb on 2015-09-20.
 */
public final class RestaurantReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public RestaurantReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class RestaurantEntry implements BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TITLE = "rName";
        public static final String COLUMN_NAME_LOCATION = "rLocation";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES_MAIN =
            "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
                    RestaurantEntry._ID + " INTEGER PRIMARY KEY," +
                    RestaurantEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    RestaurantEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    RestaurantEntry.COLUMN_NAME_LOCATION + TEXT_TYPE +

    // Any other options for the CREATE command
            " )";

    private static final String SQL_CREATE_ENTRIES_ACCEPTED =
            "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
                    RestaurantEntry._ID + " INTEGER PRIMARY KEY," +
                    RestaurantEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    RestaurantEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    RestaurantEntry.COLUMN_NAME_LOCATION + TEXT_TYPE +
                    // Any other options for the CREATE command
                    " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RestaurantEntry.TABLE_NAME;

    public String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES_MAIN;
    }
    public String getSqlCreateEntriesAccepted() {
        return SQL_CREATE_ENTRIES_ACCEPTED;
    }
    public String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

}

