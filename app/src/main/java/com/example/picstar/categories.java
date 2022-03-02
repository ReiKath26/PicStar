package com.example.picstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picstar.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class categories extends AppCompatActivity {

    RecyclerView pic_recyclerView;
    TextView textView;
    private DatabaseReference databaseRef;
    private List<image_up> uploads;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        textView = findViewById(R.id.category_text);

        Intent intent = getIntent();
        category = intent.getStringExtra("Category");

        pic_recyclerView = findViewById(R.id.categories_rv);
        pic_recyclerView.setLayoutManager(new LinearLayoutManager(categories.this));

        textView.setText(category);

        uploads = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance(" https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("footages");
        Query query = databaseRef.orderByChild("category").equalTo(category);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    image_up up = postSnapshot.getValue(image_up.class);
                    uploads.add(up);
                }

                picategoryAdapter adapter = new picategoryAdapter(categories.this, uploads);
                pic_recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(categories.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}