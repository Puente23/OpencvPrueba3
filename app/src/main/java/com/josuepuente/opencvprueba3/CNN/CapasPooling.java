package com.josuepuente.opencvprueba3.CNN;
/*
La creación de imágenes es una tarea fundamental en diversas áreas,
como la fotografía, el cine, el diseño gráfico y la inteligencia artificial.
En este último campo, la creación de imágenes se utiliza en aplicaciones
como el procesamiento de imágenes médicas, la detección de objetos
y la generación de imágenes realistas.

Para crear una imagen, primero se debe definir su tamaño y tipo de datos,
como el número de píxeles y canales de color. Luego, se pueden utilizar
diversas técnicas para generar los valores de los píxeles, como la manipulación
de matrices y funciones matemáticas. Estos valores se pueden representar en un
objeto org.pytorch.Tensor de Pytorch, que es una matriz que contiene los datos
de la imagen.

Una vez que se tiene el objeto se convierte a Mat, que se puede convertir a un objeto Tensor
de TensorFlow Lite utilizando la clase TensorBuffer y aplicando una conversion. Para ello, se debe crear
un objeto TensorBuffer con las dimensiones y tipo de datos adecuados,
y copiar los datos del objeto Mat al objeto Tensor utilizando el
método creado en esta seccion. El objeto Tensor resultante se
puede utilizar como entrada para un modelo de TensorFlow Lite que
realice tareas como la detección de objetos o la generación de imágenes.

En resumen, la creación de imágenes es una tarea importante en
diversas áreas, y se puede lograr utilizando técnicas como la
 manipulación de matrices y la conversión de objetos Mat a objetos Tensor.
*/
/*
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
public class CapasPooling {
        private final int[] poolSize;

        public CapasPooling(int[] size, int stridePool) {
            this.poolSize = size;
        }

        public Tensor aplicar(Mat[] entrada) {
            // Obtener las dimensiones de la matriz de entrada
            Tensor t=convertirMatATensor(entrada);
            int[] entradaShape = t.shape();

// Calcular las dimensiones de la matriz de salida después del pooling
            int[] salidaShape = new int[]{
                    entradaShape[0],
                    entradaShape[1] / poolSize[0],
                    entradaShape[2] / poolSize[1],
                    entradaShape[3]
            };

// Obtener los datos de la matriz de entrada en un arreglo de float
            float[] entradaArray = tensorToFloatArray(t);

// Crear un arreglo de float para almacenar los datos de salida
            float[] salidaArray = new float[salidaShape[0] * salidaShape[1] * salidaShape[2] * salidaShape[3]];

// Iterar sobre los datos de la matriz de entrada
            for (int batch = 0; batch < entradaShape[0]; batch++) {
                for (int channel = 0; channel < entradaShape[3]; channel++) {
                    for (int y = 0; y < entradaShape[2]; y += poolSize[1]) {
                        for (int x = 0; x < entradaShape[1]; x += poolSize[0]) {
                            // Calcular el valor máximo en la región de pooling actual
                            float max = reducir(entradaArray, batch, channel, y, x);

                            // Calcular el índice correspondiente en la matriz de salida
                            int salidaIndex = calcularIndiceSalida(batch, channel, y, x, salidaShape);

                            // Asignar el valor máximo a la matriz de salida
                            salidaArray[salidaIndex] = max;
                        }
                    }
                }
            }

            ///traspaso de salidas de int a long
            long[] longArray = new long[salidaArray.length];

            for (int i = 0; i < salidaShape.length; i++) {
                longArray[i] = (long) salidaShape[i];
            }


// Convertir el arreglo de salida en un objeto Tensor
            org.pytorch.Tensor salidaTensor0 = org.pytorch.Tensor.fromBlob(salidaArray, longArray);

            Tensor salidaTensor = pytorchToTensorflow(salidaTensor0);

            return salidaTensor;
        }

    private float reducir(float[] datos, int batch, int canal, int startY, int startX) {
        int ancho = poolSize[0];
        int alto = poolSize[1];
        int canalesPorCapa=1;
        float max = Float.MIN_VALUE;
        for (int y = startY; y < startY + alto; y++) {
            for (int x = startX; x < startX + ancho; x++) {
                int indice = batch * ancho * alto * canalesPorCapa +
                        y * ancho * canalesPorCapa + x * canalesPorCapa + canal;
                float valor = datos[indice];
                if (valor > max) {
                    max = valor;
                }
            }
        }
        return max;
    }

        /*public float[] tensorToFloatArray(Tensor tensor) {
            int size = tensor.numElements();
            float[] floatArray = new float[size];
            tensor.copyTo(floatArray);
            return floatArray;
        }*/
    ///forma primitiva de solucion de tensores a flotantes
