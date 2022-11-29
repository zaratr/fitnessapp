package com.doinWondrs.betterme.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import com.doinWondrs.betterme.R;
import com.doinWondrs.betterme.activities.mlhelpers.CameraXViewModel;
import com.doinWondrs.betterme.activities.mlhelpers.GraphicOverlay;
import com.doinWondrs.betterme.activities.mlhelpers.VisionImageProcessor;
import com.doinWondrs.betterme.activities.posedetector.PoseDetectorProcessor;
import com.doinWondrs.betterme.activities.preference.PreferenceUtils;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;

import java.util.ArrayList;

public class TrainerActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final String TAG = "TrainerActivity";
    private ArrayList<String> REQUIRED_RUNTIME_PERMISSIONS  = new ArrayList<>();
    private final int PERMISSIONS_REQUESTS = 1;

    @NonNull
    private ProcessCameraProvider cameraProvider;
    @NonNull private Preview previewUseCase;
    @Nullable
    private ImageAnalysis analysisUseCase;
    @Nullable private VisionImageProcessor imageProcessor;
    private static final String POSE_DETECTION = "Pose Detection";
    private String selectedModel = POSE_DETECTION;
    private PreviewView previewView;
    private GraphicOverlay graphicOverlay;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private CameraSelector cameraSelector;
    private static final String STATE_SELECTED_MODEL = "selected_model";
    private boolean needUpdateGraphicOverlayImageSourceInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

        REQUIRED_RUNTIME_PERMISSIONS.add(Manifest.permission.CAMERA);
        REQUIRED_RUNTIME_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        REQUIRED_RUNTIME_PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        /* after permissions checking we do the code here*/


        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
        if (savedInstanceState != null) {
            selectedModel = savedInstanceState.getString(STATE_SELECTED_MODEL, POSE_DETECTION);
        }
        previewView = findViewById(R.id.preview_view);
        if (previewView == null) {
            Log.d(TAG, "previewView is null");
        }
        graphicOverlay = findViewById(R.id.graphic_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(CameraXViewModel.class)
                .getProcessCameraProvider()
                .observe(
                        this,
                        provider -> {
                            cameraProvider = provider;
                            bindAllCameraUseCases();
                        });


        /* after permissions checking we do the code here*/
        if(!allRuntimePermissionsGranted()){
            getRuntimePermissions();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bindAllCameraUseCases();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (imageProcessor != null) {
            imageProcessor.stop();
        }
    }

    private void bindAllCameraUseCases()
    {
        if(cameraProvider!= null)
        {
            cameraProvider.unbindAll();
            bindPreviewUseCase();
            bindAnalysisUseCase();
        }
    }

    private void bindPreviewUseCase()
    {
        if (!PreferenceUtils.isCameraLiveViewportEnabled(this)) {
            return;
        }
        if (cameraProvider == null) {
            return;
        }
        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        Preview.Builder builder = new Preview.Builder();
        Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
        if (targetResolution != null) {
            builder.setTargetResolution(targetResolution);
        }
        previewUseCase = builder.build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, previewUseCase);
    }


    private void bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return;
        }
        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }
        if (imageProcessor != null) {
            imageProcessor.stop();
        }

        try {
            PoseDetectorOptionsBase poseDetectorOptions =
                    PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
//            boolean shouldShowInFrameLikelihood =
//                    PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
//            boolean visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
//            boolean rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
//            boolean runClassification = PreferenceUtils.shouldPoseDetectionRunClassification(this);
            imageProcessor =
                    new PoseDetectorProcessor(
                            this,
                            poseDetectorOptions,
                            true, //shouldShowInFrameLikelihood,
                            true, //visualizeZ,
                            true, //rescaleZ,
                            true, //runClassification,
                            /* isStreamMode = */ true);
        } catch (Exception e) {
            Log.e(TAG, "Can not create image processor: " + selectedModel, e);
            Toast.makeText(
                            getApplicationContext(),
                            "Can not create image processor: " + e.getLocalizedMessage(),
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
        Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
        if (targetResolution != null) {
            builder.setTargetResolution(targetResolution);
        }

        analysisUseCase = builder.build();

        needUpdateGraphicOverlayImageSourceInfo = true;
        analysisUseCase.setAnalyzer(
                // imageProcessor.processImageProxy will use another thread to run the detection underneath,
                // thus we can just runs the analyzer itself on main thread.
                ContextCompat.getMainExecutor(this),
                imageProxy -> {
                    if (needUpdateGraphicOverlayImageSourceInfo) {
                        boolean isImageFlipped = lensFacing == CameraSelector.LENS_FACING_BACK;
                        int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
                        if (rotationDegrees == 0 || rotationDegrees == 180) {
                            graphicOverlay.setImageSourceInfo(
                                    imageProxy.getWidth(), imageProxy.getHeight(), isImageFlipped);
                        } else {
                            graphicOverlay.setImageSourceInfo(
                                    imageProxy.getHeight(), imageProxy.getWidth(), isImageFlipped);
                        }
                        needUpdateGraphicOverlayImageSourceInfo = false;
                    }
                    try {
                        imageProcessor.processImageProxy(imageProxy, graphicOverlay);
                    } catch (MlKitException e) {
                        Log.e(TAG, "Failed to process image. Error: " + e.getLocalizedMessage());
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, analysisUseCase);
    }

    /**
     * PERMISSIONS CREDITOR SECTION
     * since we are using camera and operations, Accredation is needed.
     * @return
     */
    private boolean allRuntimePermissionsGranted()
    {
        for(String permission : REQUIRED_RUNTIME_PERMISSIONS)
        {
            if(!isPermissionGranted(this, permission))
                return false;
        }
        return true;
    }

    private void getRuntimePermissions()
    {
        ArrayList<String>  permissionsToRequest = new ArrayList<>();
        for(String permission : REQUIRED_RUNTIME_PERMISSIONS)
        {
            if(!isPermissionGranted(this, permission))
            {
                permissionsToRequest.add(permission);
            }
        }
        if(!permissionsToRequest.isEmpty())
        {
            ActivityCompat.requestPermissions(this,
                    (String[]) permissionsToRequest.toArray(new String[0]),
                    PERMISSIONS_REQUESTS );
        }
    }
    private boolean isPermissionGranted(Context context, String permission)
    {
        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
        {
            Log.i(TAG, "Permission Granted: " + permission );
            return true;
        }
        Log.i(TAG, "Permission Not Granted: " + permission );
        return false;
    }

}