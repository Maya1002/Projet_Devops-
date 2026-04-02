package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NDArrayTest {
    // pertinence : verifier que le constructeur rejette une shape incohérente avec la taille des données
    @Test
    void testInvalidShape() {
        float[] data = {1, 2, 3};
        int[] shape = {2};

        assertThrows(IllegalArgumentException.class, () -> {
            new NDArray(data, shape);
        });
    }

    // pertinence : verifier que les méthodes d’accès 2D fonctionnent correctement
    @Test
    void testAccess2D() {
        float[] data = {1, 2, 3, 4};
        int[] shape = {2, 2};

        NDArray arr = new NDArray(data, shape);

        assertEquals(1, arr.get(0, 0));
        assertEquals(2, arr.get(0, 1));
        assertEquals(3, arr.get(1, 0));
        assertEquals(4, arr.get(1, 1));
    }

    // pertinence : verifier que les méthodes de mise a jour sont correct 
    @Test
    void testSet2D() {
        float[] data = {1, 2, 3, 4};
        int[] shape = {2, 2};
        NDArray arr = new NDArray(data, shape);
        arr.set(0, 1, 10);
        assertEquals(10, arr.get(0, 1));
    }

    // pertinence : verifie qu’un accès avec une mauvaise dimension déclenche une exceptio
    @Test
    void testWrongDimensionAccess() {
        float[] data = {1, 2, 3};
        int[] shape = {3};
        NDArray arr = new NDArray(data, shape);
        assertThrows(IllegalStateException.class, () -> {
            arr.get(0, 0);
        });
    }

    @Test
    void testZeros1D() {
        NDArray z = NDArray.zeros(4);
        assertEquals(1, z.getNdim());
        assertEquals(4, z.getSize());
        for (int i = 0; i < 4; i++) {
            assertEquals(0f, z.get(i));
        }
    }

    @Test
    void testZeros2D() {
        NDArray z = NDArray.zeros(2, 3);
        assertEquals(2, z.getNdim());
        assertEquals(6, z.getSize());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(0f, z.get(i, j));
            }
        }
    }

    //pertinence : verifie que zeros() rejette une shape invalide (dimension négative)
    @Test
    void testZerosInvalidShape() {
        assertThrows(IllegalArgumentException.class, () -> NDArray.zeros(2, -3));
    }

    @Test
    void testZerosIndependence() {
        NDArray z = NDArray.zeros(3);
        z.set(1, 5f);

        assertEquals(5f, z.get(1));
        assertEquals(0f, z.get(0));
        assertEquals(0f, z.get(2));
    }

    // vérifie que le tableau interne (data) est bien initialisé uniquement avec des zéros
    @Test
    void testZerosRawData() {
        NDArray z = NDArray.zeros(3);
        float[] data = z.getData();

        for (float v : data) {
            assertEquals(0f, v);
        }
    }

}