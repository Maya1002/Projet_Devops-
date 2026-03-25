package com.example;

import java.util.Arrays;

public class NDArray {

    private float[] data;   // données a plat
    private int[] shape;    // dim
    private int ndim;       // nb dim
    private int size;       // nb total elem

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
}