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
}