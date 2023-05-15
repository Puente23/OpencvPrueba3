package com.josuepuente.opencvprueba3.CNN;
/*
import com.josuepuente.opencvprueba3.CNN.Funciones.FuncionActivacion;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CapasConvolucionales {

    private final int nEntradas;
    private final int nFiltros;
    private int tamanoFiltro;
    private final int stride;
    private final int padding; // Added variable for padding
    private final double valorPadding;

    private final Mat[] filtros;
    private final Mat[] salidas;
    private INDArray gradienteSalida; // Added variable for gradient

    public CapasConvolucionales(int nEntradas, int nFiltros, int stride , int tamFiltros, int padding, double valorPadding) {
        this.nEntradas = nEntradas;
        this.nFiltros = nFiltros;
        this.tamanoFiltro = tamanoFiltro;
        this.stride = stride;
        this.padding = padding; // Initialize padding variable
        this.valorPadding = valorPadding; // Initialize padding value variable

        filtros = new Mat[nFiltros];
        for (int i = 0; i < nFiltros; i++) {
            filtros[i] = new Mat(tamFiltros, tamFiltros, CvType.CV_32F);
            Core.randn(filtros[i], 0, 1);
        }

        salidas = new Mat[nFiltros];
    }

    public Mat[] procesar(Mat entrada) {
        for (int i = 0; i < nFiltros; i++) {
            Mat salida = new Mat();
            Imgproc.filter2D(entrada, salida, -1, filtros[i]);
            salidas[i] = salida;
        }
        return salidas;
    }
    public INDArray[] retropropagacion(INDArray entrada) {
        // Calculamos el gradiente con respecto a la salida de la capa de convolución
        int tamañoBatch = (int) gradienteSalida.shape()[0];
        int tamañoSalidaX = (int) gradienteSalida.shape()[1];
        int tamañoSalidaY = (int) gradienteSalida.shape()[2];
        int númeroFiltros = (int) gradienteSalida.shape()[3];
        int tamañoFiltro = tamanoFiltro;
        int númeroCanales = nFiltros;
        INDArray gradiente = gradienteSalida.mul(Nd4j.ones(tamañoBatch, tamañoSalidaX, tamañoSalidaY, númeroFiltros));

        // Invertimos el padding y stride
        int paddingInvertido = tamanoFiltro - 1 - padding;
        int strideInvertido = 1 / stride;

        // Añadimos padding a la entrada
        INDArray entradaConPadding = FuncionActivacion.añadirPadding(entrada, padding, valorPadding);

        // Creamos un tensor con los gradientes de la salida y aplicamos la rotación del filtro
        INDArray gradienteTensor = gradiente.reshape(new int[]{tamañoBatch, tamañoSalidaX, tamañoSalidaY, númeroFiltros});
        INDArray gradienteRotado = FuncionActivacion.rotarFiltros(gradienteTensor);

        // Calculamos los gradientes con respecto a los pará
        // Calculamos los gradientes con respecto a los parámetros del filtro y la entrada
        INDArray gradienteFiltro = Nd4j.zeros(númeroFiltros, númeroCanales, tamañoFiltro, tamañoFiltro);
        INDArray gradienteEntrada = Nd4j.zeros(tamañoBatch, númeroCanales, nEntradas + 2 * padding, nFiltros + 2 * padding);
        for (int b = 0; b < tamañoBatch; b++) {
            for (int i = 0; i < tamañoSalidaX; i++) {
                for (int j = 0; j < tamañoSalidaY; j++) {
                    INDArray entradaTrozo = entradaConPadding.get(
                            NDArrayIndex.interval(b, b + 1),
                            NDArrayIndex.all(),
                            NDArrayIndex.interval(i * strideInvertido, i * strideInvertido + tamañoFiltro),
                            NDArrayIndex.interval(j * strideInvertido, j * strideInvertido + tamañoFiltro)
                    );
                    INDArray gradienteTrozo = gradienteRotado.get(
                            NDArrayIndex.interval(b, b + 1),
                            NDArrayIndex.all(),
                            NDArrayIndex.point(i),
                            NDArrayIndex.point(j)
                    );
                    for (int f = 0; f < númeroFiltros; f++) {
                        gradienteFiltro.put(new INDArrayIndex[]{
                                        NDArrayIndex.point(f),
                                        NDArrayIndex.all(),
                                        NDArrayIndex.all(),
                                        NDArrayIndex.all()},
                                gradienteFiltro.get(new INDArrayIndex[]{
                                                NDArrayIndex.point(f),
                                                NDArrayIndex.all(),
                                                NDArrayIndex.all(),
                                                NDArrayIndex.all()})
                                        .add(entradaTrozo.mul(gradienteTrozo.getDouble(0, f))));
                    }
                    INDArray[] filtrosIND = new INDArray[filtros.length];
                    // Convertir arreglo de Mat a arreglo de INDarray
                    int k;
                    for (k=0; k < filtros.length; k++) {
                        filtrosIND[k] = Nd4j.create(filtros[k].getNativeObjAddr());
                    }
                    gradienteEntrada.put(
                            new INDArrayIndex[]{
                                    NDArrayIndex.interval(b, b + 1),
                                    NDArrayIndex.all(),
                                    NDArrayIndex.interval(i * strideInvertido, i * strideInvertido + tamañoFiltro),
                                    NDArrayIndex.interval(j * strideInvertido, j * strideInvertido + tamañoFiltro)},
                            gradienteEntrada.get(new INDArrayIndex[]{
                                            NDArrayIndex.interval(b, b + 1),
                                            NDArrayIndex.all(),
                                            NDArrayIndex.interval(i * strideInvertido, i * strideInvertido + tamañoFiltro),
                                            NDArrayIndex.interval(j * strideInvertido, j * strideInvertido + tamañoFiltro)})
                                    .add(FuncionActivacion.rotarFiltros(gradienteTrozo).mmul(FuncionActivacion.rotarFiltros(filtrosIND[k]))
                                            .get(NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.all(), NDArrayIndex.all())));
                }
            }
        }

        // Eliminamos el padding de los gradientes de la entrada
        INDArray gradienteEntradaSinPadding = gradienteEntrada.get(
                NDArrayIndex.all(),
                NDArrayIndex.all(),
                NDArrayIndex.interval(paddingInvertido, nEntradas + paddingInvertido),
                NDArrayIndex.interval(paddingInvertido, nEntradas + paddingInvertido)
        );

        // Devolvemos los gradientes con respecto a los parámetros del filtro y la entrada
        return new INDArray[]{gradienteFiltro, gradienteEntradaSinPadding};
    }
}
*/
