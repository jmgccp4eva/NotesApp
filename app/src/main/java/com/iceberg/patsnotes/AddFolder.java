package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddFolder extends AppCompatActivity {

    EditText editText;
    String folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);
        editText = findViewById(R.id.etNewFolderName);
        folder = getIntent().getStringExtra("folder");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void saveFolder(View view) {
        String folderName = editText.getText().toString();
        if(folderName.length()>0){

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            // add folder to database
            // and re-open this page while finishing this one

            editText.setText("");
            editText.clearFocus();
        }else{
            Toast toast = Toast.makeText(this, "Please enter a folder name to save", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
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
}