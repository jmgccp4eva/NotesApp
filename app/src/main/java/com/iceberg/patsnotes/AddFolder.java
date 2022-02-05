package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AddFolder extends AppCompatActivity {

    EditText editText;
    String folder;
    Calendar calendar;
    RecyclerView recyclerView;
    NoteDatabase noteDatabase;
    List<Note> notes;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);
        editText = findViewById(R.id.etNewFolderName);
        folder = getIntent().getStringExtra("folder");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        recyclerView = findViewById(R.id.rvNotesList);
        noteDatabase = new NoteDatabase(this);
        notes = noteDatabase.getNotes(new String[]{"folder", "null"});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,notes);
        recyclerView.setAdapter(adapter);
    }

    public void saveFolder(View view) {
        String folderName = editText.getText().toString();
        if (folderName.length() > 0) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            String title = editText.getText().toString();
            String content = "";
            String type = "folder";
            calendar = Calendar.getInstance();
            String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                    calendar.get(Calendar.YEAR) + "\n" + AddNote.pad(calendar.get(Calendar.HOUR)) + ":" +
                    AddNote.pad(calendar.get(Calendar.MINUTE));
            Note note = new Note(title, content, type, date, "null");
            NoteDatabase noteDatabase = new NoteDatabase(this);
            noteDatabase.addNote(note);

            editText.setText("");
            editText.clearFocus();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        } else {
            Toast toast = Toast.makeText(this, "Please enter a folder name to save", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void returnHome(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("folder","null");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void deleteRecord(View view) {
    }
}