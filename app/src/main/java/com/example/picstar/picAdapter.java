package com.example.picstar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class picAdapter extends RecyclerView.Adapter<picAdapter.ViewHolder> {

    Context context;
    private List<image_up> Upload_image;


    public picAdapter(Fragment fragment, List<image_up> list)
    {
        this.context = fragment.getContext();
        this.Upload_image = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pic_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull picAdapter.ViewHolder holder, int position) {
        image_up uploadCurrent = Upload_image.get(position);
        holder.photoTitle.setText(uploadCurrent.getName());
        holder.username.setText(uploadCurrent.getUser());
        holder.status.setText(uploadCurrent.getStatus());
        Picasso.get().load(uploadCurrent.getImageUrl()).fit().centerCrop().into(holder.imageView);


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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =itemView.findViewById(R.id.photo);
            photoTitle = itemView.findViewById(R.id.photo_title);
            username = itemView.findViewById(R.id.username);
            status = itemView.findViewById(R.id.status_text);
        }
    }
}
