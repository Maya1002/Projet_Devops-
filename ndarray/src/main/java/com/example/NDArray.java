package com.example;

import java.util.Arrays;

/**
 * NDArray - structure de données simple inspirée de NumPy.
 * Supporte les tableaux 1D et 2D de type float.
 */
public class NDArray {

    // Données stockées en mémoire sous forme aplatie (row-major)
    private float[] data;

    // Forme du tableau (ex: [2,3])
    private int[] shape;

    // Nombre de dimensions (1D, 2D)
    private int ndim;

    // Nombre total d'éléments
    private int size;

    /**
     * Constructeur principal
     * @param data valeurs du tableau (aplaties)
     * @param shape dimensions du tableau
     */
    public NDArray(float[] data, int[] shape) {
        this.data = data;
        this.shape = shape;
        this.ndim = shape.length;

        // Calcul du nombre total d'éléments
        this.size = 1;
        for (int s : shape) {
            this.size *= s;
        }

        // Vérification cohérence shape / data
        if (this.size != data.length) {
            throw new IllegalArgumentException("Data size does not match shape");
        }
    }

    
    // GETTERS

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

    // ACCÈS AUX DONNÉES (1D)

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

    // ACCÈS AUX DONNÉES (2D)

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

    // CRÉATION

    /**
     * Crée un NDArray rempli de zéros
     */
    public static NDArray zeros(int... shape) {
        int size = 1;

        for (int s : shape) {
            if (s <= 0) {
                throw new IllegalArgumentException("Shape dimensions must be positive");
            }
            size *= s;
        }

        float[] data = new float[size]; // initialisé automatiquement à 0
        return new NDArray(data, shape);
    }

    /**
     * Crée un NDArray 1D à partir d'un tableau
     */
    public static NDArray array(float[] input) {
        return new NDArray(input, new int[]{input.length});
    }

    /**
     * Crée un NDArray 2D à partir d'un tableau 2D
     */
    public static NDArray array(float[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        // Vérifie que toutes les lignes ont la même taille
        for (int i = 0; i < rows; i++) {
            if (input[i].length != cols) {
                throw new IllegalArgumentException("Inconsistent row sizes");
            }
        }

        // Conversion en tableau 1D
        float[] data = new float[rows * cols];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[index++] = input[i][j];
            }
        }

        return new NDArray(data, new int[]{rows, cols});
    }

    /**
     * Génère une suite de valeurs [start, stop[
     */
    public static NDArray arange(float start, float stop, float step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step cannot be 0");
        }

        int size = (int) ((stop - start) / step);
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid range");
        }

        float[] data = new float[size];

        for (int i = 0; i < size; i++) {
            data[i] = start + i * step;
        }

        return new NDArray(data, new int[]{size});
    }

    // RESHAPE

    /**
     * Change la forme du tableau sans modifier les données
     */
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

    // OPERATIONS

    /**
     * Addition élément par élément (retourne un nouveau NDArray)
     */
    public NDArray add(NDArray other) {
        if (!Arrays.equals(this.shape, other.shape)) {
            throw new IllegalArgumentException("Shapes must match for addition");
        }

        float[] result = new float[this.size];

        for (int i = 0; i < this.size; i++) {
            result[i] = this.data[i] + other.data[i];
        }

        return new NDArray(result, this.shape);
    }

    /**
     * Addition élément par élément (modifie l'objet courant)
     */
    public void addInPlace(NDArray other) {
        if (!Arrays.equals(this.shape, other.shape)) {
            throw new IllegalArgumentException("Shapes must be identical");
        }

        for (int i = 0; i < this.size; i++) {
            this.data[i] += other.data[i];
        }
    }

    // AFFICHAGE

    @Override
    public String toString() {
        if (ndim == 1) {
            return Arrays.toString(data);
        }

        if (ndim == 2) {
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