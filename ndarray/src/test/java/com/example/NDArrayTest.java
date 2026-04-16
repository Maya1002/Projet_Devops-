package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de la classe NDArray.
 *
 * Objectif :
 * Vérifier le bon fonctionnement des fonctionnalités principales :
 * - création (array, zeros, arange)
 * - accès et modification des données
 * - reshape
 * - opérations mathématiques (add, addInPlace)
 * - gestion des erreurs
 */
public class NDArrayTest {

    // CONSTRUCTEUR / VALIDATION

    @Test
    void testInvalidShape() {
        // Vérifie que le constructeur détecte une incohérence entre data et shape
        float[] data = {1, 2, 3};
        int[] shape = {2};

        assertThrows(IllegalArgumentException.class, () -> {
            new NDArray(data, shape);
        });
    }

    // ACCÈS AUX DONNÉES 2D

    @Test
    void testAccess2D() {
        // Vérifie l'accès correct aux éléments d'un tableau 2D
        float[] data = {1, 2, 3, 4};
        int[] shape = {2, 2};

        NDArray arr = new NDArray(data, shape);

        assertEquals(1f, arr.get(0, 0));
        assertEquals(2f, arr.get(0, 1));
        assertEquals(3f, arr.get(1, 0));
        assertEquals(4f, arr.get(1, 1));
    }

    @Test
    void testSet2D() {
        // Vérifie la modification correcte d’un élément 2D
        NDArray arr = new NDArray(new float[]{1, 2, 3, 4}, new int[]{2, 2});

        arr.set(0, 1, 10f);

        assertEquals(10f, arr.get(0, 1));
    }

    @Test
    void testWrongDimensionAccess() {
        // Vérifie qu’un accès 2D sur un tableau 1D déclenche une exception
        NDArray arr = new NDArray(new float[]{1, 2, 3}, new int[]{3});

        assertThrows(IllegalStateException.class, () -> {
            arr.get(0, 0);
        });
    }

    // ZEROS()

    @Test
    void testZeros1D() {
        // Vérifie la création d’un tableau 1D rempli de zéros
        NDArray z = NDArray.zeros(4);

        assertEquals(1, z.getNdim());
        assertEquals(4, z.getSize());

        for (int i = 0; i < 4; i++) {
            assertEquals(0f, z.get(i));
        }
    }

    @Test
    void testZeros2D() {
        // Vérifie la création d’un tableau 2D rempli de zéros
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
        // Vérifie que les dimensions invalides sont rejetées
        assertThrows(IllegalArgumentException.class, () -> NDArray.zeros(2, -3));
    }

    @Test
    void testZerosIndependence() {
        // Vérifie que les valeurs peuvent être modifiées indépendamment
        NDArray z = NDArray.zeros(3);

        z.set(1, 5f);

        assertEquals(5f, z.get(1));
        assertEquals(0f, z.get(0));
        assertEquals(0f, z.get(2));
    }

    @Test
    void testZerosRawData() {
        // Vérifie que le tableau interne est bien initialisé à zéro
        NDArray z = NDArray.zeros(3);

        for (float v : z.getData()) {
            assertEquals(0f, v);
        }
    }

    // ARRAY()

