# ChronoTours, l'agence de voyage dans le temps

> **Langage :** Java (sans framework)
>
> **Durée :** 1h
>
> **Objectif :** modéliser un petit domaine en objets, le coder et le faire valider par un test d'acceptance.

## 1. Le contexte

**ChronoTours** est la première agence de voyage dans le temps. Elle est installée en **2026** et envoie ses clients visiter le passé.

Le problème : le voyage dans le temps est dangereux. Un client qui croise son propre passé risque de **détruire l'univers**. L'agence veut donc un logiciel qui **refuse tout saut dangereux** avant qu'il ait lieu.

## 2. Le métier, expliqué par la directrice de l'agence

### 2.1 Les voyageurs

Chaque voyageur a un **nom**. Il part de l'agence, en **2026**, avec une réserve d'énergie temporelle, mesurée en **chronitons**.

À chaque saut, il dépense des chronitons. Sa réserve ne peut jamais devenir négative : s'il n'a pas assez de chronitons, le saut est refusé.

> « Je veux pouvoir lire le **carnet de voyage** d'un client : toutes les années où il est passé, dans l'ordre, et ce qu'il lui reste comme chronitons. »

### 2.2 Les machines

L'agence possède trois machines. Elles ne calculent pas le coût d'un saut de la même façon :

| Machine | Coût d'un saut | Portée maximale |
| --- | --- | --- |
| **Cabine** | 1 chroniton par année parcourue | 100 ans |
| **DeLorean** | 30 chronitons, quelle que soit la distance | Illimitée |
| **Portail** | 2 chronitons par année parcourue | Illimitée |

Le nombre d'années parcourues est l'écart entre l'année de départ et l'année d'arrivée, dans un sens comme dans l'autre. Par exemple, de 2026 à 1955, on parcourt 71 ans.

> « Avant de partir, un client veut un **devis** : combien lui coûterait ce saut avec chaque machine ? Et le jour où on achètera une nouvelle machine, je ne veux pas tout réécrire. »

### 2.3 Les sauts interdits

Un saut est refusé, avec son **motif**, dans chacun de ces cas :

1. **Avant l'invention :** on ne peut pas aller avant **1885**, l'année où le voyage dans le temps a été inventé.
2. **Paradoxe :** on ne peut pas aller dans une année où l'on **est déjà passé**, sinon on se croiserait soi-même. Une seule exception : on peut toujours **rentrer à l'agence**, en 2026.
3. **Hors de portée :** la machine ne peut pas parcourir autant d'années.
4. **Réserve insuffisante :** le voyageur n'a pas assez de chronitons pour ce saut.

Un saut refusé ne change rien : le voyageur reste où il est, avec la même réserve.

## 3. Ce qui vous est demandé

Aucune notion de POO n'est imposée. Modélisez le domaine comme il vous semble juste, puis soyez prêts à expliquer vos choix.

1. **Le vocabulaire (10 min) :** relevez les noms et les verbes de la section 2. Lesquels deviennent des classes, des attributs, des méthodes ?
2. **Un diagramme de classes (15 min) :** sur papier ou en Mermaid, avec les attributs, les méthodes principales et les relations.
3. **Le code (30 min) :** écrivez le modèle, puis le programme `Main` qui rejoue le scénario ci-dessous.
4. **Le test au vert (5 min) :** lancez `mvn test` depuis le dossier `java`.

### 3.1 Scénario (à rejouer dans `Main`)

1. Deux voyageurs arrivent à l'agence en 2026 : **Marty** avec 100 chronitons et **Doc** avec 200 chronitons.
2. Marty demande un devis pour aller de 2026 à 1955 → Cabine **71**, DeLorean **30**, Portail **142**.
3. Les sauts de Marty :
   - en 1955 avec la DeLorean → accepté, il lui reste **70** chronitons ;
   - en 1955 avec la Cabine → **refus** (il est déjà passé en 1955) ;
   - en 1850 avec la DeLorean → **refus** (avant 1885) ;
   - en 2026 avec la Cabine → **refus** (71 chronitons nécessaires, il n'en a que 70) ;
   - en 2026 avec la DeLorean → accepté, il rentre à l'agence avec **40** chronitons.
4. Les sauts de Doc :
   - en 1900 avec la Cabine → **refus** (126 ans, au-delà de la portée de 100 ans) ;
   - en 1985 avec la Cabine → accepté, il reste **159** chronitons ;
   - en 1955 avec le Portail → accepté, il reste **99** chronitons ;
   - en 1985 avec la DeLorean → **refus** (déjà passé en 1985).
5. Afficher le carnet de voyage de chacun :
   - Marty : 2026 → 1955 → 2026, 40 chronitons ;
   - Doc : 2026 → 1985 → 1955, 99 chronitons.

### 3.2 Le test d'acceptance

`java/src/test/java/chrono/AcceptanceTest.java` exécute votre `Main` et compare tout ce qu'il affiche au fichier `AcceptanceTest.scenario_de_demonstration.approved.txt`. Ce fichier est la **sortie attendue**, au caractère près : lisez-le avant de commencer et ne le modifiez pas.

### 3.3 Contraintes techniques

- Java 25 ou plus, sans framework, tout en mémoire.
- Un saut refusé lève une **exception** avec son motif. Le modèle n'affiche rien lui-même : c'est `Main` qui affiche.

## 4. Pour ceux qui finissent en avance

1. L'agence achète un **Hyperloop temporel** : 50 chronitons fixes, mais seulement vers les années bissextiles. Combien de classes existantes avez-vous modifiées ?
2. Ajoutez une **recharge** de chronitons, possible uniquement à l'agence (en 2026).
