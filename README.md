![Java CI](https://github.com/Maya1002/Projet_Devops-/actions/workflows/ci.yml/badge.svg)

# NDArray - Bibliothèque de calcul scientifique en Java

Projet réalisé dans le cadre du cours DevOps M1 INFO - UFR IM2AG, Université Grenoble Alpes (2026).

Le but de ce projet est de développer une bibliothèque Java inspirée de NumPy (Python) pour manipuler des tableaux multidimensionnels, tout en mettant en pratique les principes d'intégration continue et de travail collaboratif.

## Fonctionnalités

Notre bibliothèque repose sur la classe `NDArray`. Elle permet de travailler avec des tableaux 1D (vecteurs) et 2D (matrices) contenant des données de type `float`.

Chaque NDArray possède les attributs suivants :
- `ndim` : le nombre de dimensions (1 ou 2)
- `shape` : la forme du tableau, par exemple `[2, 3]` pour une matrice 2 lignes × 3 colonnes
- `size` : le nombre total d'éléments

### Création de NDArray

- `NDArray.array(float[])` : crée un tableau 1D à partir d'un tableau Java classique:
```java
NDArray a = NDArray.array(new float[]{1, 2, 3});
```

- `NDArray.array(float[][])` : crée un tableau 2D: 
```java
NDArray m = NDArray.array(new float[][]{{1, 2}, {3, 4}});
```

- `NDArray.zeros(int...)` : crée un tableau rempli de zéros avec la forme donnée: 
```java
NDArray z = NDArray.zeros(2, 3); // matrice 2x3 de zéros
```

- `NDArray.arange(start, stop, step)` : crée un tableau 1D avec une séquence de nombres (comme `range` en Python) :
```java
NDArray a = NDArray.arange(0, 10, 2); // [0, 2, 4, 6, 8]
```

### Opérations
- `add(NDArray other)` : addition élément par élément, retourne un nouveau NDArray sans modifier les originaux.
```java
NDArray c = a.add(b);
```

- `addInPlace(NDArray other)` : addition en place (équivalent du `+=` de NumPy), modifie directement le tableau.
```java
a.addInPlace(b); // a est modifié
```

### Reshape
- `reshape(int... newShape)` : change la forme du tableau. La taille totale doit rester la même.
```java
NDArray a = NDArray.arange(0, 6, 1);
a.reshape(2, 3); // passe de [6] à [2, 3]
```
## Fonctionnalités Optionnelles

### Fonctions universelles:

Les fonctions universelles s’appliquent élément par élément et retournent un nouveau NDArray sans modifier l’objet original.

- `sin()` : applique la fonction sinus à chaque élément
NDArray r = a.sin();

- `cos()` : applique la fonction cosinus à chaque élément
NDArray r = a.cos();

- `exp()` : applique la fonction exponentielle à chaque élément
NDArray r = a.exp();

- `sqrt()` : applique la racine carrée à chaque élément
NDArray r = a.sqrt();

### Fonctions de statistiques:

Les fonctions de statistiques permettent de calculer des valeurs globales sur un NDArray.

- `sum()` : calcule la somme de tous les éléments  
float s = a.sum();

- `mean()` : calcule la moyenne des éléments  
float m = a.mean();

- `min()` : retourne la valeur minimale du tableau  
float mn = a.min();

- `max()` : retourne la valeur maximale du tableau  
float mx = a.max();

### Affichage

On a implémenté `toString()` pour avoir un affichage lisible. Par exemple pour un tableau 2D :
```
[
  [1.0, 2.0, 3.0]
  [4.0, 5.0, 6.0]
]
```

## Outils utilisés

- Java 17 pour le développement
- Maven pour le build et la gestion des dépendances
- JUnit 5 pour les tests unitaires
- JaCoCo pour mesurer la couverture de code
- Git + GitHub pour le versionnement et la collaboration
- GitHub Actions pour l'intégration continue

## Notre workflow Git

On a adopté un workflow basé sur les **feature branches**.

La branche main contient le code stable. Pour chaque nouvelle fonctionnalité, on crée une branche `feature/nom-de-la-fonctionnalité` (par exemple `feature/ndarray-addInPlace` ou `feature/ndarray-reshape`).

Concrètement, quand on développe une fonctionnalité :

1. On crée une branche `feature/...` à partir de `main`
2. On code la fonctionnalité + les tests qui vont avec
3. On push sur GitHub et on crée une Pull Request
4. L'autre personne du binôme relit le code et approuve la PR
5. On merge dans `main`

Comme on travaille souvent en présentiel côte à côte, on fait aussi pas mal de pair programming. Dans ce cas on revoit le code ensemble avant de push, et la validation sur GitHub sert surtout à garder une trace de l'approbation.

## Intégration continue

On a mis en place un pipeline avec **GitHub Actions** (fichier `.github/workflows/ci.yml`).

Le pipeline se déclenche automatiquement :
- à chaque push sur `main` ou sur une branche `feature/*`
- à chaque Pull Request vers `main`

Il fait les étapes suivantes :
1. Récupère le code (checkout)
2. Installe Java 17
3. Lance `mvn clean verify` qui compile le code, exécute tous les tests et génère le rapport de couverture JaCoCo

Ça nous permet de vérifier que rien n'est cassé à chaque modification.

## Structure du projet
```txt
Projet_Devops-/
├── .github/
│   └── workflows/
│       └── ci.yml
├── ndarray/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   └── java/
│       │       └── com/example/
│       │           └── NDArray.java
│       └── test/
│           └── java/
│               └── com/example/
│                   └── NDArrayTest.java
├── AUTHORS
└── README.md
```

## Feedback

**Git et GitHub** : on connaissait déjà les bases de Git mais ce projet nous a vraiment fait pratiquer les branches et les Pull Requests. Au début on avait tendance à tout push sur main mais on a vite compris l'intérêt des feature branches, surtout quand on travaille à deux.

**Maven** : c'était un peu galère au début pour comprendre le pom.xml et la structure des dossiers, mais une fois en place c'est pratique. L'intégration avec JaCoCo marche bien.

**JUnit 5** : assez simple à prendre en main. Écrire les tests nous a aidé à trouver des bugs qu'on aurait pas vu autrement (notamment sur les cas limites comme les dimensions négatives).

**JaCoCo** : utile pour voir si on a oublié de tester certaines parties du code. Le rapport HTML généré est facile à lire.

**GitHub Actions** : la mise en place était plus simple que ce qu'on pensait. Le fait que les tests tournent automatiquement à chaque push c'est rassurant, on sait tout de suite si on a cassé quelque chose.


### À propos de la revue de code

Au début du projet, on ne connaissait pas le système de review intégré à GitHub (l'approbation via "Review changes" dans les Pull Requests). Comme on travaille en binôme en présentiel, on faisait nos revues de code en direct : on codait ensemble, on vérifiait le code de l'autre en parallèle, on testait en local, et on ne pushait que quand les deux étaient d'accord. On pensait que ça suffisait.

Par la suite, quand on a découvert le mécanisme de revue de code sur GitHub (avec l'option Approve dans les PRs), on l'a adopté et on l'utilise systématiquement. Chaque PR est maintenant approuvée formellement par l'autre membre du binôme avant d'être mergée.