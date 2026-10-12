# CinéCoda, barème de correction

> **Total :** 20 points
>
> **Évaluation :** en binôme, sur les livrables (vocabulaire métier, diagramme, code, historique git) et un court échange oral de justification.

## Règles du jeu

> **L'usage de l'IA n'est pas autorisé** (ChatGPT, Copilot, Claude, assistants intégrés à l'IDE, etc.). L'objectif est de construire **votre** raisonnement de modélisation, pas d'obtenir un modèle tout fait. Un usage avéré entraîne la note de **0/20**.

> **Micro-commits obligatoires.** Commitez à chaque petit pas, avec un message qui dit ce que vous avez fait et pourquoi. L'historique git doit permettre de **suivre l'évolution de votre réflexion**, y compris vos hésitations et vos changements d'avis. Un seul gros commit en fin de séance ne permet pas d'évaluer votre démarche.

## Vue d'ensemble

| # | Critère | Points |
| --- | --- | --- |
| 1 | Vocabulaire métier (`GLOSSAIRE.md`) | 2 |
| 2 | Diagramme de classes | 1 |
| 3 | Conception objet (attendus de la section 3.1) | 6 |
| 4 | Règles métier et comportements B1 à B11 | 5 |
| 5 | Démonstration : `Main` et test d'acceptance | 2 |
| 6 | Qualité du code et contraintes techniques | 1 |
| 7 | Historique git en micro-commits | 2 |
| 8 | Justification orale des choix | 1 |
| | **Total** | **20** |
| | Malus | voir plus bas |

## 1. Vocabulaire métier (2 pts)

| Élément évalué | Points |
| --- | --- |
| Les entités du métier sont identifiées et définies dans vos mots | 1 |
| Les comportements et les règles sont identifiés, définis et rattachés à une entité | 1 |

## 2. Diagramme de classes (1 pt)

| Élément évalué | Points |
| --- | --- |
| Schéma lisible et cohérent avec le code livré (la notation UML stricte n'est pas exigée) | 1 |

## 3. Conception objet (6 pts)

| Élément évalué | Points |
| --- | --- |
| Encapsulation | 1 |
| Classe abstraite pertinente | 1 |
| Interface pertinente | 1 |
| Composition pertinente | 1 |
| Énumération pour représenter un état | 1 |
| Traitement polymorphe, sans test de type | 1 |

## 4. Règles métier et comportements (5 pts)

Chaque règle violée lève une **exception** avec un motif clair, et un refus ne modifie rien.

| Élément évalué | Points |
| --- | --- |
| Cycle de vie de la salle | 1 |
| Démarrage d'une séance | 1 |
| Billetterie et tarifs | 1 |
| Programmation des séances par le cinéma | 1 |
| Comportements B1 à B11 portés par l'objet qui détient l'information, interrogations sans effet de bord | 1 |

## 5. Démonstration (2 pts)

| Élément évalué | Points |
| --- | --- |
| `Main` rejoue les 9 étapes du scénario en s'appuyant sur le modèle | 1 |
| `mvn test` est vert, sans modification du fichier `.approved.txt` ni de `ConsoleLogger` | 1 |

## 6. Qualité du code et contraintes techniques (1 pt)

| Élément évalué | Points |
| --- | --- |
| Contraintes de la section 3.4 respectées, nommage issu du vocabulaire métier | 1 |

## 7. Historique git en micro-commits (2 pts)

| Élément évalué | Points |
| --- | --- |
| Commits nombreux et petits, répartis sur toute la séance | 1 |
| Messages explicites qui permettent de suivre la démarche | 1 |

## 8. Justification orale (1 pt)

| Élément évalué | Points |
| --- | --- |
| Le binôme sait expliquer et défendre ses choix de conception | 1 |

## Malus

| Situation | Points |
| --- | --- |
| Usage de l'IA avéré | note de 0/20 |
| Un seul commit (ou presque) pour tout le projet | −3 |
| Projet qui ne compile pas | −4 |
| Fichier `.approved.txt` ou `ConsoleLogger` modifié pour faire passer le test | −3 |
| Sortie attendue « en dur » dans `Main` sans passer par le modèle | −3 |
| Utilisation d'un framework ou d'une bibliothèque externe | −1 |
