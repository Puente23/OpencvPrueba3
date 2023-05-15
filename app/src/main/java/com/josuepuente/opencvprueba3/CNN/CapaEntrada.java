package com.josuepuente.opencvprueba3.CNN;
/*
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class CapaEntrada {

    private final int ancho;
    private final int alto;
    private final int profundidad;

    public CapaEntrada(int ancho, int alto, int profundidad) {
        this.ancho = ancho;
        this.alto = alto;
        this.profundidad = profundidad;
    }

    public Mat procesar(Mat imagen) {
        Mat entrada = new Mat();
        Imgproc.cvtColor(imagen, entrada, Imgproc.COLOR_RGBA2GRAY);
        Core.transpose(entrada, entrada);
        Core.flip(entrada, entrada, 1);
        Imgproc.resize(entrada, entrada, new Size(ancho, alto));
        entrada.convertTo(entrada, -1, 1.0/255);
        return entrada.reshape(0, 1);
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getProfundidad() {
        return profundidad;
    }

}
*/