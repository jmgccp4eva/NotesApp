package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {

    EditText editText;
    String folder;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        folder = getIntent().getStringExtra("folder");
        editText = findViewById(R.id.etNote);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void saveNote(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        Toast.makeText(this, "Save clicked", Toast.LENGTH_SHORT).show();
        String content = editText.getText().toString();
        String[] split = content.split("\n");
        String title = split[0];
        String type = "note";
        calendar = Calendar.getInstance();
        String date = (calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+
                calendar.get(Calendar.YEAR)+"\n"+pad(calendar.get(Calendar.HOUR))+":"+
                pad(calendar.get(Calendar.MINUTE));
        Note note = new Note(title,content,type,date,folder);
        NoteDatabase noteDatabase = new NoteDatabase(this);
        noteDatabase.addNote(note);
    }

    private String pad(int i) {
        if(i<10){
            return "0"+i;
        }
        return String.valueOf(i);
    }
}