package com.rohksin.grocery2home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by Illuminati on 10/25/2017.
 */

public class UploadActivity extends AppCompatActivity {

    private ImageView uplaodProductImage;
    private EditText productName;

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        uplaodProductImage = (ImageView)findViewById(R.id.uploadProductImage);
        productName = (EditText) findViewById(R.id.uploadProductName);

        uplaodProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

    }


    public byte[] getImageBytes()
    {
        uplaodProductImage.setDrawingCacheEnabled(true);
        uplaodProductImage.buildDrawingCache();

        Bitmap bitmap = uplaodProductImage.getDrawingCache();
        ByteArrayOutputStream boas = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100, boas);

        uplaodProductImage.setDrawingCacheEnabled(false);

        byte[] data = boas.toByteArray();

        return data;
    }

    public void upload()
    {
        byte[] data = getImageBytes();

        String imageName = productName.getText().toString();
        String path = "groceryImage/"+ imageName+"_"+UUID.randomUUID()+".png";

        StorageReference groceryImageDir = firebaseStorage.getReference(path);



        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("Caption","This is sample metadata")
                .build();

        UploadTask uploadTask = groceryImageDir.putBytes(data,metadata);

        uploadTask.addOnSuccessListener(UploadActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                productName.setText("Done");



            }
        });



    }
}
