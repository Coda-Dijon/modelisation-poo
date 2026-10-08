# CinéCoda, l'exploitation de films en salle

> **Langage :** Java (sans framework)
> 
> **Objectif :** concevoir un modèle objet à partir d'un besoin métier, puis le coder et le démontrer.

## 1. Le contexte métier

**CinéCoda** est un petit cinéma indépendant de 3 salles. Aujourd'hui, tout est géré sur un tableur. 

Résultat, l'équipe a vécu cette semaine :

- une séance annulée parce que le film était livré en **copie 35 mm** alors que la salle n'a qu'un **projecteur numérique**
- un film numérique impossible à lancer : sa **clé de lecture** avait **expiré** la veille
- 3 billets de trop vendus pour une salle déjà pleine
- une salle encore en plein **ménage** alors que les spectateurs de la séance suivante attendaient devant la porte.

La direction veut un **noyau logiciel** (pas d'interface graphique, pas de base de données) qui **refuse les situations incohérentes** au lieu de les laisser arriver.

> Votre mission : concevoir ce modèle objet, puis le coder et le démontrer dans un programme `Main`.

## 2. Le métier, expliqué par la responsable d'exploitation

### 2.1 Le cinéma et ses salles

Le cinéma a un nom et **plusieurs salles**. Une salle n'existe pas en dehors de son cinéma : ce sont les salles **du** cinéma.

Chaque salle a un **numéro**, une **capacité** (nombre de places) et **un projecteur**.

Au cours de la journée, une salle est soit **libre** (propre, prête à accueillir une séance), soit **en séance** (un film est projeté), soit **en nettoyage** :

- On ne démarre une séance que dans une salle libre.
- À la fin d'une séance, la salle passe **obligatoirement** en nettoyage.
- Elle ne redevient libre que lorsque le nettoyage est déclaré terminé.
- Rien d'autre n'est permis (par exemple, on ne « termine pas le nettoyage » d'une salle déjà libre).

### 2.2 Les projecteurs

Nous avons deux sortes de projecteurs, qui ont tous un **numéro de série** :

- les **projecteurs numériques**, qui lisent les fichiers numériques ;
- les **projecteurs argentiques**, qui lisent la pellicule 35 mm.

Un projecteur « tout court » n'existe pas : c'est toujours l'un ou l'autre.

### 2.3 Les films et leurs supports

Un **film** a un titre et une durée (en minutes).

Le distributeur ne nous envoie pas « un film », il nous envoie une **copie du film sur un support**. Il existe deux types de supports :

- **Le fichier numérique (DCP)** : il n'est lisible que par un projecteur **numérique**, et seulement jusqu'à la **date d'expiration de sa clé de lecture**.
- **La copie 35 mm** : elle n'est lisible que par un projecteur **argentique**. La pellicule s'**use** : chaque projection compte, et à partir de **50 projections** la copie n'est plus projetable.

> « Quand on lance une séance, je veux que le système vérifie **lui-même** si le support peut être lu par le projecteur de la salle. Et le jour où on recevra un nouveau type de support (on parle d'une livraison par satellite), je veux qu'on puisse l'ajouter **sans réécrire tout le reste**. »

### 2.4 Les séances et la billetterie

Une **séance**, c'est la projection d'un **support de film** dans une **salle**, à une **date** donnée, avec un **tarif de base** (par exemple 10 €).

- **Démarrer** une séance n'est possible que si la salle est libre **et** que le support est lisible par le projecteur de la salle **à la date de la séance**. Sinon, le système refuse et explique **pourquoi**.
- **Terminer** une séance fait passer la salle en nettoyage et, pour une copie 35 mm, compte une projection de plus.
- On ne peut jamais vendre plus de billets que la **capacité de la salle**. Le nombre de places restantes doit toujours être juste, et personne ne doit pouvoir le modifier « à la main ».

Il existe 3 **types de billets**, chacun avec sa propre règle de prix :

| Billet | Prix |
| --- | --- |
| Plein tarif | tarif de base de la séance |
| Étudiant | tarif de base − 25 % |
| Enfant | 5 € fixe |

> « Je veux connaître la **recette d'une séance** sans me soucier du type de chaque billet. Et on va sûrement ajouter un tarif senior le mois prochain. »

### 2.5 Le programme du cinéma

C'est le cinéma qui **programme** les séances : on lui indique le numéro de la salle, le support, la date et le tarif de base. Il refuse une salle qui n'est pas l'une des siennes.

### 2.6 Les comportements métier attendus

Au-delà des règles ci-dessus, l'équipe veut pouvoir **interroger** le système et lui **demander d'agir**. Voici ce qu'elle attend, avec ses mots :

| # | Ce que demande l'équipe | Exemple |
| --- | --- | --- |
| B1 | « Programme une séance de ce support dans telle salle, tel jour, à tel tarif. » | Séance de « Dune 3 » en Salle 1, à 10 € |
| B2 | « Vends ce billet pour cette séance. » | 1 billet étudiant |
| B3 | « Démarre la séance », « Termine la séance », « Le ménage de la salle est fini. » | La salle passe en nettoyage |
| B4 | « Combien reste-t-il de **places** pour cette séance ? » | 96 |
| B5 | « Quel est le **taux de remplissage** de cette séance ? » (billets vendus / capacité de la salle) | 4 % |
| B6 | « Quelle est la **recette** de cette séance ? » | 32,50 € |
| B7 | « Dans quel **état** est la salle n° X ? » | Salle 2 : en nettoyage |
| B8 | « Quelles salles sont **libres** en ce moment ? » | Salle 1 |
| B9 | « Quelles séances sont **programmées** tel jour ? » | Dune 3 (Salle 1), I Play Rocky (Salle 2)... |
| B10 | « Quelle est la **recette du jour** ? » (somme des recettes des séances de la journée) | 50,50 € |
| B11 | « Combien de fois cette copie 35 mm a-t-elle été **projetée** ? » | 50 |

> « Quand je pose une de ces questions, je ne veux pas qu'on me réponde en fouillant dans les données : je veux que **l'objet qui sait** me réponde. »

Une **interrogation** (B4 à B11) ne modifie jamais rien. Une **action** (B1 à B3) qui viole une règle de la section 2 est refusée avec son motif.

## 3. Ce qui vous est demandé

### 3.1 Attendus de conception

Votre modèle doit mettre en œuvre **les 4 piliers de la POO** : encapsulation, abstraction, héritage et polymorphisme. Il doit contenir **au minimum** :

- **une classe abstraite** ;
- **une interface** ;
- **une relation de composition** ;
- **une énumération** pour représenter un état ;
- des **relations d'héritage** pertinentes ;
- au moins **un traitement polymorphe** (une même méthode appelée sur des objets de types différents, sans tester leur type).

Chaque comportement métier de la section 2.6 doit être porté par une **méthode de l'objet qui détient l'information** (par exemple, ce n'est pas `Main` qui additionne les prix des billets).

À vous de déterminer **où** ces notions ont du sens dans le métier décrit ci-dessus. Vous devrez être capables de **justifier à l'oral** chacun de vos choix (par exemple : pourquoi une classe abstraite ici et une interface là).

### 3.2 Livrables

1. **Un diagramme de classes UML** (draw.io, PlantUML, Mermaid ou papier photographié) avec :
   - les classes, classes abstraites, interfaces et énumérations, avec la notation UML adaptée ;
   - les attributs avec leur visibilité et les méthodes principales, dont celles qui réalisent les comportements B1 à B11 ;
   - les relations avec la bonne notation (héritage, réalisation d'interface, composition, association) et leurs **multiplicités**.
2. **Un diagramme d'états** de la salle, avec ce qui déclenche chaque transition.
3. **Le code Java** du modèle.
4. **Un programme `Main`** qui rejoue le scénario ci-dessous et affiche ce qui se passe, y compris les refus.

Un point d'étape est prévu après **45 minutes** : vous présenterez votre diagramme de classes à un autre binôme avant de commencer à coder.

### 3.3 Scénario de démonstration (à rejouer dans `Main`)

Toutes les séances ont lieu le **même jour J**, le **15/10/2026**. Entre parenthèses, le comportement métier mis en œuvre.

> **Test d'acceptance :** `java/src/test/java/cine/AcceptanceTest.java` exécute votre `Main` et compare tout ce qu'il affiche au fichier `AcceptanceTest.scenario_de_demonstration.approved.txt` (test d'approbation). Ce fichier est la **sortie attendue** du scénario, au caractère près : lisez-le avant de commencer. Ne le modifiez pas, c'est votre code qui doit s'y conformer. Lancez-le avec `mvn test` depuis le dossier `java`.

1. Créer le cinéma **CinéCoda** avec 2 salles :
   - Salle 1 : 100 places, projecteur numérique ;
   - Salle 2 : 3 places (une salle de test), projecteur argentique.

   Afficher les salles libres (**B8**) → Salle 1 et Salle 2.
2. Créer les films et leurs supports :
   - « Dune 3 » (166 min) en fichier numérique, clé valable jusqu'au 30 du mois ;
   - « Avengers: Doomsday » (119 min) en fichier numérique, clé **expirée** depuis hier ;
   - « I Play Rocky » (103 min) en copie 35 mm ayant déjà **49 projections**.
3. Programmer une séance de « Dune 3 » en Salle 1, tarif de base 10 € (**B1**). Vendre 2 pleins tarifs, 1 étudiant et 1 enfant (**B2**), puis afficher :
   - les places restantes (**B4**) → **96** ;
   - le taux de remplissage (**B5**) → **4 %** ;
   - la recette (**B6**) → **32,50 €**.
4. Programmer une séance d'« Avengers: Doomsday » en Salle 1 et tenter de la démarrer (**B3**) → **refus** (clé expirée).
5. Programmer une séance du « I Play Rocky » en Salle 1 et tenter de la démarrer → **refus** (pellicule sur projecteur numérique). Afficher l'état de la Salle 1 (**B7**) → toujours **libre** : les refus n'ont rien modifié.
6. Programmer une séance du « I Play Rocky » en Salle 2, tarif de base 8 € :
   - vendre 3 billets étudiants, tenter d'en vendre un 4ᵉ → **refus** (salle pleine) ;
   - afficher les places restantes → **0** et le taux de remplissage → **100 %** ;
   - démarrer puis terminer la séance ;
   - afficher l'état de la Salle 2 (**B7**) → **en nettoyage**, les salles libres (**B8**) → Salle 1 seulement, et le nombre de projections de la copie (**B11**) → **50**.
7. Programmer une nouvelle séance du « I Play Rocky » en Salle 2 :
   - tenter de la démarrer → **refus** (salle en nettoyage) ;
   - terminer le nettoyage (**B3**) et retenter → **refus** (copie usée).
8. Tenter de programmer une séance en Salle 3 (**B1**) → **refus** (le cinéma n'a pas de salle 3).
9. Bilan de la journée J :
   - afficher les séances programmées (**B9**) → les 5 séances des étapes 3 à 7, y compris celles qui n'ont pas pu démarrer ;
   - afficher la recette du jour (**B10**) → 32,50 € + 3 × 6 € = **50,50 €**.

### 3.4 Contraintes techniques

- Java 25 ou plus, sans framework ni bibliothèque externe, tout en mémoire.
- Une règle métier violée lève une **exception** avec un message clair, jamais un `System.out.println` dans le modèle ni un `return false` silencieux.
- Pas de `LocalDate.now()` au fond du modèle : la date est celle de la séance, ce qui permet de tester l'expiration sans attendre.
- Les montants s'affichent au format français (`32,50 €`) quelle que soit la configuration de la machine : précisez la langue, par exemple `String.format(Locale.FRANCE, "%.2f €", montant)`.

## 4. Pour ceux qui finissent en avance

1. Ajouter un **billet senior** (−30 %). Combien de classes existantes avez-vous dû modifier ?
2. Ajouter la **livraison par satellite** : un support numérique sans clé, mais **supprimé** automatiquement après 3 projections. Même question.
3. Écrire un **test JUnit** par règle de la section 2 (une règle = au moins un test qui vérifie le refus).
