package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class EditFolderSelector extends AppCompatActivity {

    String folder, type;
    Calendar calendar;
    RecyclerView recyclerView;
    NoteDatabase noteDatabase;
    List<Note> notes;
    Adapter adapter;
    TextView tvTitle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_folder_selector);
        tvTitle = findViewById(R.id.tvTitle1);
        recyclerView = findViewById(R.id.rvNotesList1);
        Log.i("here","i am here");
        folder = getIntent().getStringExtra("folder");
        type = getIntent().getStringExtra("type");


        noteDatabase = new NoteDatabase(this);
        notes = noteDatabase.getNotes(new String[]{"folder", "null"});
        Log.i("here","Size: "+notes.size());
        String temp = tvTitle.getText().toString()+" ("+notes.size()+")";
        tvTitle.setText(temp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,notes, adapter.mActivity);
        recyclerView.setAdapter(adapter);
    }
}