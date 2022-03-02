package com.example.picstar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.picstar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class upload extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int Pick_image_request = 1;

    private Button btn_choose_image, btn_upload;
    private EditText et_file_name;
    private ProgressBar progressBar;
    private ImageView image_view;
    private String category, url;
    private String status;
    private Spinner spinner;
    private CheckBox checkBox;

    private Uri imageUri;

    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private String username;

    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        btn_choose_image = findViewById(R.id.choose_file);
        btn_upload = findViewById(R.id.image_up_button);
        et_file_name = findViewById(R.id.editTextTitle);
        progressBar = findViewById(R.id.up_progress_bar);
        image_view = findViewById(R.id.to_up_image);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        spinner = findViewById(R.id.category_spinner);
        checkBox = findViewById(R.id.checkbox);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        if(checkBox.isChecked())
        {
            status = "Premium";
        }

        else
        {
            status = "Free";
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        storageRef = FirebaseStorage.getInstance().getReference("footages");
        databaseRef = FirebaseDatabase.getInstance("https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("footages");

        btn_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uploadTask!= null && uploadTask.isInProgress())
                {
                    Toast.makeText(upload.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadimage();
                }
            }
        });
    }

    private void openFileChooser()
    {
        Intent intent = new  Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_image_request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_image_request && resultCode == RESULT_OK
            && data != null && data.getData()!= null)
        {
            imageUri = data.getData();
            image_view.setImageURI(imageUri);
        }

    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadimage()
    {
        if(imageUri!=null)
        {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 5000);

                    Toast.makeText(upload.this, "Upload Successful", Toast.LENGTH_LONG).show();

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image_up upload = new image_up(et_file_name.getText().toString().trim(), status, category, username, uri.toString(), "." + getFileExtension(imageUri));
                            String uploadId = databaseRef.push().getKey();
                            databaseRef.child(uploadId).setValue(upload);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                }
            });
        }

        else
        {
            Toast.makeText(upload.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Select Category", Toast.LENGTH_LONG).show();
    }
}