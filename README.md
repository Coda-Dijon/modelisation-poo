# Ateliers de modélisation objet (POO)

Deux exercices en Java, à faire dans l'ordre, en individuel.

| # | Exercice | Durée | Ce qu'on travaille |
| --- | --- | --- | --- |
| 1 | [ChronoTours, l'agence de voyage dans le temps](01-chronotours/README.md) | 1h | Modéliser librement un petit domaine, sans contrainte imposée |
| 2 | [CinéCoda, l'exploitation de films en salle](02-cinecoda/README.md) | 4h | Les 4 piliers de la POO, interface, composition, énumération d'état, diagrammes UML |

## Organisation

Chaque exercice a la même structure :

```
0X-exercice/
  README.md              # énoncé
  java/                  # projet Maven de départ
    src/main/java/...    # Main à compléter + ConsoleLogger (fourni, à ne pas modifier)
    src/test/java/...    # test d'acceptance + sortie attendue (*.approved.txt)
```

Au départ, le test d'acceptance est **rouge** : `Main` n'affiche encore rien. À vous de le faire passer au vert.

## La démarche

1. Identifier le vocabulaire du métier (noms et verbes).
2. Dessiner un diagramme de classes.
3. Écrire `Main` d'après le scénario de l'énoncé, puis générer les classes manquantes depuis l'IDE.
4. Faire passer le test d'acceptance au vert :

```bash
cd 0X-exercice/java
mvn test
```

Le test exécute `Main` et compare tout ce qu'il affiche au fichier `*.approved.txt`, qui contient la sortie attendue au caractère près. Pour afficher, utilisez le logger déjà créé dans `Main` : `log.info("...")`.
