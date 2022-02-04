package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    NoteDatabase noteDatabase;
    TextView pageTitle;
    String folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteDatabase = new NoteDatabase(this);
        SQLiteDatabase sqLiteDatabase = noteDatabase.getWritableDatabase();
        folder = getIntent().getStringExtra("folder");
        int leng;
        try {
            leng = folder.length();
        } catch (Exception e){
            leng = 0;
        }
        if(leng==0){ folder = "null"; }
        Toast.makeText(this, ""+folder, Toast.LENGTH_SHORT).show();
    }

    public void showPopUp(View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.note_or_folder);
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()){
            case R.id.addNoteBtn:
                i = new Intent(this,AddNote.class);
                startActivity(i);
                return true;
            case R.id.addFolderBtn:
                i = new Intent(this,AddFolder.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }
}