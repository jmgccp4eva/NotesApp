package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
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
    String folder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        folder = getIntent().getStringExtra("folder");
        folder1 = folder;
        Toast.makeText(this, "My folder: "+folder+"\n\n"+folder1, Toast.LENGTH_SHORT).show();
        editText = findViewById(R.id.etFolder);
        editText.requestFocus();
    }

    public void saveNote(View view) {
        String content = editText.getText().toString();
        if(content.length()>0) {
            // HIDES KEYBOARD
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            // GET THE ACTUAL DATA NEEDED
            String title = content.split("\n")[0];
            String type = "note";
            calendar = Calendar.getInstance();
            String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                    calendar.get(Calendar.YEAR) + "\n" + pad(calendar.get(Calendar.HOUR)) + ":" +
                    pad(calendar.get(Calendar.MINUTE));
            //folder1 = folder;
            //Toast.makeText(this, "Right after folder1 supplied\n" + folder1, Toast.LENGTH_LONG).show();

            // HERE IS WHERE WE NEED TO PUT THE DATA INTO THE DATABASE
            Note note = new Note(title, content, type, date, folder);
            NoteDatabase noteDatabase = new NoteDatabase(this);
            long x = noteDatabase.addNote(note);

            // Makes a new Intent to return results back to onActivityResult
            Intent intent = new Intent();
            intent.putExtra("result", String.valueOf(x));
            intent.putExtra("folder", folder1);
            setResult(1203, intent);
            super.onBackPressed();
        }else{
            // SHOW ALERT DIALOG BOX THAT THERE MUST BE SOMETHING IN THE
            // CONTENT
            Toast.makeText(this,"You must enter details for the note",Toast.LENGTH_SHORT).show();
        }
    }

    public static String pad(int i) {
        if(i<10){
            return "0"+i;
        }
        return String.valueOf(i);
    }
}