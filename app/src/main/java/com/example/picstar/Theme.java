package com.example.picstar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.picstar.databinding.ActivityThemeBinding;

import java.util.ArrayList;

public class Theme extends AppCompatActivity {

    ListView listView;

    String themes[] = {
            "Nature", "Sports", "Foodies", "Kids", "Mugshot", "Others"
    };

    int img[] = {R.drawable.ic_baseline_emoji_nature_24,
                R.drawable.ic_baseline_sports_basketball_24,
                R.drawable.ic_baseline_fastfood_24,
                R.drawable.ic_baseline_extension_24,
                R.drawable.ic_baseline_person_24,
                R.drawable.ic_baseline_filter_vintage_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        ListAdapter adapter = new ListAdapter(this, themes, img);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Theme.this, Picture.class);
                startActivity(intent);
            }
        });

    }

    class ListAdapter extends ArrayAdapter<String>
    {
        Context context;
        String rTheme[];
        int img[];

        ListAdapter (Context c, String rTheme[], int img[])
        {
            super(c, R.layout.theme_item, R.id.theme_name, rTheme);
            this.context = c;
            this.rTheme = rTheme;
            this.img = img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(R.layout.theme_item, parent, false);
            ImageView image = v.findViewById(R.id.icon_theme);
            TextView text = v.findViewById(R.id.theme_name);

            image.setImageResource(img[position]);
            text.setText(rTheme[position]);

            return v;
        }
    }

}