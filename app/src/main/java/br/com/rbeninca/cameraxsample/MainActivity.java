package br.com.rbeninca.cameraxsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonTakePhoto:
                break;
            case R.id.imageButtonFlip:
                break;
            case R.id.imageButtonVideo:
                break;
        }
    }
}