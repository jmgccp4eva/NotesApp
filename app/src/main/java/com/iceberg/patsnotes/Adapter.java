package com.iceberg.patsnotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<Note> notes;

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String type = notes.get(getAdapterPosition()).getType();
                    if(type.equals("note")){
                        Intent intent = new Intent(view.getContext(),EditRecord.class);
                        intent.putExtra("ID",notes.get(getAdapterPosition()).getId());
                        view.getContext().startActivity(intent);
                    }else if(type.equals("folder")){
                        Intent intent = new Intent(view.getContext(),NotesInFolders.class);
                        intent.putExtra("ID",notes.get(getAdapterPosition()).getId());
                        view.getContext().startActivity(intent);
                    }
                    /*if(notes.get(getAdapterPosition()).getType()=="note"){

                    }else if(notes.get(getAdapterPosition()).getType()=="folder"){
                        Toast.makeText(view.getContext(),"Folder ID: "+notes.get(getAdapterPosition()).getId()+" was clicked",Toast.LENGTH_SHORT).show();
                    }*/

                }
            });
        }
    }
}
