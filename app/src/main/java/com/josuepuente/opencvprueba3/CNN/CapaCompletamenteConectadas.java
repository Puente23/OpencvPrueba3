package com.josuepuente.opencvprueba3.CNN;
/*
import org.nd4j.linalg.api.ndarray.INDArray;
import org.tensorflow.lite.Tensor;
import java.util.Random;

public class CapaCompletamenteConectadas {
    private final int numeroEntradas;
    private final int numeroSalidas;
    private float[][] pesos;
    private float[] sesgos;
    private final Random aleatorio;

    public CapaCompletamenteConectadas(int numeroEntradas, int numeroSalidas) {
        this.numeroEntradas = numeroEntradas;
        this.numeroSalidas = numeroSalidas;
        this.pesos = new float[numeroEntradas][numeroSalidas];
        this.sesgos = new float[numeroSalidas];
        this.aleatorio = new Random();
        inicializarPesos();
        inicializarSesgos();
    }

    private void inicializarPesos() {
        for (int i = 0; i < numeroEntradas; i++) {
            for (int j = 0; j < numeroSalidas; j++) {
                pesos[i][j] = aleatorio.nextFloat() - 0.5f;
            }
        }
    }

    private void inicializarSesgos() {
        for (int i = 0; i < numeroSalidas; i++) {
            sesgos[i] = aleatorio.nextFloat() - 0.5f;
        }
    }

    public float[] calcularSalida(Tensor entrada) {
        if (!entrada.shape().equals(entrada.shape().length)) {
            throw new IllegalArgumentException("La entrada debe tener " + numeroEntradas + " elementos");
        }

        float[] salida = new float[numeroSalidas];
        for (int j = 0; j < numeroSalidas; j++) {
            float suma = 0;
            for (int i = 0; i < numeroEntradas; i++) {
                suma += entrada.numElements() * pesos[i][j];
            }
            salida[j] = activacion(suma + sesgos[j]);
        }
        return salida;
    }


    private float activacion(float x) {
        return (float) (1.0 / (1.0 + Math.exp(-x)));
    }

    public int getNumeroEntradas() {
        return numeroEntradas;
    }

    public int getNumeroSalidas() {
        return numeroSalidas;
    }

    public float[][] getPesos() {
        return pesos;
    }

    public float[] getSesgos() {
        return sesgos;
    }

    public void setPesos(float[][] pesos) {
        if (pesos.length != numeroEntradas || pesos[0].length != numeroSalidas) {
            throw new IllegalArgumentException("La matriz de pesos debe tener dimensiones " + numeroEntradas + "x" + numeroSalidas);
        }
        this.pesos = pesos;
    }

    public void setSesgos(float[] sesgos) {
        if (sesgos.length != numeroSalidas) {
            throw new IllegalArgumentException("El vector de sesgos debe tener " + numeroSalidas + " elementos");
        }
        this.sesgos = sesgos;
    }

    public INDArray[] retropropagacion(INDArray[] salida) {
        return salida;
    }
}

*/