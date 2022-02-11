package com.iceberg.patsnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFolder extends AppCompatActivity {

    TextView tvTitle;
    EditText etNewFolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_folder);
        Toast.makeText(this, "THIS IS THE EDIT FOLDER PAGE", Toast.LENGTH_SHORT).show();
        tvTitle = findViewById(R.id.tvTitle);
        etNewFolderName = findViewById(R.id.etNewFolderName);
        
    }
}