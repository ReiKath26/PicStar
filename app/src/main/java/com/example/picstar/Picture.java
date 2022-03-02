package com.example.picstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picstar.databinding.ActivityPictureBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Picture extends AppCompatActivity {

    TextView title, username;
    ImageView imageView;
    Button download, premium;

    private String name, file_extension, url, status, user_status;
    private StorageReference storageReference;
    private DatabaseReference reference;

    FirebaseUser user;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        userID = user.getUid();

        title = findViewById(R.id.photo_title_detail);
        username = findViewById(R.id.username_detail);
        imageView = findViewById(R.id.photo_detail);
        download = findViewById(R.id.button_download);
        premium = findViewById(R.id.premium_download);
        setData();

        storageReference = FirebaseStorage.getInstance().getReference("footages");
    }

    private void setData()
    {
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("URL")).fit().centerCrop().into(imageView);
        url = intent.getStringExtra("URL");
        title.setText(intent.getStringExtra("Title"));
        name = intent.getStringExtra("Title");
        file_extension = intent.getStringExtra("file");
        username.setText(intent.getStringExtra("Username"));
        status = intent.getStringExtra("Status");

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User users = snapshot.getValue(User.class);

                if(status.contentEquals("Premium"))
                {
                    if(users.status.contentEquals("Premium"))
                    {
                        premium.setVisibility(View.GONE);

                    }

                    else
                    {
                        download.setVisibility(View.GONE);
                    }
                }

                else
                {
                    premium.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Picture.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Picture.this, "Downloading image....", Toast.LENGTH_LONG).show();
                Download(Picture.this, name, file_extension, DIRECTORY_DOWNLOADS, url);
            }
        });

        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Picture.this, promo_premium.class);
                startActivity(intent);
            }
        });

    }

    private void Download(Context context, String fileName, String fileExtension, String destinationDirectory, String URL)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        context.getExternalFilesDir(DIRECTORY_DOWNLOADS).mkdirs();
        Uri uri = Uri.parse(URL);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }
}