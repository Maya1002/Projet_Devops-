package com.example;

/**
 * Classe de démonstration du projet NDArray.
 *
 * Cette classe illustre les principales fonctionnalités
 * de la bibliothèque : création, manipulation et opérations.
 */
public class App {

    public static void main(String[] args) {

        System.out.println("=== NDArray DEMO ===\n");

        // Création de tableaux

        NDArray simple = NDArray.array(new float[]{1, 2, 3, 4, 5});
        System.out.println("Array simple (1D) :");
        System.out.println(simple);

        NDArray a = NDArray.arange(0, 6, 1);
        System.out.println("\nArray a (arange 0 à 6) :");
        System.out.println(a);

        NDArray b = NDArray.zeros(2, 3);
        System.out.println("\nArray b (zeros 2x3) :");
        System.out.println(b);

        // Reshape

        a.reshape(2, 3);
        System.out.println("\nArray a après reshape (2x3) :");
        System.out.println(a);

        // Addition (retourne un nouveau tableau)

        NDArray c = NDArray.array(new float[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        NDArray d = NDArray.array(new float[][]{
            {10, 20, 30},
            {40, 50, 60}
        });

        NDArray result = c.add(d);
        System.out.println("\nAddition c + d :");
        System.out.println(result);

        // AddInPlace (modifie directement le tableau)

        System.out.println("\nc avant addInPlace :");
        System.out.println(c);

        c.addInPlace(d);
        System.out.println("\nc après addInPlace(d) :");
        System.out.println(c);

        System.out.println("\n=== FIN DEMO ===");
    }
}