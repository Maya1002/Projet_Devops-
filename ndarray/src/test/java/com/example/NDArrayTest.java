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

    //array()
    @Test
    void testArray1D() {
        float[] input = {1.0f, 2.0f, 3.0f};

        NDArray array = NDArray.array(input);

        assertEquals(1, array.getNdim());
        assertArrayEquals(new int[]{3}, array.getShape());
        assertEquals(3, array.getSize());

        assertEquals(1.0f, array.get(0));
        assertEquals(2.0f, array.get(1));
        assertEquals(3.0f, array.get(2));
    }

    @Test
    void testArray2D() {
        float[][] input = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };

        NDArray array = NDArray.array(input);

        assertEquals(2, array.getNdim());
        assertArrayEquals(new int[]{2, 2}, array.getShape());
        assertEquals(4, array.getSize());

        assertEquals(1.0f, array.get(0, 0));
        assertEquals(2.0f, array.get(0, 1));
        assertEquals(3.0f, array.get(1, 0));
        assertEquals(4.0f, array.get(1, 1));
    }

    @Test
    void testArrayInvalidRows() {
        float[][] input = {
            {1.0f, 2.0f},
            {3.0f}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            NDArray.array(input);
        });
    }

    //arange () 
    @Test
    void testArange() {
        NDArray a = NDArray.arange(0, 5, 1);
        assertEquals(5, a.getSize());
        assertEquals(0f, a.get(0));
        assertEquals(4f, a.get(4));
    }

    @Test
    void testArangeStep2() {
        NDArray a = NDArray.arange(0, 10, 2);
        float[] expected = {0f, 2f, 4f, 6f, 8f};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], a.get(i));
        }
    }

    //reshape()
    @Test
    void testReshapeValid() {
        NDArray a = NDArray.array(new float[]{1, 2, 3, 4});
        a.reshape(2, 2);

        assertArrayEquals(new int[]{2, 2}, a.getShape());
        assertEquals(2, a.getNdim());
        assertEquals(4, a.getSize());
    }

    @Test
    void testReshapeInvalid() {
        NDArray a = NDArray.array(new float[]{1, 2, 3, 4});

        assertThrows(IllegalArgumentException.class, () -> {
            a.reshape(3, 2);
        });
    }

    //dimensions négatives
    @Test
    void testReshapeInvalidShape() {
        NDArray a = NDArray.array(new float[]{1, 2, 3, 4});

        assertThrows(IllegalArgumentException.class, () -> {
            a.reshape(2, -2);
        });
    }

    // arange + reshape -> arange 2D
    @Test
    void testArangeReshape2D() {
        NDArray a = NDArray.arange(0, 6, 1);
        a.reshape(2, 3);

        assertEquals(2, a.getNdim());
        assertArrayEquals(new int[]{2, 3}, a.getShape());
        
        assertEquals(0f, a.get(0, 0));
        assertEquals(1f, a.get(0, 1));
        assertEquals(2f, a.get(0, 2));
        assertEquals(3f, a.get(1, 0));
        assertEquals(4f, a.get(1, 1));
        assertEquals(5f, a.get(1, 2));
    }

    @Test
    void testArangeReshape2DStep2() {
        NDArray a = NDArray.arange(0, 12, 2);
        a.reshape(2, 3);

        assertEquals(0f, a.get(0, 0));
        assertEquals(2f, a.get(0, 1));
        assertEquals(4f, a.get(0, 2));
        assertEquals(6f, a.get(1, 0));
        assertEquals(8f, a.get(1, 1));
        assertEquals(10f, a.get(1, 2));
    }


    //AddInPlace()
    @Test
    void testAddInPlace1D() {
        NDArray a = NDArray.array(new float[]{1, 2, 3});
        NDArray b = NDArray.array(new float[]{4, 5, 6});

        a.addInPlace(b);

        assertEquals(5f, a.get(0));
        assertEquals(7f, a.get(1));
        assertEquals(9f, a.get(2));
    }

    @Test
    void testAddInPlace2D() {
        NDArray a = NDArray.array(new float[][]{
            {1, 2},
            {3, 4}
        });

        NDArray b = NDArray.array(new float[][]{
            {10, 20},
            {30, 40}
        });

        a.addInPlace(b);

        assertEquals(11f, a.get(0, 0));
        assertEquals(22f, a.get(0, 1));
        assertEquals(33f, a.get(1, 0));
        assertEquals(44f, a.get(1, 1));
    }

    @Test
    void testAddInPlaceShapeMismatch() {
        NDArray a = NDArray.array(new float[]{1, 2, 3});
        NDArray b = NDArray.array(new float[]{1, 2});

        assertThrows(IllegalArgumentException.class, () -> {
            a.addInPlace(b);
        });
    }

}