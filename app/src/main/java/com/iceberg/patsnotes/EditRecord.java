package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class EditRecord extends AppCompatActivity {

    String folder;
    EditText etDetails;
    Long id;
    NoteDatabase noteDatabase;
    Note note;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);
        Intent intent = getIntent();
        folder = getIntent().getStringExtra("folder");
        id = intent.getLongExtra("ID",0);
        noteDatabase = new NoteDatabase(this);
        note = noteDatabase.getNote(id);
        etDetails = findViewById(R.id.etDetails);
        etDetails.setText(note.getContent());
        etDetails.setMovementMethod(new ScrollingMovementMethod());
        Toast.makeText(this,""+folder,Toast.LENGTH_SHORT).show();
    }

    public void updateNote(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etDetails.getWindowToken(), 0);

        String content = etDetails.getText().toString();
        calendar = Calendar.getInstance();
        String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.YEAR) + "\n" + AddNote.pad(calendar.get(Calendar.HOUR)) + ":" +
                AddNote.pad(calendar.get(Calendar.MINUTE));
        note.setTitle(content.split("\n")[0]);
        note.setContent(content);
        note.setDate(date);
        int id = noteDatabase.UpdateRecord(note);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etDetails.getWindowToken(), 0);
        Toast.makeText(this,"id: "+id+"\n\nnote.getId(): "+note.getId(),Toast.LENGTH_LONG).show();
        finish();
    }
}