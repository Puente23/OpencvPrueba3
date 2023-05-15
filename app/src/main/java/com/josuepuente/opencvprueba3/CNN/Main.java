package com.josuepuente.opencvprueba3.CNN;
/*
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.tensorflow.lite.Tensor;

public class Main {

    public static  Mat[] main(Mat inputFrame) {

        // Cargar el marco de video
        Mat colorFrame = inputFrame;

        // Ejecutar la red neuronal convolucional
        Mat[] resultado = ejecutarRedNeuronal(colorFrame);

        return resultado;
        // Mostrar el resultado

        // ...
    }

    private static Mat[] ejecutarRedNeuronal(Mat colorFrame) {
        // Crear un objeto de la clase ModeloRedNeuronal
        ModeloRedNeuronal modelo = new ModeloRedNeuronal();
        //secuencia entrada, poolign, capas Convolcionales, capaCompletamente Conectadas, retropropagacion

        // Cargar el modelo previamente entrenado
        modelo.prepararModelo(colorFrame);

        CapaEntrada capa=new CapaEntrada(50,50,3);
        Mat resultadoEntrada = capa.procesar(colorFrame);

        // Crear un objeto de la clase CapasConvolucionales
        CapasConvolucionales convolucionales = new CapasConvolucionales(1,5,2,5,3,2.4);



        // Aplicar la primera capa convolucional
        Mat[] resultadoConvolucional = convolucionales.procesar(resultadoEntrada);

        // Crear un objeto de la clase CapasPooling
        int[] size = {2, 3};
        int stridepool = 3;
        CapasPooling pooling = new CapasPooling(size, stridepool);

        // Aplicar la primera capa de pooling
        Tensor resultadoPooling =  pooling.aplicar(resultadoConvolucional);

        CapaCompletamenteConectadas capaCompletament =new CapaCompletamenteConectadas(1,3);

        float[] resultadoCompletament=capaCompletament.calcularSalida(resultadoPooling);
        // Repetir para las capas restantes
        // ...
        INDArray indArray = Nd4j.create(resultadoCompletament);
        INDArray[] a=convolucionales.retropropagacion(indArray);
        Mat[] resultadoFinal=convertirINDArrayAMat(a);


        // Devolver el resultado final de la red neuronal
        return resultadoFinal;
    }

    public static Mat[] convertirINDArrayAMat(INDArray[] entrada) {
        int n = entrada.length;
        int altura = (int) entrada[0].size(2);
        int ancho = (int) entrada[0].size(3);
        int canales = (int) entrada[0].size(1);
        Mat[] matrices = new Mat[n];
        for (int i = 0; i < n; i++) {
            float[] data = entrada[i].data().asFloat();
            Mat mat = new Mat(altura, ancho, CvType.CV_32FC(canales));
            mat.put(0, 0, data);
            matrices[i] = mat;
        }
        return matrices;
    }

}
*/