package com.example.picstar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.core.Tag;
import com.huawei.hms.image.vision.*;
import com.huawei.hms.image.vision.ImageVisionImpl;
import com.huawei.hms.image.vision.bean.ImageVisionResult;
import com.huawei.secure.android.common.util.LogsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class filter_beta extends AppCompatActivity {

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Button btn_choose, btn_apply;
    private static final int Pick_image_request = 1;
    ImageView imageView;
    EditText et_filter, et_intensity, et_compress;
    private int initCodeState = -2;
    private int stopCodeState = -2;
    private Bitmap bitmap;

    private Uri imageUri;

    private final int requestCode = 100;

    ImageVisionImpl imageVision;

    String string = "{\"projectId\":\"projectIdTest\",\"appId\":\"appIdTest\",\"authApiKey\":\"authApiKeyTest\",\"clientSecret\":\"clientSecretTest\",\"clientId\":\"clientIdTest\",\"token\":\"tokenTest\"}";

    private JSONObject authJson;

    {
        try {
            authJson = new JSONObject(string);
        } catch(JSONException e)
        {
            LogsUtil.e("Filter exp", e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_beta);

        imageView = findViewById(R.id.img_filter);
        btn_choose = findViewById(R.id.btn_choose);
        btn_apply = findViewById(R.id.apply_filter);
        et_filter = findViewById(R.id.filter_type);
        et_compress = findViewById(R.id.compressrate);
        et_intensity = findViewById(R.id.intensity);
        initFilter(filter_beta.this);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                String filterType = et_filter.getText().toString();
                String compress = et_compress.getText().toString();
                String intensity = et_intensity.getText().toString();
                if(initCodeState !=0 | stopCodeState == 0)
                {
                    Toast.makeText(filter_beta.this, "Service not initialized! Try again later!", Toast.LENGTH_LONG).show();
                    return;
                }

                startFilter(filterType, compress, intensity, authJson);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void startFilter(String filterType, String compress, String intensity, JSONObject authJson) {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                JSONObject jsonObject = new JSONObject();
                JSONObject taskJson = new JSONObject();
                try {

                    taskJson.put("intensity", intensity);
                    taskJson.put("filterType", filterType);
                    taskJson.put("compressRate", compress);
                    jsonObject.put("requestId", 1);
                    jsonObject.put("taskJson", taskJson);
                    jsonObject.put("authJson", authJson);

                    final ImageVisionResult visionResult = imageVision.getColorFilter(jsonObject, bitmap);

                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap image = visionResult.getImage();
                            imageView.setImageBitmap(image);
//                            Toast.makeText(filter_beta.this, visionResult.getResponse().toString() + "Result Code: " + visionResult.getResultCode(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch(JSONException e)
                {
                    Toast.makeText(filter_beta.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        };

        executorService.execute(runnable);
    }

    private void openFileChooser()
    {
        Intent intent = new  Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_image_request);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_image_request && resultCode == RESULT_OK
                && data != null && data.getData()!= null)
        {
            imageUri = data.getData();
            ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageUri);
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initFilter(final Context context) {
        imageVision = ImageVision.getInstance(this);
        imageVision.setVisionCallBack(new ImageVision.VisionCallBack() {
            @Override
            public void onSuccess(int i) {
                int initCode = imageVision.init(context, authJson);
                initCodeState = initCode;
                stopCodeState = -2;
                Toast.makeText(context, "Image Vision initialized! Init Code: " + initCode, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i) {
                Toast.makeText(context, "Image Vision initialize failed! Error Code" + i, Toast.LENGTH_LONG).show();
            }
        });
    }
}