    @Test
    void testArray1D() {
        // Vérifie la création d’un NDArray 1D depuis un tableau
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
        // Vérifie la création d’un NDArray 2D depuis un tableau 2D
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
        // Vérifie que les lignes de tailles différentes sont rejetées
        float[][] input = {
            {1.0f, 2.0f},
            {3.0f}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            NDArray.array(input);
        });
    }

    // ARANGE()

    @Test
    void testArange() {
        // Vérifie la génération d’une suite simple
        NDArray a = NDArray.arange(0, 5, 1);

        assertEquals(5, a.getSize());
        assertEquals(0f, a.get(0));
        assertEquals(4f, a.get(4));
    }

    @Test
    void testArangeStep2() {
        // Vérifie la génération avec un pas de 2
        NDArray a = NDArray.arange(0, 10, 2);

        float[] expected = {0f, 2f, 4f, 6f, 8f};

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], a.get(i));
        }
    }

    // RESHAPE()

    @Test
    void testReshapeValid() {
        // Vérifie le reshape valide
        NDArray a = NDArray.array(new float[]{1, 2, 3, 4});

        a.reshape(2, 2);

        assertArrayEquals(new int[]{2, 2}, a.getShape());
        assertEquals(2, a.getNdim());
        assertEquals(4, a.getSize());
    }

    @Test
    void testReshapeInvalid() {
        // Vérifie rejet si taille incompatible
        NDArray a = NDArray.array(new float[]{1, 2, 3, 4});

        assertThrows(IllegalArgumentException.class, () -> {
            a.reshape(3, 2);
        });
    }

    @Test
    void testReshapeInvalidShape() {
        // Vérifie rejet de dimensions négatives
        NDArray a = NDArray.array(new float[]{1, 2, 3, 4});

        assertThrows(IllegalArgumentException.class, () -> {
            a.reshape(2, -2);
        });
    }

    @Test
    void testArangeReshape2D() {
        // Vérifie combinaison arange + reshape
        NDArray a = NDArray.arange(0, 6, 1);

        a.reshape(2, 3);

        assertEquals(2, a.getNdim());
        assertArrayEquals(new int[]{2, 3}, a.getShape());

        assertEquals(0f, a.get(0, 0));
        assertEquals(5f, a.get(1, 2));
    }

    // ADDITION

    @Test
    void testAdd1D() {
        // Vérifie addition classique 1D (retour nouveau tableau)
        NDArray a = NDArray.arange(0, 5, 1);
        NDArray b = NDArray.arange(5, 10, 1);

        NDArray c = a.add(b);

        float[] expected = {5f, 7f, 9f, 11f, 13f};

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], c.get(i));
        }
    }

    @Test
    void testAdd2D() {
        // Vérifie addition 2D
        NDArray a = NDArray.array(new float[][]{{1, 2}, {3, 4}});
        NDArray b = NDArray.array(new float[][]{{10, 20}, {30, 40}});

        NDArray c = a.add(b);

        assertEquals(11f, c.get(0, 0));
        assertEquals(44f, c.get(1, 1));
    }

    // ADD IN PLACE

    @Test
    void testAddInPlace1D() {
        // Vérifie addition en place 1D
        NDArray a = NDArray.array(new float[]{1, 2, 3});
        NDArray b = NDArray.array(new float[]{4, 5, 6});

        a.addInPlace(b);

        assertEquals(5f, a.get(0));
        assertEquals(7f, a.get(1));
        assertEquals(9f, a.get(2));
    }

    @Test
    void testAddInPlaceWithArange() {
        // Vérifie addInPlace avec arange
        NDArray a = NDArray.arange(0, 5, 1);
        NDArray b = NDArray.arange(5, 10, 1);

        a.addInPlace(b);

        float[] expected = {5f, 7f, 9f, 11f, 13f};

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], a.get(i));
        }
    }

    @Test
    void testAddInPlace2DMatrix() {
        // Vérifie addition en place sur matrice 2D
        NDArray a = NDArray.array(new float[][]{{1, 2}, {3, 4}});
        NDArray b = NDArray.array(new float[][]{{10, 20}, {30, 40}});

        a.addInPlace(b);

        assertEquals(11f, a.get(0, 0));
        assertEquals(44f, a.get(1, 1));
    }

    @Test
    void testAddInPlaceShapeMismatch() {
        // Vérifie rejet si shapes incompatibles
        NDArray a = NDArray.array(new float[]{1, 2, 3});
        NDArray b = NDArray.array(new float[]{1, 2});

        assertThrows(IllegalArgumentException.class, () -> {
            a.addInPlace(b);
        });
    }

    // Fonctions universelles: élément par élément

    @Test
    void testSin() {
        // Vérifie le calcul du sinus sur un tableau 1D
        NDArray a = NDArray.array(new float[]{0f, (float)Math.PI/2});

        NDArray r = a.sin();

        assertEquals(0f, r.get(0), 1e-5);
        assertEquals(1f, r.get(1), 1e-5);
    }

    @Test
    void testCos() {
        // Vérifie le calcul du cosinus sur un tableau
        NDArray a = NDArray.array(new float[]{0f});

        NDArray r = a.cos();

        assertEquals(1f, r.get(0), 1e-5);
    }

    @Test
    void testExp() {
        // Vérifie le calcul de l'exponentielle
        NDArray a = NDArray.array(new float[]{0f});

        NDArray r = a.exp();

        assertEquals(1f, r.get(0), 1e-5);
    }

    @Test
    void testSqrt() {
        // Vérifie le calcul de la racine carrée
        NDArray a = NDArray.array(new float[]{4f});

        NDArray r = a.sqrt();

        assertEquals(2f, r.get(0), 1e-5);
    }

    // Fonctions de statistiques

    @Test
    void testSum() {
        // Vérifie le calcul de la somme des éléments
        NDArray a = NDArray.array(new float[]{1f, 2f, 3f});
        assertEquals(6f, a.sum());
    }

    @Test
    void testMean() {
        // Vérifie le calcul de la moyenne
        NDArray a = NDArray.array(new float[]{2f, 4f});
        assertEquals(3f, a.mean());
    }

    @Test
    void testMinMax() {
        // Vérifie le calcul du minimum et du maximum
        NDArray a = NDArray.array(new float[]{-1f, 5f, 3f});

        assertEquals(-1f, a.min());
        assertEquals(5f, a.max());
    }

    // Tests avancés: UFuncs + Statistics

    @Test
    void testMeanAfterExp() {
        // Vérifie que mean() après une transformation exp() fonctionne correctement
        NDArray a = NDArray.array(new float[]{0f, 1f, 2f});

        NDArray b = a.exp();

        float mean = b.mean();

        assertTrue(mean > 1f); // exp augmente les valeurs donc moyenne > 1
    }

    @Test
    void testSinRangeWithStats() {
        // Vérifie cohérence entre sin() et stats (valeurs bornées)
        NDArray a = NDArray.array(new float[]{0f, (float)Math.PI/2, (float)Math.PI});

        NDArray r = a.sin();

        assertTrue(r.min() >= -1f);
        assertTrue(r.max() <= 1f);
    }

    @Test
    void testSqrtMean() {
        // Vérifie que sqrt() conserve une moyenne positive
        NDArray a = NDArray.array(new float[]{1f, 4f, 9f});

        NDArray r = a.sqrt();

        assertEquals(2f, r.mean(), 1e-5);
    }

    @Test
    void testCosSizeAndStats() {
        // Vérifie que cos() n'altère pas la taille et permet stats cohérentes
        NDArray a = NDArray.array(new float[]{0f, 1f, 2f, 3f});

        NDArray r = a.cos();

        assertEquals(a.getSize(), r.getSize());

        float max = r.max();
        float min = r.min();

        assertTrue(max <= 1f && min >= -1f);
    }

    @Test
    void testExpMinMax() {
        // Vérifie combinaison exp() + min/max (valeurs strictement positives)
        NDArray a = NDArray.array(new float[]{-1f, 0f, 1f});

        NDArray r = a.exp();

        assertTrue(r.min() > 0f);
        assertTrue(r.max() > r.min());
    }

    
}