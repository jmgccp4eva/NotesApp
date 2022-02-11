package com.iceberg.patsnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Note> notes;
    List<Note> children;
    NoteDatabase noteDatabase;
    String message = "Would you like to edit this note's name or delete this note?";
    String mTitle, title;
    TextView tvetNewName;

    Adapter(Context context, List<Note> notes){
        this.layoutInflater = LayoutInflater.from(context);
        this.notes = notes;
    }



    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_list_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String title = notes.get(i).getTitle();
        holder.myTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView myTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTitle = itemView.findViewById(R.id.tvCustomTitle);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    title = notes.get(getAdapterPosition()).getTitle();
                    String type = notes.get(getAdapterPosition()).getType();
                    String parentLookingFor = String.valueOf(notes.get(getAdapterPosition()).getId());
                    String temp="Are you sure you want to delete folder "+title+"?\nIt contains ";
                        // get notes in folder and change
                    if(type.equals("folder")){
                        NoteDatabase noteDatabase = new NoteDatabase(view.getContext());
                        children = noteDatabase.getNotes(new String[]{"note",parentLookingFor});
                        temp+=children.size()+" notes that will also be deleted.  These notes are:\n";
                        for(int i=0;i<children.size();i++){
                            if(i==(children.size()-1)){
                                temp=temp+" & ";
                            }else if(i>0){
                                temp=temp+", ";
                            }
                            temp=temp+children.get(i).getTitle();
                        }
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.AlertDialogTheme);
                    View v = null;
                    if(type.equals("note")){
                        v = LayoutInflater.from(view.getContext()).inflate(
                                R.layout.layout_dialog2,
                                view.findViewById(R.id.layoutDialogContainer)
                        );
                    }else{
                        v = LayoutInflater.from(view.getContext()).inflate(
                                R.layout.layout_dialog,
                                view.findViewById(R.id.layoutDialogContainer)
                        );
                    }

                    builder.setView(v);
                    mTitle = notes.get(getAdapterPosition()).getTitle();
                    ((TextView)v.findViewById(R.id.textTitle)).setText(mTitle);
                    if(type.equals("folder")){
                        tvetNewName = v.findViewById(R.id.etNewName);
                    }
                    TextView tvMessage = v.findViewById(R.id.tvMessage);

                    if(type.equals("note")){
                        String tString = tvMessage.getText().toString();
                        tString = tString + "Note: " + notes.get(getAdapterPosition()).getTitle()+"?";
                        tvMessage.setText(tString);
                    }else{
                        String tString = "Either Enter a New Folder Name and Click Save OR Delete Folder: " + notes.get(getAdapterPosition()).getTitle()+"?";
                        tvMessage.setText(tString);
                    }

                    AppCompatButton btnDelete = ((androidx.appcompat.widget.AppCompatButton)v.findViewById(R.id.btnDel));

                    final AlertDialog alertDialog = builder.create();

                    if(type.equals("folder")){
                        tvetNewName.setText(title);
                    }

                    if(type.equals("folder")){
                        AppCompatButton btnEdt = ((androidx.appcompat.widget.AppCompatButton)v.findViewById(R.id.btnEdt));
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(view.getContext(), "FOLDER",Toast.LENGTH_SHORT).show();
                            }
                        });
                        btnEdt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String newTitle = tvetNewName.getText().toString();
                                if(!notes.get(getAdapterPosition()).getTitle().equals(newTitle)){
                                    Note note = notes.get(getAdapterPosition());


                                    Calendar calendar = Calendar.getInstance();
                                    String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                                            calendar.get(Calendar.YEAR) + "\n" + AddNote.pad(calendar.get(Calendar.HOUR)) + ":" +
                                            AddNote.pad(calendar.get(Calendar.MINUTE));
                                    note.setTitle(newTitle);
                                    note.setDate(date);
                                    NoteDatabase nb = new NoteDatabase(alertDialog.getContext());
                                    nb.UpdateRecord(note);
                                    Intent intent = new Intent(view.getContext(),AddFolder.class);
                                    intent.putExtra("folder","folder");
                                    view.getContext().startActivity(intent);
                                    alertDialog.dismiss();
                                    ((Activity)view.getContext()).finish();
                                }
                            }
                        });

                    }else{
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String parent = notes.get(getAdapterPosition()).getParent();
                                if(parent.equals("null")){
                                    // NOTE NOT IN FOLDER
                                    Toast.makeText(view.getContext(), "Not in folder NOTE",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(view.getContext(), "IN folder NOTE",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    alertDialog.show();

                    return true;
                }
            });

            itemView.setOnClickListener(view -> {
                String type = notes.get(getAdapterPosition()).getType();
                long id = notes.get(getAdapterPosition()).getId();
                if(type.equals("note")){
                    Intent intent = new Intent(view.getContext(),EditRecord.class);
                    intent.putExtra("ID",notes.get(getAdapterPosition()).getId());
                    intent.putExtra("type",notes.get(getAdapterPosition()).getType());
                    intent.putExtra("parent",notes.get(getAdapterPosition()).getParent());
                    view.getContext().startActivity(intent);
                }else if(type.equals("folder")){
                    Intent intent = new Intent(view.getContext(),NotesInFolders.class);
                    intent.putExtra("ID",notes.get(getAdapterPosition()).getId());
                    intent.putExtra("folder","folder");
                    intent.putExtra("parent","null");
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
