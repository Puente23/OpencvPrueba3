package com.josuepuente.opencvprueba3.CNN.Funciones;
/*
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public abstract class FuncionActivacion {

    public abstract float[][][] aplicar(float[][][] entrada);

    public abstract float[][][] calcularGradiente(float[][][] entrada, float[][][] gradienteSalida);
    public static INDArray rotarFiltros(INDArray gradienteTensor) {
        int tamañoFiltro = (int) gradienteTensor.shape()[3];
        INDArray gradienteRotado = Nd4j.create(gradienteTensor.shape());

        for (int i = 0; i < tamañoFiltro; i++) {
            INDArray filtro = gradienteTensor.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(i));
            Core.rotate((Mat) filtro, (Mat) gradienteRotado.get(NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.point(i)), Core.ROTATE_180);
        }
        return gradienteRotado;
    }

    public static INDArray añadirPadding(INDArray entrada, int padding, double valorPadding) {
        int tamañoBatch = (int) entrada.shape()[0];
        int númeroCanales = (int) entrada.shape()[1];
        int anchoEntrada = (int) entrada.shape()[2];
        int altoEntrada = (int) entrada.shape()[3];

        // Creamos una nueva matriz con el padding añadido
        int anchoSalida = anchoEntrada + 2 * padding;
        int altoSalida = altoEntrada + 2 * padding;
        INDArray salida = Nd4j.zeros(tamañoBatch, númeroCanales, anchoSalida, altoSalida);
        salida.addi(valorPadding);

        // Copiamos los valores de la entrada en la salida, respetando el padding
        INDArrayIndex[] indicesEntrada = new INDArrayIndex[]{
                NDArrayIndex.all(),
                NDArrayIndex.all(),
                NDArrayIndex.interval(padding, anchoEntrada + padding),
                NDArrayIndex.interval(padding, altoEntrada + padding)
        };
        INDArray submatrizEntrada = entrada.get(indicesEntrada);
        INDArrayIndex[] indicesSalida = new INDArrayIndex[]{
                NDArrayIndex.all(),
                NDArrayIndex.all(),
                NDArrayIndex.interval(0, anchoSalida),
                NDArrayIndex.interval(0, altoSalida)
        };
        salida.put(indicesSalida, submatrizEntrada);

        return salida;
    }


}
*/