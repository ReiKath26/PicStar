package com.example.picstar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picstar.ui.home.HomeFragment;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {

    String theme[];
    int img[];
    Context context;

    public categoryAdapter(HomeFragment fragment, String theme[], int img[])
    {
        this.theme = theme;
        this.img = img;
        this.context = fragment.getContext();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.theme_item, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category_icon.setImageResource(img[position]);
        holder.category_text.setText(theme[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, categories.class);
                intent.putExtra("Category", theme[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView category_text;
        ImageView category_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_icon = itemView.findViewById(R.id.icon_theme);
            category_text = itemView.findViewById(R.id.theme_name);
        }
    }
}
