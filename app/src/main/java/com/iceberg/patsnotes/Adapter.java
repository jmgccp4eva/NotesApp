package com.iceberg.patsnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Note> notes;
    List<Note> children;
    NoteDatabase noteDatabase;

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
                    String title = notes.get(getAdapterPosition()).getTitle();
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
                    View v = LayoutInflater.from(view.getContext()).inflate(
                            R.layout.layout_dialog,
                            view.findViewById(R.id.layoutDialogContainer)
                    );
                    builder.setView(v);
                    ((TextView)v.findViewById(R.id.textTitle)).setText(v.getResources().getString(R.string.warning));
                    ((TextView)v.findViewById(R.id.textMessage)).setText(temp);
                    ((androidx.appcompat.widget.AppCompatButton)v.findViewById(R.id.btnCancel)).setText(v.getResources().getString(R.string.cancel));
                    ((androidx.appcompat.widget.AppCompatButton)v.findViewById(R.id.btnDelete)).setText(v.getResources().getString(R.string.delete));
                    ((ImageView)v.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_baseline_warning_24);

                    final AlertDialog alertDialog = builder.create();

                    v.findViewById(R.id.btnDelete).setOnClickListener(view1 -> {
                        noteDatabase = new NoteDatabase(view.getContext());
                        String title1 = notes.get(getAdapterPosition()).getTitle();
                        long id = notes.get(getAdapterPosition()).getId();
                        String type1 = notes.get(getAdapterPosition()).getType();
                        String parent = notes.get(getAdapterPosition()).getParent();
                        if(type1.equals("folder")){
                            children = noteDatabase.getNotes(new String[]{"note",parentLookingFor});
                            for(int j=0;j<children.size();j++){
                                long myID = children.get(j).getId();
                                noteDatabase.deleteNote(myID);
                            }
                            noteDatabase.deleteNote(id);
                            // Refresh load of current page
                            Intent intent = new Intent(view.getContext(),AddFolder.class);
                            intent.putExtra("folder","null");
                            view.getContext().startActivity(intent);
                            ((Activity)(view.getContext())).finish();

                        }else if(type1.equals("note")){
                            // DELETE THIS NOTE
                            Intent intent;
                            if(parent.equals("null")){
                                intent = new Intent(view.getContext(),MainActivity.class);
                                intent.putExtra("folder","null");
                            }else{
                                intent = new Intent(view.getContext(),NotesInFolders.class);
                                intent.putExtra("folder",parent);
                            }
                            noteDatabase.deleteNote(id);

                            view.getContext().startActivity(intent);
                            ((Activity)(view.getContext())).finish();
                        }
                        alertDialog.dismiss();

                    });
                    v.findViewById(R.id.btnCancel).setOnClickListener(view12 -> alertDialog.dismiss());

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
                    view.getContext().startActivity(intent);
                }else if(type.equals("folder")){
                    Intent intent = new Intent(view.getContext(),NotesInFolders.class);
                    intent.putExtra("ID",notes.get(getAdapterPosition()).getId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
