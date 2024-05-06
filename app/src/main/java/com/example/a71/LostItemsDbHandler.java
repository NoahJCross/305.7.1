package com.example.a71;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LostItemsDbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lostitemsdb";
    private static final String TABLE_NAME = "lostitems";
    private static final String ID_COL = "id";
    private static final String LOST_ITEM_COL = "lostitem";
    private static final String PHONE_NUM_COL = "phonenumber";
    private static final String DESCRIPTION_COL = "description";
    private static final String DATE_COL = "date";
    private static final String LOCATION_COL = "location";
    private static final String FOUND_COL = "found";


    public LostItemsDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LostItemS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LOST_ITEM_COL + " TEXT NOT NULL,"
                + PHONE_NUM_COL + " TEXT NOT NULL,"
                + DESCRIPTION_COL + " TEXT NOT NULL,"
                + DATE_COL + " TEXT NOT NULL,"
                + LOCATION_COL + " TEXT NOT NULL,"
                + FOUND_COL + " INTEGER NOT NULL )";
        db.execSQL(CREATE_LostItemS_TABLE);
    }

    public void addLostItem(LostItem lostItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOST_ITEM_COL, lostItem.getLostItemName());
        values.put(PHONE_NUM_COL, lostItem.getPhoneNumber());
        values.put(DESCRIPTION_COL, lostItem.getDescription());
        values.put(DATE_COL, lostItem.getDate());
        values.put(LOCATION_COL, lostItem.getLocation());
        values.put(FOUND_COL, lostItem.getFound());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public LostItem getLostItemById(long itemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(itemId)});


        LostItem lostItem = null;
        if (cursor.moveToFirst()) {
            lostItem = new LostItem();
            lostItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID_COL)));
            lostItem.setLostItemName(cursor.getString(cursor.getColumnIndexOrThrow(LOST_ITEM_COL)));
            lostItem.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(PHONE_NUM_COL)));
            lostItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_COL)));
            lostItem.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL)));
            lostItem.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_COL)));
            lostItem.setFound(cursor.getInt(cursor.getColumnIndexOrThrow(FOUND_COL)));
        }

        db.close();
        return lostItem;
    }


    public List<LostItem> getLostItems() {
        List<LostItem> lostItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                LostItem lostItem = new LostItem();
                lostItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ID_COL)));
                lostItem.setLostItemName(cursor.getString(cursor.getColumnIndexOrThrow(LOST_ITEM_COL)));
                lostItem.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(PHONE_NUM_COL)));
                lostItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_COL)));
                lostItem.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL)));
                lostItem.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_COL)));
                lostItem.setFound(cursor.getInt(cursor.getColumnIndexOrThrow(FOUND_COL)));
                lostItemList.add(lostItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lostItemList;
    }

    public void deleteLostItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

