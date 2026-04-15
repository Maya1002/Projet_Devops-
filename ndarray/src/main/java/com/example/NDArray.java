package com.example;

import java.util.Arrays;

public class NDArray {

    private float[] data;   // données a plat
    private int[] shape;    // dim
    private int ndim;       // nb dim
    private int size;       // nb total 

    public NDArray(float[] data, int[] shape) {
        this.data = data;
        this.shape = shape;
        this.ndim = shape.length;

        // calcul du size
        this.size = 1;
        for (int s : shape) {
            this.size *= s;
        }

        if (this.size != data.length) {
            throw new IllegalArgumentException("Data size does not match shape");
        }
    }

    //GETTER
    public int getNdim() {
        return ndim;
    }

    public int[] getShape() {
        return shape;
    }

    public int getSize() {
        return size;
    }

    public float[] getData() {
        return data;
    }

    //1D
    public float get(int index) {
        if (ndim != 1) {
            throw new IllegalStateException("Not a 1D array");
        }
        return data[index];
    }

    public void set(int index, float value) {
        if (ndim != 1) {
            throw new IllegalStateException("Not a 1D array");
        }
        data[index] = value;
    }

    //2D
    public float get(int i, int j) {
        if (ndim != 2) {
            throw new IllegalStateException("Not a 2D array");
        }
        int cols = shape[1];
        return data[i * cols + j];
    }

    public void set(int i, int j, float value) {
        if (ndim != 2) {
            throw new IllegalStateException("Not a 2D array");
        }
        int cols = shape[1];
        data[i * cols + j] = value;
    }

    //Fonctions 
    //zeros()
    public static NDArray zeros(int... shape) {
        // calcul taille totale
        int size = 1;
        for (int s : shape) {
            if (s <= 0) throw new IllegalArgumentException("Shape dimensions must be positive");
            size *= s;
        }
        // crée tab rempli de 0
        float[] data = new float[size]; // init a 0 automatiquement 
        return new NDArray(data, shape);
    }

    //array()
    //1D
    public static NDArray array(float[] input) {
        return new NDArray(input, new int[]{input.length});
    }
    //2D
    public static NDArray array(float[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        // vérifier que toutes les lignes ont la même taille
        for (int i = 0; i < rows; i++) {
            if (input[i].length != cols) {
                throw new IllegalArgumentException("Inconsistent row sizes");
            }
        }

        // flatten
        float[] data = new float[rows * cols];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[index++] = input[i][j];
            }
        }

        return new NDArray(data, new int[]{rows, cols});
    }

    public static NDArray arange(float start, float stop, float step) {
        if (step == 0) throw new IllegalArgumentException("Step cannot be 0");
        int size = (int) ((stop - start) / step);
        if (size <= 0) throw new IllegalArgumentException("Invalid range");
        
        float[] data = new float[size];
        for (int i = 0; i < size; i++) {
            data[i] = start + i * step;
        }
        return new NDArray(data, new int[]{size});
    }

    //reshape()
    public void reshape(int... newShape) {

        int newSize = 1;
        for (int s : newShape) {
            if (s <= 0) {
                throw new IllegalArgumentException("Shape must be positive");
            }
            newSize *= s;
        }

        if (newSize != this.size) {
            throw new IllegalArgumentException("Total size must remain the same");
        }

        this.shape = newShape;
        this.ndim = newShape.length;
    }

    // addition élément par élément, retourne un nouveau NDArray
    public NDArray add(NDArray other) {
        if (!Arrays.equals(this.shape, other.shape)) {
            throw new IllegalArgumentException("Shapes must match for addition");
        }

        float[] resultData = new float[this.size];
        for (int i = 0; i < this.size; i++) {
            resultData[i] = this.data[i] + other.data[i];
        }

        return new NDArray(resultData, this.shape);
    }

    //AddInPlace()
    public void addInPlace(NDArray other) {
        // vérifier compatibilité
        if (!Arrays.equals(this.shape, other.shape)) {
            throw new IllegalArgumentException("Shapes must be identical");
        }

        // addition élément par élément
        for (int i = 0; i < this.size; i++) {
            this.data[i] += other.data[i];
        }
    }

    //AFFICHAGE
    @Override
    public String toString() {
        if (ndim == 1) {
            return Arrays.toString(data);
        } else if (ndim == 2) {
            int rows = shape[0];
            int cols = shape[1];
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for (int i = 0; i < rows; i++) {
                sb.append("  [");
                for (int j = 0; j < cols; j++) {
                    sb.append(get(i, j));
                    if (j < cols - 1) sb.append(", ");
                }
                sb.append("]\n");
            }
            sb.append("]");
            return sb.toString();
        }
        return "Unsupported dimension";
    }

    //debug/verification : mvn compile exec:java -Dexec.mainClass="com.example.NDArray"
    public static void main(String[] args) {
    NDArray z1 = NDArray.zeros(5);
    NDArray z2 = NDArray.zeros(2, 3);

    System.out.println("1D zeros:");
    System.out.println(z1);

    System.out.println("2D zeros:");
    System.out.println(z2);
}
}
