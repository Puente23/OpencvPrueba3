package com.josuepuente.opencvprueba3.CNN.Funciones.Var;
/*
import com.josuepuente.opencvprueba3.CNN.Funciones.Optimizador;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.HashMap;
import java.util.Map;

public class ParametrosRedNeuronal {
    private final Map<String, INDArray> parametros;
    private final int nCapas;
    private final int[] tamanoEntrada;
    private final int[] tamanoCapasOcultas;
    private final int tamanoSalida;


    public ParametrosRedNeuronal(int[] tamanoEntrada, int tamanoSalida) {
          this.tamanoEntrada = tamanoEntrada;
            this.tamanoSalida = tamanoSalida;
            this.nCapas = 2; // Asumimos que hay dos capas ocultas
            this.tamanoCapasOcultas = new int[] {16, 32}; // Tamaños arbitrarios de las capas ocultas
            this.parametros = new HashMap<>();
            inicializar();
        }

        public INDArray obtenerParametro(String nombre) {
            return parametros.get(nombre);
        }

        public void actualizarParametro(String nombre, INDArray nuevoValor) {
            parametros.put(nombre, nuevoValor);
        }

    public void inicializar() {
        int nCapas = this.nCapas;
        for (int i = 0; i < nCapas; i++) {
            int nEntradas;
            int nSalidas;
            if (i == 0) {
                nEntradas = this.tamanoEntrada[0] * this.tamanoEntrada[1] * this.tamanoEntrada[2];
            } else {
                nEntradas = this.tamanoCapasOcultas[i-1];
            }
            nSalidas = this.tamanoCapasOcultas[i];
            this.parametros.put("W" + i, Nd4j.randn(nEntradas, nSalidas).mul(Math.sqrt(2.0 / nEntradas)));
            this.parametros.put("b" + i, Nd4j.zeros(1, nSalidas));
        }
        int nEntradas = this.tamanoCapasOcultas[nCapas-1];
        int nSalidas = this.tamanoSalida;
        this.parametros.put("W" + nCapas, Nd4j.randn(nEntradas, nSalidas).mul(Math.sqrt(2.0 / nEntradas)));
        this.parametros.put("b" + nCapas, Nd4j.zeros(1, nSalidas));
    }

    public void actualizar(INDArray gradiente, Optimizador optimizador) {
    }
}
*/