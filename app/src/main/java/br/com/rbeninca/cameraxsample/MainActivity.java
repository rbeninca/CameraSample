package br.com.rbeninca.cameraxsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;
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
    PreviewView previewView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    String permissions [] = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    CameraXHelper cameraXHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previewView = findViewById(R.id.previewView);

         cameraXHelper = new CameraXHelper(this,this, previewView);
        requestPermissions(permissions, PackageManager.PERMISSION_GRANTED);
        cameraXHelper.checkPermissions(permissions);
        cameraXHelper.startCamera();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonTakePhoto:

                break;
            case R.id.imageButtonFlip:
                cameraXHelper.switchCamera();
                break;
            case R.id.imageButtonVideo:
                break;
        }
    }
}