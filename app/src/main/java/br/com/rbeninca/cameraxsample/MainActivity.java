package br.com.rbeninca.cameraxsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    String permissions [] = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

    }
    public void takePicktureItent(){
        //Apartir do android 23 verifica se existe permissão de camera

            requestPermissions(permissions, 1);



        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED &&
             ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            // Permissão já concedida, pode iniciar a intenção de captura de imagem
            startCameraIntent();
        }


    }

    private void startCameraIntent() {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }else{
            Toast.makeText(this, "Sem app de camera", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode== REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            if ( extras != null){
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                try {
                    File imageFile;
                    String  filename= new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(new Date());
                    imageFile= new File (
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            , filename+"image.jpg");

                    FileOutputStream fileOutputStream= new FileOutputStream(imageFile);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    Toast.makeText(this, ""+imageFile.getAbsoluteFile() , Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonTakePhoto:
                takePicktureItent();
                break;
            case R.id.imageButtonFlip:
                break;
            case R.id.imageButtonVideo:
                break;
        }
    }
}