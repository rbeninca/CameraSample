package br.com.rbeninca.cameraxsample;

import android.content.Context;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraXHelper {
    private Context context;
    private LifecycleOwner   lifecycleOwner;
    private PreviewView previewView;
    private CameraSelector currentCamera;

    public CameraXHelper(Context context, LifecycleOwner lifecycleOwner, PreviewView previewView) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.previewView = previewView;
        this.currentCamera = cameraSelector();
    }

    public void startCamera(){
        //Recupera o proverdo de seviços do cameraX em uma instancia de ListenableFuture
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture.addListener(()-> {
            //Recuperando o CameraXProvider
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();//Desvincula todos os casos de uso do provedor inicialmente
                bindPreview(cameraProvider); //Vincula o preview ao provedor de camera
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }, ContextCompat.getMainExecutor(context));
    }

    public void bindPreview(ProcessCameraProvider cameraProvider){
        Preview previewUseCase = new Preview.Builder().build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(lifecycleOwner,currentCamera, previewUseCase);

    }

    public void checkPermissions(String[] permissions) {
        ActivityCompat.requestPermissions((MainActivity)context, permissions, 1);

    }
    public CameraSelector cameraSelector(){
      if( currentCamera==CameraSelector.DEFAULT_BACK_CAMERA){
          currentCamera=CameraSelector.DEFAULT_FRONT_CAMERA;
          return currentCamera;

      }else {
            currentCamera=CameraSelector.DEFAULT_BACK_CAMERA;
            return currentCamera;
      }

    }
    public void switchCamera(){
        cameraSelector();
        startCamera();

    }
}
