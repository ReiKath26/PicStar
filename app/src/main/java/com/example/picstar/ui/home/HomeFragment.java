package com.example.picstar.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picstar.R;
import com.example.picstar.categoryAdapter;
import com.example.picstar.image_up;
import com.example.picstar.picAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.capture.TimeCategoriesResponse;
import com.huawei.hms.kit.awareness.status.TimeCategories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    TextView textView;
    private Map<Integer, String>  mTimeinfoMap = new HashMap<>();

    RecyclerView pic_recyclerView;
    private DatabaseReference databaseRef;
    private List<image_up> uploads;

    String theme[] = {"Foodie", "Landscape", "Sports", "Snapshots", "Animals", "Others"};

    int img[] = {R.drawable.ic_baseline_fastfood_24, R.drawable.ic_baseline_local_florist_24, R.drawable.ic_baseline_sports_basketball_24,
                R.drawable.ic_baseline_camera_alt_24,R.drawable.ic_baseline_emoji_nature_24 , R.drawable.ic_baseline_extension_24};

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        
        getTimeCategories();
        recyclerView = view.findViewById(R.id.recycler_view);
        textView = view.findViewById(R.id.timeanddate);

        pic_recyclerView = view.findViewById(R.id.item_picture_rv);
        pic_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        uploads = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance(" https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("footages");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    image_up up = postSnapshot.getValue(image_up.class);
                    uploads.add(up);
                }

                picAdapter adapter = new picAdapter(HomeFragment.this, uploads);
                pic_recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        categoryAdapter categoryAdapter = new categoryAdapter(HomeFragment.this, theme, img);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));

    }

    private void getTimeCategories()
    {

        @SuppressLint("MissingPermission") Task<TimeCategoriesResponse> task = Awareness.getCaptureClient(getActivity()).getTimeCategories();
        task.addOnSuccessListener(new OnSuccessListener<TimeCategoriesResponse>() {
            @Override
            public void onSuccess(TimeCategoriesResponse timeCategoriesResponse) {
                TimeCategories timeCategories = timeCategoriesResponse.getTimeCategories();
                StringBuilder stringBuilder = new StringBuilder();
                for(int timeCategoriesCode : mTimeinfoMap.keySet())
                {
                    if(timeCategories.isTimeCategory(timeCategoriesCode))
                    {
                        stringBuilder.append(mTimeinfoMap.get(timeCategoriesCode));
                    }
                }
                textView.setText(stringBuilder.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MM yyyy 'at' HH:mm:ss z");
                    String currdate = sdf.format(new Date());
                    textView.setText(currdate);
            }
        });
    }
}