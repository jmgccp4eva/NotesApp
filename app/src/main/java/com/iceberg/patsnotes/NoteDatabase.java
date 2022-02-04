package com.iceberg.patsnotes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patsNotesDB";
    private static final String DATABASE_TABLE = "patsNotesDBTable";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATE = "date";
    private static final String KEY_PARENT = "parent";

    private static final String CREATE_TABLE = "create table "+DATABASE_TABLE+" ("+
            KEY_ID+" integer primary key autoincrement, "+
            KEY_TITLE+" text, "+
            KEY_CONTENT+" text, "+
            KEY_TYPE+" text, "+
            KEY_DATE + " text, "+
            KEY_PARENT+ " text)";
    private static final String DROP_TABLE = "drop table if exists "+DATABASE_TABLE;
    Context context;

    public NoteDatabase(@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
        Toast.makeText(context, "Constructor called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate Called", Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(context, "ERROR: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            Toast.makeText(context, "ONUPGRADE CALLED", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }catch (SQLException e){
            Toast.makeText(context, "ERROR: "+e, Toast.LENGTH_SHORT).show();
        }
    }
}
