package com.example.picstar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class picAdapter2 extends RecyclerView.Adapter<picAdapter2.ViewHolder> {

    Context context;
    private List<image_up> Upload_image;
    private FirebaseStorage storage;
    private DatabaseReference dataRef;


    public picAdapter2(Fragment fragment, List<image_up> list, FirebaseStorage storage, DatabaseReference ref)
    {
        this.context = fragment.getContext();
        this.Upload_image = list;
        this.storage = storage;
        this.dataRef = ref;
    }

    @NonNull
    @Override
    public picAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.profile_footage, parent, false);
        picAdapter2.ViewHolder viewHolder = new picAdapter2.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull picAdapter2.ViewHolder holder, int position) {
        image_up uploadCurrent = Upload_image.get(position);
        holder.photoTitle.setText(uploadCurrent.getName());
        holder.username.setText(uploadCurrent.getUser());
        holder.status.setText(uploadCurrent.getStatus());
        Picasso.get().load(uploadCurrent.getImageUrl()).fit().centerCrop().into(holder.imageView);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_up up = Upload_image.get(position);
                String key = up.getKey();

                StorageReference ref = storage.getReferenceFromUrl(up.getImageUrl());
                ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataRef.child(key).removeValue();
                        Toast.makeText(context, "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Picture.class);
                intent.putExtra("Title", uploadCurrent.getName());
                intent.putExtra("Username", uploadCurrent.getUser());
                intent.putExtra("URL", uploadCurrent.getImageUrl());
                intent.putExtra("Status", uploadCurrent.getStatus());
                intent.putExtra("file", uploadCurrent.getFile_extension());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Upload_image.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView photoTitle;
        TextView status;
        TextView username;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete_button);
            imageView =itemView.findViewById(R.id.photo);
            photoTitle = itemView.findViewById(R.id.photo_title);
            username = itemView.findViewById(R.id.username);
            status = itemView.findViewById(R.id.status_text);
        }
    }
}
