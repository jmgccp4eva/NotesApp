package com.iceberg.patsnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class NotesInFolders extends AppCompatActivity {

    NoteDatabase noteDatabase;
    List<Note> notes;
    RecyclerView recyclerView;
    TextView tvTitle;
    long folder_id;
    Adapter adapter;
    ConstraintLayout constraintLayout;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==1203){
                        Intent intent = result.getData();
                        if(intent!=null){
                            Intent i = new Intent(NotesInFolders.this, NotesInFolders.class);
                            String folder1 = intent.getStringExtra("folder");
                            i.putExtra("folder",folder1);
                            Toast.makeText(NotesInFolders.this,"FOLDER\n\n\n"+folder1,Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_in_folders);
        noteDatabase = new NoteDatabase(this);
        recyclerView = findViewById(R.id.rvNotesList);
        folder_id = getIntent().getLongExtra("ID",0);
        Log.i("thisData","Folder_id: "+folder_id);
        if(folder_id==0){
            folder_id = Long.parseLong(getIntent().getStringExtra("folder"));
        }
        constraintLayout = findViewById(R.id.outerConstraintForCustom);

        Toast.makeText(this,"XYZ\n"+folder_id,Toast.LENGTH_SHORT).show();
        notes = noteDatabase.getNotes(new String[]{"note", String.valueOf(folder_id)});

        tvTitle = findViewById(R.id.tvTitle);
        String titleBarTitle = tvTitle.getText().toString();
        titleBarTitle+="("+notes.size()+")";
        tvTitle.setText(titleBarTitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,notes,NotesInFolders.this);
        recyclerView.setAdapter(adapter);
    }

    public void addNote(View view) {
        Intent intent = new Intent(this,AddNote.class);
        intent.putExtra("folder",String.valueOf(folder_id));
        activityResultLauncher.launch(intent);
    }
}