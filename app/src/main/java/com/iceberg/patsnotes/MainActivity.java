package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    NoteDatabase noteDatabase;
    TextView pageTitle;
    String folder;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        folder = getIntent().getStringExtra("folder");
        int leng;
        try {
            leng = folder.length();
        } catch (Exception e){
            leng = 0;
        }
        if(leng==0){ folder = "null"; }
        recyclerView = findViewById(R.id.listOfNotes);
        noteDatabase = new NoteDatabase(this);
        notes = noteDatabase.getNotes(new String[]{"note", "null"});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,notes);
        recyclerView.setAdapter(adapter);
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
                i.putExtra("folder",folder);
                startActivity(i);
                return true;
            case R.id.addFolderBtn:
                i = new Intent(this,AddFolder.class);
                i.putExtra("folder",folder);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }

    public void deleteRecord(View view) {
    }
}