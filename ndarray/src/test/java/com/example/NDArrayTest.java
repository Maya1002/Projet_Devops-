package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NDArrayTest {

    //1D
    @Test
    void testCreation1D() {
        float[] data = {1, 2, 3};
        int[] shape = {3};

        NDArray arr = new NDArray(data, shape);

        assertEquals(1, arr.getNdim());
        assertEquals(3, arr.getSize());
        assertEquals(3, arr.getShape()[0]);
        assertEquals(2, arr.get(1));
    }

    @Test
    void testInvalidShape() {
        float[] data = {1, 2, 3};
        int[] shape = {2};

        assertThrows(IllegalArgumentException.class, () -> {
            new NDArray(data, shape);
        });
    }

    //2D
    @Test
    void testCreation2D() {
        float[] data = {1, 2, 3, 4};
        int[] shape = {2, 2};

        NDArray arr = new NDArray(data, shape);

        assertEquals(2, arr.getNdim());
        assertEquals(4, arr.getSize());
        assertEquals(2, arr.getShape()[0]);
        assertEquals(2, arr.getShape()[1]);
    }

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

    @Test
    void testSet2D() {
        float[] data = {1, 2, 3, 4};
        int[] shape = {2, 2};

        NDArray arr = new NDArray(data, shape);

        arr.set(0, 1, 10);

        assertEquals(10, arr.get(0, 1));
    }

    @Test
    void testWrongDimensionAccess() {
        float[] data = {1, 2, 3};
        int[] shape = {3};

        NDArray arr = new NDArray(data, shape);

        assertThrows(IllegalStateException.class, () -> {
            arr.get(0, 0);
        });
    }

    //zeros()
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

    @Test
    void testZerosInvalidShape() {
        assertThrows(IllegalArgumentException.class, () -> NDArray.zeros(2, -3));
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

}