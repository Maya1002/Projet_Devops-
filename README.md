![Java CI](https://github.com/Maya1002/Projet_Devops-/actions/workflows/ci.yml/badge.svg)

# NDArray - Bibliothèque de calcul scientifique en Java

Projet réalisé dans le cadre du cours DevOps M1 INFO - UFR IM2AG, Université Grenoble Alpes (2026).

Le but de ce projet est de développer une bibliothèque Java inspirée de NumPy (Python) pour manipuler des tableaux multidimensionnels, tout en mettant en pratique les principes d'intégration continue et de travail collaboratif.

---

## Fonctionnalités

Notre bibliothèque repose sur la classe NDArray. Elle permet de travailler avec des tableaux 1D (vecteurs) et 2D (matrices) contenant des données de type float.

Chaque NDArray possède les attributs suivants :
- ndim : le nombre de dimensions (1 ou 2)
- shape : la forme du tableau, par exemple [2, 3] pour une matrice 2 lignes × 3 colonnes
- size : le nombre total d'éléments

---

## Création de NDArray

NDArray.array(float[])
Crée un tableau 1D à partir d'un tableau Java classique :

NDArray a = NDArray.array(new float[]{1, 2, 3});

---

NDArray.array(float[][])
Crée un tableau 2D :

NDArray m = NDArray.array(new float[][]{{1, 2}, {3, 4}});

---

NDArray.zeros(int...)
Crée un tableau rempli de zéros :

NDArray z = NDArray.zeros(2, 3);

---

NDArray.arange(start, stop, step)
Crée une séquence de nombres (équivalent de range en Python) :

NDArray a = NDArray.arange(0, 10, 2);

---

## Opérations

add(NDArray other)
Addition élément par élément (retourne un nouveau NDArray) :

NDArray c = a.add(b);

---

addInPlace(NDArray other)
Addition en place (modifie l’objet courant) :

a.addInPlace(b);

---

## Fonctions universelles (ufuncs)

Les fonctions universelles s’appliquent élément par élément et retournent un nouveau NDArray sans modifier l’objet original.

sin()
NDArray r = a.sin();

cos()
NDArray r = a.cos();

exp()
NDArray r = a.exp();

sqrt()
NDArray r = a.sqrt();

---

## Reshape

reshape(int... newShape)
Change la forme du tableau sans modifier les données :

NDArray a = NDArray.arange(0, 6, 1);
a.reshape(2, 3);

---

## Affichage

La méthode toString() permet un affichage lisible :

[
  [1.0, 2.0, 3.0]
  [4.0, 5.0, 6.0]
]

---

## Outils utilisés

- Java 17
- Maven
- JUnit 5
- JaCoCo
- Git + GitHub
- GitHub Actions

---

## Workflow Git

On utilise un workflow basé sur les feature branches.

1. Création d’une branche feature
2. Développement + tests
3. Pull Request
4. Review par le binôme
5. Merge dans main

---

## Intégration continue

Pipeline GitHub Actions :

- build automatique
- exécution des tests
- rapport JaCoCo

Déclenché à chaque push et Pull Request.

---

## Structure du projet

Projet_Devops-/
├── .github/workflows/
│   └── ci.yml
├── ndarray/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/example/
│       │   └── NDArray.java
│       └── test/java/com/example/
│           └── NDArrayTest.java
├── AUTHORS
└── README.md

---

## Feedback

Git et GitHub
On a appris à utiliser les branches et les Pull Requests correctement.

Maven
Difficile au début mais pratique ensuite.

JUnit 5
Très utile pour détecter les cas limites.

JaCoCo
Permet de vérifier la couverture de tests.

GitHub Actions
Permet de détecter automatiquement les erreurs.

---

## Revue de code

Au début, les revues étaient faites en présentiel.

Ensuite, nous avons utilisé les Pull Requests GitHub avec validation formelle avant merge(Approve).