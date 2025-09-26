package com.example.etiketkereta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO {
    private DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean register(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);

        long result = db.insert("users", null, cv);
        return result != -1;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        boolean success = cursor.getCount() > 0;
        cursor.close();
        return success;
    }
}
