package com.cjy.hometalk.discovery;

import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiaryDbAdapter
{

    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CREATED = "created";

    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE = "create table beiwang (_id integer primary key autoincrement, "
            + "title text not null, body text not null, created text not null);";

    private static final String NAME = "database";
    private static final String TABLE = "beiwang";
    private static final int VERSION = 1;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        DatabaseHelper(Context context)
        {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS beiwang");
            onCreate(db);
        }
    }

    public DiaryDbAdapter(Context ctx)
    {
        this.mCtx = ctx;
    }

    public DiaryDbAdapter open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void closeclose()
    {
        mDbHelper.close();
    }

    public long createDiary(String title, String body)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        Calendar calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        if(calendar.get(Calendar.HOUR_OF_DAY)<10){
            
            if(calendar.get(Calendar.MINUTE)<10){
            String created = calendar.get(Calendar.YEAR) + "."
                    + month + "."
                    + calendar.get(Calendar.DAY_OF_MONTH) + " "
                    +0+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    +0+ calendar.get(Calendar.MINUTE);
            		initialValues.put(KEY_CREATED, created);
            }
            else{
            	String created = calendar.get(Calendar.YEAR) + "."
                        + month + "."
                        + calendar.get(Calendar.DAY_OF_MONTH) + " "
                        +0+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
                        + calendar.get(Calendar.MINUTE);
            			initialValues.put(KEY_CREATED, created);
            }
            }
            else{
                if(calendar.get(Calendar.MINUTE)<10){
                    String created = calendar.get(Calendar.YEAR) + "."
                            + month + "."
                            + calendar.get(Calendar.DAY_OF_MONTH) + " "
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                            +0+ calendar.get(Calendar.MINUTE);
                    		initialValues.put(KEY_CREATED, created);
                    }
                    else{
                    	String created = calendar.get(Calendar.YEAR) + "."
                                + month + "."
                                + calendar.get(Calendar.DAY_OF_MONTH) + " "
                                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                                + calendar.get(Calendar.MINUTE);
                    			initialValues.put(KEY_CREATED, created);
                    }
            }
        
        return mDb.insert(TABLE, null, initialValues);
    }

    public boolean deleteDiary(int rowId)
    {

        return mDb.delete(TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllNotes()
    {    Cursor cursor=mDb.query(TABLE, new String[]
            {KEY_ROWID,KEY_TITLE, KEY_BODY, KEY_CREATED }, null, null, null,
            null, null);
    System.out.println(cursor.getCount());
        return cursor;
    }

    public Cursor getDiary(long rowId) throws SQLException
    {
        Cursor mCursor = mDb.query(true, TABLE, new String[]
        { KEY_ROWID, KEY_TITLE, KEY_BODY, KEY_CREATED }, KEY_ROWID + "="
                + rowId, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateDiary(long rowId, String title, String body)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_BODY, body);
        Calendar calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        if(calendar.get(Calendar.HOUR_OF_DAY)<10){
            
            if(calendar.get(Calendar.MINUTE)<10){
            String created = calendar.get(Calendar.YEAR) + "."
                    + month + "."
                    + calendar.get(Calendar.DAY_OF_MONTH) + " "
                    +0+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    +0+ calendar.get(Calendar.MINUTE);
            		args.put(KEY_CREATED, created);
            }
            else{
            	String created = calendar.get(Calendar.YEAR) + "."
                        + month + "."
                        + calendar.get(Calendar.DAY_OF_MONTH) + " "
                        +0+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
                        + calendar.get(Calendar.MINUTE);
            			args.put(KEY_CREATED, created);
            }
            }
            else{
                if(calendar.get(Calendar.MINUTE)<10){
                    String created = calendar.get(Calendar.YEAR) + "."
                            + month + "."
                            + calendar.get(Calendar.DAY_OF_MONTH) + " "
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                            +0+ calendar.get(Calendar.MINUTE);
                    		args.put(KEY_CREATED, created);
                    }
                    else{
                    	String created = calendar.get(Calendar.YEAR) + "."
                                + month + "."
                                + calendar.get(Calendar.DAY_OF_MONTH) + " "
                                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                                + calendar.get(Calendar.MINUTE);
                    			args.put(KEY_CREATED, created);
                    }
            }
        

        return mDb.update(TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
