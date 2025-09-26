package com.example.etiketkereta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "eticketdb";
    private static final int DB_VERSION = 4; // naikkan versi biar reset tabel

    // Tabel User
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Tabel Tiket
    private static final String TABLE_TIKET = "tiket";
    private static final String COL_TIKET_ID = "id";
    private static final String COL_NAMA = "nama";
    private static final String COL_TUJUAN = "tujuan";
    private static final String COL_TANGGAL = "tanggal";
    private static final String COL_JUMLAH = "jumlah";
    private static final String COL_HARGA = "harga";
    private static final String COL_BAYAR = "bayar"; // jumlah uang dibayar

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Buat tabel user
        String createUsers = "CREATE TABLE " + TABLE_USERS + " ("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT UNIQUE, "
                + COL_PASSWORD + " TEXT)";
        db.execSQL(createUsers);

        // Buat tabel tiket
        String createTiket = "CREATE TABLE " + TABLE_TIKET + " ("
                + COL_TIKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAMA + " TEXT, "
                + COL_TUJUAN + " TEXT, "
                + COL_TANGGAL + " TEXT, "
                + COL_JUMLAH + " INTEGER, "
                + COL_HARGA + " INTEGER, "
                + COL_BAYAR + " INTEGER)";
        db.execSQL(createTiket);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIKET);
        onCreate(db);
    }

    // =================== LOGIN/REGISTER ===================
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    // =================== TIKET CRUD ===================
    public void addBooking(String nama, String tujuan, String tanggal, int jumlah, int harga, int bayar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, nama);
        values.put(COL_TUJUAN, tujuan);
        values.put(COL_TANGGAL, tanggal);
        values.put(COL_JUMLAH, jumlah);
        values.put(COL_HARGA, harga);
        values.put(COL_BAYAR, bayar);
        db.insert(TABLE_TIKET, null, values);
        db.close();
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TIKET, null);
    }

    public void updateBooking(int id, String nama, String tujuan, String tanggal, int jumlah, int harga, int bayar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, nama);
        values.put(COL_TUJUAN, tujuan);
        values.put(COL_TANGGAL, tanggal);
        values.put(COL_JUMLAH, jumlah);
        values.put(COL_HARGA, harga);
        values.put(COL_BAYAR, bayar);
        db.update(TABLE_TIKET, values, COL_TIKET_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteBooking(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIKET, COL_TIKET_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
