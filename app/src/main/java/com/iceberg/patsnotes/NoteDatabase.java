package com.iceberg.patsnotes;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patsNotesDB.db";
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

    public long addNote(Note note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,note.getTitle());
        contentValues.put(KEY_CONTENT,note.getContent());
        contentValues.put(KEY_TYPE,note.getType());
        contentValues.put(KEY_DATE,note.getDate());
        contentValues.put(KEY_PARENT,note.getParent());
        long ID = sqLiteDatabase.insert(DATABASE_TABLE,null,contentValues);
        Toast.makeText(context, "ID: "+ID, Toast.LENGTH_SHORT).show();
        return ID;
    }

    public Note getNote(long id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_TYPE,KEY_DATE,KEY_PARENT},
                KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,KEY_TITLE);
        if(cursor!=null)
            cursor.moveToNext();
        Note note = new Note(cursor.getLong(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5));
        cursor.close();
        return note;
    }

    public int UpdateRecord(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,note.getTitle());
        contentValues.put(KEY_CONTENT,note.getContent());
        contentValues.put(KEY_DATE,note.getDate());
        return db.update(DATABASE_TABLE,contentValues,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
    }

    public List<Note> getNotes(String[] values){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Note> notes = new ArrayList<>();
        String[] keys = {KEY_TYPE,KEY_PARENT};
        String query = buildSelectionQuery(keys,values);
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_TYPE,KEY_DATE,KEY_PARENT},
                KEY_TYPE+"=? AND "+KEY_PARENT+"=?",
                values,null,null,KEY_TITLE);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setType(cursor.getString(3));
                note.setDate(cursor.getString(4));
                note.setParent(cursor.getString(5));
                notes.add(note);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    private String buildSelectionQuery(String[] whereKey,String[] wheres){
        String q = "SELECT * FROM "+DATABASE_TABLE + " WHERE ";
        if(whereKey.length==1){
            q=q+" WHERE "+ whereKey[0] + "=" + wheres[0] + " ORDER BY "+ KEY_TITLE;
        }else if(whereKey.length>1){
            for(int i=0;i<whereKey.length;i++){
                if(i>0)
                    q += " AND ";
                q += whereKey[i] + "=" + wheres[i];
            }
            q = q + " ORDER BY " + KEY_TITLE;
        }
        return q;
    }
}
