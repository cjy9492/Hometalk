package com.cjy.hometalk.db;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class BirthdayDbAdapter {
	

	    public static final String KEY_TITLE = "title";
	    public static final String KEY_BODY = "body";
	    public static final String KEY_ROWID = "_id";
	    public static final String KEY_CREATED = "created";

	    private static final String TAG = "DbAdapter";
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;

	    private static final String DATABASE_CREATE = "create table birthday (_id integer primary key autoincrement, "
	            + "title text not null, body text not null, created text not null);";

	    private static final String NAME = "databasebirthday";
	    private static final String TABLE = "birthday";
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
	            db.execSQL("DROP TABLE IF EXISTS birthday");
	            onCreate(db);
	        }
	    }

	    public BirthdayDbAdapter(Context ctx)
	    {
	        this.mCtx = ctx;
	    }

	    public BirthdayDbAdapter open() throws SQLException
	    {
	        mDbHelper = new DatabaseHelper(mCtx);
	        mDb = mDbHelper.getWritableDatabase();
	        return this;
	    }

	    public void closeclose()
	    {
	        mDbHelper.close();
	    }

	    public long createDiary(String title, String body,String date)
	    {
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_TITLE, title);
	        initialValues.put(KEY_BODY, body);
	        initialValues.put(KEY_CREATED, date);
	        
	        
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

	    public boolean updateDiary(long rowId, String title, String body,String date)
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_TITLE, title);
	        args.put(KEY_BODY, body);
	        args.put(KEY_CREATED, date);

	        return mDb.update(TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	    }
	}