/*
        public static float[] tensorToFloatArray(Tensor tensor) {
            int[] shape = tensor.shape();
            int tensorSize = (int)tensor.numElements();
            float[] floatArray = new float[tensorSize];
            int index = 0;

            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    for (int k = 0; k < shape[2]; k++) {
                        floatArray[index++] = tensor.asReadOnlyBuffer().getFloat();
                    }
                }
            }
            return floatArray;
        }



    // Convertir Mat[] a Tensor
    public Tensor convertirMatATensor(Mat[] entrada) {
        int n = entrada.length;
        int altura = entrada[0].height();
        int ancho = entrada[0].width();
        int canales = entrada[0].channels();
        Mat[] matrices = new Mat[0];
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(n * altura * ancho * canales * 4);
        inputBuffer.order(ByteOrder.nativeOrder());
        for (int i = 0; i < n; i++) {
            Mat mat = matrices[i];
            int size = mat.channels() * mat.cols() * mat.rows();
            byte[] byteData = new byte[size];
            mat.get(0, 0, byteData);
            FloatBuffer floatBuffer = inputBuffer.asFloatBuffer();
            for (int j = 0; j < size; j++) {
                floatBuffer.put((float)(byteData[j] & 0xff));
            }
        }
        org.pytorch.Tensor tensor= org.pytorch.Tensor.fromBlob(inputBuffer, new long[] {n, altura, ancho, canales});
        return pytorchToTensorflow(tensor);
    }



    public Tensor pytorchToTensorflow(org.pytorch.Tensor pyTorchTensor) {
// Obtener la forma del tensor de PyTorch
        long[] shapeLong = pyTorchTensor.shape();
        // Convertir la forma a un arreglo de flotantes
        float[] shapeFloat = new float[shapeLong.length];
        int[] arregloInt = new int[shapeFloat.length];

        for (int i = 0; i < shapeFloat.length; i++) {
            arregloInt[i] = Math.round(shapeFloat[i]);
        }
        for (int i = 0; i < shapeLong.length; i++) {
            shapeFloat[i] = (float) shapeLong[i];
        }

// Obtener los datos del tensor de PyTorch como un arreglo de flotantes
        float[] dataFloat = pyTorchTensor.getDataAsFloatArray();

// Convertir los datos a un arreglo de enteros
        int[] dataInt = new int[dataFloat.length];
        for (int i = 0; i < dataFloat.length; i++) {
            dataInt[i] = (int) dataFloat[i];
        }
// Crear un objeto de tipo TensorBuffer con la forma y los datos
        TensorBuffer tensorBuffer = TensorBuffer.createFixedSize(arregloInt, DataType.FLOAT32);
        tensorBuffer.loadArray(dataInt);

// Crear un objeto de tipo Tensor a partir del TensorBuffer
        // obtener la imagen a partir del TensorBuffer
        TensorImage tensorImage = new TensorImage(tensorBuffer.getDataType());

// convertir la imagen a un arreglo de bytes
        byte[] byteBuffer = tensorImage.getBitmap().getNinePatchChunk();

// crear un arreglo de Mat a partir del arreglo de bytes
        Mat[] mats = new Mat[tensorBuffer.getShape()[0]];
        int rows = tensorBuffer.getShape()[1];
        int cols = tensorBuffer.getShape()[2];
        for (int i = 0; i < mats.length; i++) {
            mats[i] = new Mat(rows, cols, CvType.CV_32FC1);
            mats[i].put(0, 0, byteBuffer, i * rows * cols * 4, rows * cols * 4);
        }
        // crear el objeto Tensor
        Tensor tensor= convertirMatATensor(mats);
        return tensor;
    }

    /**
     * Calcula el índice correspondiente en la matriz de salida.
     *
     * @param batch El índice de lote (batch).
     * @param channel El índice de canal.
     * @param y La coordenada y en la matriz de entrada.
     * @param x La coordenada x en la matriz de entrada.
     * @param salidaShape El tamaño de la matriz de salida.
     * @return El índice correspondiente en la matriz de salida.

    private int calcularIndiceSalida(int batch, int channel, int y, int x, int[] salidaShape) {
        int salidaIndex = batch * salidaShape[1] * salidaShape[2] * salidaShape[3]
                + y / poolSize[1] * salidaShape[2] * salidaShape[3]
                + x / poolSize[0] * salidaShape[3]
                + channel;
        return salidaIndex;
    }



}

*/