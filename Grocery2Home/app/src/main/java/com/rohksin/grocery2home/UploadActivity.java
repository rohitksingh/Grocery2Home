package com.rohksin.grocery2home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Illuminati on 10/25/2017.
 */

public class UploadActivity extends AppCompatActivity {

    private ImageView uplaodProductImage;
    private EditText productName;
    private EditText originalPrice;
    private EditText currentPrice;
    private Button openGalleryButton;

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference Groceriesreference = reference.child("Groceries");


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
        originalPrice = (EditText)findViewById(R.id.originalPrice);
        currentPrice = (EditText)findViewById(R.id.currentPrice);
        openGalleryButton = (Button)findViewById(R.id.openGallery);

        openGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

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

         final String imageName = productName.getText().toString();
        String path = "groceryImage/"+ imageName+"_"+UUID.randomUUID()+".png";

        StorageReference groceryImageDir = firebaseStorage.getReference(path);





        final StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("Caption","This is sample metadata")
                .build();

        //String downloadUrl = metadata.getDownloadUrl().toString();



        UploadTask uploadTask = groceryImageDir.putBytes(data,metadata);

        uploadTask.addOnSuccessListener(UploadActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                productName.setText("Done");

                String downloadUrl = taskSnapshot.getDownloadUrl().toString();

                Grocery grocery = new Grocery(imageName);

                grocery.setImage(downloadUrl);

                grocery.setCurrentPrice(currentPrice.getText().toString());
                grocery.setOriginalPrice(originalPrice.getText().toString());
                //grocery.setImage(downloadUrl);
                Groceriesreference.push().setValue(grocery);

                Log.d("Download Url",downloadUrl);




            }
        });

       // Grocery grocery = new Grocery(imageName);
        //grocery.setImage(downloadUrl);
        //Groceriesreference.push().setValue(grocery);
        //Grocery grocery = new Grocery(imageName);
        //Groceriesreference.push().setValue(grocery);


    }



    public void selectImage()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");


       // boolean b = intent.resolveActivity( (getPackageManager()!=null));

        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intent,89);
        }
    }


    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data)
    {
        if(reqCode==89 && resCode == RESULT_OK)
        {
           // Bitmap thumbnail = data.("data");
           //

           // Log.d("Bitmap is null ?", (thumbnail==null)+"");
            Uri imageUri = data.getData();

            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uplaodProductImage.setImageBitmap(thumbnail);
            Log.d("Image Url",imageUri+"");
        }
    }





    public void uploadImageFromGallery(Uri uri)
    {

    }

}
