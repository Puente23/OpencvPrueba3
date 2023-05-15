package com.josuepuente.opencvprueba3.vistacontrolador;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

//import com.josuepuente.opencvprueba3.CNN.Main;
import com.josuepuente.opencvprueba3.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = "IdentificacionActivity";
    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mRgba;
    private Mat mGray;
   private MatOfRect objects;
   private Mat grayFrame;


    private final BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) {
                Log.i(TAG, "OpenCV loaded successfully");
                mOpenCvCameraView.enableView();
            } else {
                super.onManagerConnected(status);
            }
        }
    };
    public MainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mOpenCvCameraView = findViewById(R.id.camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        
    }

    CascadeClassifier mCascadeClassifier;
    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            try {
                InputStream is = getResources().openRawResource(R.raw.cascade_classifier);
                File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                File cascadeFile = new File(cascadeDir, "cascade_classifier.xml");
                FileOutputStream os = new FileOutputStream(cascadeFile);
                Log.d(TAG, "todo bien");
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                is.close();
                os.close();
                mCascadeClassifier = new CascadeClassifier(cascadeFile.getAbsolutePath());
                if (mCascadeClassifier.empty()) {
                    Log.e(TAG, "Failed to load cascade classifier");
                    mCascadeClassifier = null;
                }
            } catch (Exception e) {
                Log.e(TAG, "Error loading cascade", e);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        grayFrame = new Mat(height, width, CvType.CV_8UC1);
        }

    @Override
    public void onCameraViewStopped() {
        objects.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        grayFrame=inputFrame.rgba();
       // Mat[]resultadodered =Main.main(grayFrame);
      //  Mat grayframe2= convertirMatArrayAMat(resultadodered );


        objects= new MatOfRect();
        if (mCascadeClassifier!= null){
            mCascadeClassifier.detectMultiScale(grayFrame, objects, 1.1, 3, 0, // TODO: obj-detect.CV_HAAR_SCALE_IMAGE
                    new Size(200,200), new Size());

        }
        for (Rect rect : objects.toArray()) {
            if (rect.area() < 200) {
                Imgproc.rectangle(grayFrame, rect.tl(), rect.br(), new Scalar(255, 0, 0), 2);


            } else {
                Imgproc.rectangle(grayFrame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 2);
            }
        }
        return grayFrame;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpenCvCameraView);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    public Mat convertirMatArrayAMat(Mat[] matrizArray) {
        // Crear una lista de las matrices
        List<Mat> matrices = new ArrayList<>();
        Collections.addAll(matrices, matrizArray);

        // Concatenar las matrices verticalmente
        Mat resultado = new Mat();
        Core.vconcat(matrices, resultado);

        return resultado;
    }
}