package com.josuepuente.opencvprueba3.vistacontrolador;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.josuepuente.opencvprueba3.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG = "IdentificacionActivity";
    private CameraBridgeViewBase mOpenCvCameraView;
    private static final int cols = 10;
    private static final int rows = 10;
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
    public MainActivity2() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main2);
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
        Mat grayFrame = inputFrame.gray(); // Convertimos a escala de grises para una mejor detección
        MatOfRect objects = new MatOfRect();
        if (mCascadeClassifier != null && !mCascadeClassifier.empty()) {
            mCascadeClassifier.detectMultiScale(grayFrame, objects, 1.1, 3, 0, new Size(200, 200), new Size());
        }
        Mat colorFrame = inputFrame.rgba(); // Conservamos la imagen original a color para dibujar el grid
        Mat ocv = new Mat(400,400,16,Scalar.all(127));

        for (Rect rect : objects.toArray()) {
            Imgproc.putText(colorFrame, "Maiz", new Point(ocv.cols() / 4, ocv.rows() / 2),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(255, 0, 0), 2);
            Imgproc.rectangle(colorFrame, rect.tl(), rect.br(), new Scalar(255, 0, 0), 2);
            dibujarGrid(colorFrame, rect);


            // Si el objeto detectado es maíz, generamos un grid para contar sus granos
            /*if (esMaiz(rect)) {
                dibujarGrid(colorFrame, rect);
            }*/
        }
        return colorFrame;
    }
    // Función que determina si el objeto detectado es maíz
    private boolean esMaiz(Rect rect) {
        // En este ejemplo, asumimos que el objeto detectado es maíz si su ancho es mayor que su alto
        return rect.width > rect.height;
    }

    // Función que dibuja un grid para contar los granos de maíz en la región especificada por el rectángulo
    private void dibujarGrid(Mat frame, Rect rect) {
        // Calculamos el tamaño de cada celda en el grid
        int gridWidth = rect.width / cols;
        int gridHeight = rect.height / rows;

        // Dibujamos el grid en la imagen
        for (int i = 1; i < rows; i++) {
            int y = rect.y + i * gridHeight;
            Imgproc.line(frame, new Point(rect.x, y), new Point(rect.x + rect.width, y), new Scalar(0, 255, 0), 2);
        }
        for (int i = 1; i < cols; i++) {
            int x = rect.x + i * gridWidth;
            Imgproc.line(frame, new Point(x, rect.y), new Point(x, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }
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
}
