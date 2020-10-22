package com.kosmos.brushbreakfast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kosmos.brushbreakfast.utils.UT;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Verify.db";

    public static final String VERIFY_RECORD_TABLE_NAME = "VerifyRecord";

    private static final String createDataBase = "create table " + VERIFY_RECORD_TABLE_NAME + "(" +
            "id integer primary key autoincrement, " +
            "verifyTime text, " +
            "roomCardNumber text, " +
            "verifyResult text, " +
            "verifyDate text," +
            "ext text)";

    public static int databaseVersion = 1;

    private Context mContext;

    //带全部参数的构造函数，此构造函数必不可少
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDataBase);
        UT.loge("数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
