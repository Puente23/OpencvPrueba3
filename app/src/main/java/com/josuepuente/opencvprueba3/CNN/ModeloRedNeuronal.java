package com.josuepuente.opencvprueba3.CNN;
/*
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ModeloRedNeuronal {
    private final CascadeClassifier clasificador;
    private final MatOfRect objetos = new MatOfRect();
    private final Mat imagenGris = new Mat();

    private final Mat image = new Mat();

    public ModeloRedNeuronal() {
        // Cargar el archivo cascade_classifier.xml
        String rutaArchivo = "ruta/a/cascade_classifier.xml";
        clasificador = new CascadeClassifier(rutaArchivo);
    }

    public boolean evaluar(Mat imagen) {

        Imgproc.cvtColor(imagen, imagenGris, Imgproc.COLOR_BGR2GRAY);
        clasificador.detectMultiScale(imagenGris, objetos);
        Rect[] objetosArray = objetos.toArray();
        for (int i = 0; i < objetosArray.length; i++) {
            if (objetosArray[i].x >= 0 && objetosArray[i].y >= 0) {
                // La imagen contiene maíz
                return true;
            }
        }
        // La imagen no contiene maíz
        return false;
    }

    public void prepararModelo(Mat image) {
        if (clasificador.empty()) {
            System.err.println("No se pudo cargar el clasificador");
            return;
        }
        System.out.println("Clasificador cargado correctamente");

        // Ajustar los parámetros del clasificador
        clasificador.detectMultiScale(image, objetos, 1.1);

    }
}

 */