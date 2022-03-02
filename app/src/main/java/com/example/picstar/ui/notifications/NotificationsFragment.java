package com.example.picstar.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picstar.Login;
import com.example.picstar.MainActivity;
import com.example.picstar.R;
import com.example.picstar.User;
import com.example.picstar.image_up;
import com.example.picstar.picAdapter;
import com.example.picstar.picAdapter2;
import com.example.picstar.ui.home.HomeFragment;
import com.example.picstar.upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    Button btn_logout, btn_upload;

    RecyclerView pic_recyclerView;
    private DatabaseReference databaseRef;
    private List<image_up> uploads;

    private FirebaseUser user;
    private ValueEventListener listener;
    private FirebaseStorage storageRef;
    private DatabaseReference reference;
    private String userID, username;
    private TextView textView;
    private String flag;

    private NotificationsViewModel notificationsViewModel;
    private HuaweiIdAuthService service;
    private HuaweiIdAuthParams authParams;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_logout = view.findViewById(R.id.log_out_btn);
        btn_upload = view.findViewById(R.id.upload_btn);
        textView = view.findViewById(R.id.username_profile);
        Intent intent = getActivity().getIntent();
        flag = intent.getStringExtra("Flag");

        pic_recyclerView = view.findViewById(R.id.footages_rv);
        pic_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        uploads = new ArrayList<>();
        storageRef = FirebaseStorage.getInstance();
        databaseRef = FirebaseDatabase.getInstance(" https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("footages");

        if(flag.contentEquals("Email"))
        {
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance("https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
            userID = user.getUid();

           reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    username = user.name;
                    textView.setText(user.name);

                    Query query = databaseRef.orderByChild("user").equalTo(user.name);

                    listener = query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            uploads.clear();
                            for(DataSnapshot postSnapshot : snapshot.getChildren())
                            {
                                image_up up = postSnapshot.getValue(image_up.class);
                                up.setKey(postSnapshot.getKey());
                                uploads.add(up);
                            }

                            picAdapter2 adapter = new picAdapter2(NotificationsFragment.this, uploads, storageRef, databaseRef);
                            pic_recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getContext(), "Log Out Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            });
        }



        else

        {
            textView.setText(intent.getStringExtra("Username"));
            username = intent.getStringExtra("Username");
            authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams();
            service = HuaweiIdAuthManager.getService(getContext(), authParams);

            Query query = databaseRef.orderByChild("user").equalTo(username);

            listener = query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    uploads.clear();
                    for(DataSnapshot postSnapshot : snapshot.getChildren())
                    {
                        image_up up = postSnapshot.getValue(image_up.class);
                        up.setKey(postSnapshot.getKey());
                        uploads.add(up);
                    }

                    picAdapter2 adapter = new picAdapter2(NotificationsFragment.this, uploads, storageRef, databaseRef);
                    pic_recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task<Void> signOutTask = service.signOut();
                    signOutTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Toast.makeText(getContext(), "Log Out Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), upload.class);
                intent.putExtra("Flag", flag);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

    }

}