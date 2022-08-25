package com.example.myshoppinglist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myshoppinglist.Model.ShoppingModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "shoppingListDatabase";
    private static final String SHOPPING_TABLE = "shopping";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";

    private static final String CREATE_SHOPPING_TABLE = "CREATE TABLE " + SHOPPING_TABLE +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ITEM + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop the older tables
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_TABLE);
        // Create table again
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertItem(ShoppingModel item){
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item.getItem());
        cv.put(STATUS, 0);
        db.insert(SHOPPING_TABLE, null, cv);
    }

    public List<ShoppingModel> getAllItems() {
        List<ShoppingModel> itemList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            // return all rows from db
            cur = db.query(SHOPPING_TABLE, null, null, null,
                    null, null, null, null);
            if (cur != null) {
                if(cur.moveToFirst()){
                    do{
                        ShoppingModel item = new ShoppingModel();
                        item.setId(cur.getInt(cur.getColumnIndex(ID)));
                        item.setItem(cur.getString(cur.getColumnIndex(ITEM)));
                        item.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        itemList.add(item);
                    } while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return itemList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(SHOPPING_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateItem(int id, String item){
        ContentValues cv = new ContentValues();
        cv.put(ITEM, item);
        db.update(SHOPPING_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteItem(int id){
        db.delete(SHOPPING_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }

}
