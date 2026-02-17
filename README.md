# StudyWisely – Application mobile de gestion de routines d’étude

## Projet 8INF257 – Informatique mobile (Hiver 2026)

### Description générale

StudyWisely est une application Android développée en Kotlin avec Jetpack Compose. Le projet vise à proposer un outil simple permettant aux étudiants de structurer leurs révisions à l’aide de routines planifiées. L’application permet de créer, visualiser et organiser des tâches de révision afin d’améliorer la planification du travail académique.

Le développement est organisé en phases (PP1, PP2, etc.), où chaque partie ajoute progressivement de la logique et des fonctionnalités à une base d’interface initialement statique.

---

## Structure générale du projet

Le projet suit une architecture de type MVVM (Model – View – ViewModel), afin de séparer clairement :

* la logique métier
* l’interface utilisateur
* la gestion des données

Cette séparation permet une meilleure organisation du code et facilite les modifications futures.

---

## Organisation des dossiers

### data/

Contient les éléments liés aux données et aux modèles.

* **model/**

  * `RoutineVM.kt`
    Représente une routine d’étude. Chaque routine possède :

    * un identifiant unique
    * un titre
    * une description
    * un label de temps (date/heure)
    * un niveau de priorité

  * `PriorityType.kt`
    Définit les niveaux de priorité possibles :

    * Faible
    * Moyenne
    * Élevée

* **util/**

  * `RoutineUtils.kt`
    Sert de stockage temporaire en mémoire.
    Une liste mutable de routines est conservée localement pour simuler une base de données.
    Ce composant permet :

    * d’obtenir toutes les routines
    * d’ajouter ou modifier une routine (upsert)
    * de supprimer une routine

Ce choix permet de tester la logique sans base de données réelle.

---

### viewmodel/

Contient la logique applicative intermédiaire entre l’interface et les données.

* `AddEditRoutineViewModel.kt`
  Gère l’état du formulaire d’ajout/modification.
  Il met à jour les champs en fonction des actions de l’utilisateur et déclenche l’enregistrement via `RoutineUtils`.

* `AddEditRoutineEvent.kt`
  Définit les événements possibles du formulaire :

  * saisie du titre
  * saisie de la description
  * saisie du temps
  * choix de la priorité
  * sauvegarde de la routine

* `ListRoutinesViewModel.kt`
  Gère l’affichage de la liste des routines.
  Il récupère les données depuis `RoutineUtils` et permet de rafraîchir la liste.

Le rôle principal des ViewModels est de centraliser la logique et d’éviter que l’interface accède directement aux données.

---

### presentation/

Contient l’interface utilisateur construite avec Jetpack Compose.

* **list/**

  * `RoutinesListScreen.kt`
    Écran principal affichant toutes les routines sous forme de liste.
    Les données proviennent de `ListRoutinesViewModel`.

* **addedit/**

  * `AddEditRoutineScreen.kt`
    Écran permettant de créer une nouvelle routine.
    Les champs du formulaire sont reliés au `AddEditRoutineViewModel`.

* **components/**

  * `RoutineCard.kt`
    Composant visuel représentant une routine dans la liste :

    * titre
    * heure
    * priorité

---

### navigation/

Gère la navigation entre les écrans.

* `Screen.kt`
  Définit les routes de navigation (liste, ajout).

* `AppNavHost.kt`
  Configure la navigation Compose entre les écrans.

---

### ui/theme/

Gère l’apparence globale de l’application.

* `Color.kt` : couleurs principales
* `Theme.kt` : configuration du thème Material
* `Type.kt` : typographie et police utilisée

---

## Fonctionnement global de l’application

1. L’écran principal affiche une liste de routines.
2. Un bouton permet d’accéder à l’écran d’ajout.
3. Le formulaire permet de saisir :

   * un nom de routine
   * une description
   * une date/heure
4. Lors de l’enregistrement :

   * les données sont envoyées au ViewModel
   * le ViewModel enregistre la routine via `RoutineUtils`
   * l’application revient à l’écran principal
5. La liste est rafraîchie pour afficher la nouvelle routine.

---

## Partie du PP2 déjà intégrée

Une partie fonctionnelle du PP2 a été ajoutée, notamment :

* liaison entre l’interface et les ViewModels
* gestion des événements du formulaire
* création réelle d’une routine
* insertion dans la liste mémoire
* rafraîchissement dynamique de l’écran principal

Le système permet maintenant d’ajouter une routine et de la voir apparaître dans la liste sans redémarrer l’application.

---

## Choix techniques

* Kotlin comme langage principal
* Jetpack Compose pour l’interface
* Architecture MVVM pour structurer le projet
* Navigation Compose pour gérer les écrans
* Stockage temporaire en mémoire (en attendant une base de données)

---

## Logique importante à comprendre

* L’interface ne modifie jamais directement les données.
* Les interactions passent toujours par les ViewModels.
* `RoutineUtils` agit comme une fausse base de données.
* Le rafraîchissement de la liste est nécessaire pour refléter les nouvelles données.
* Chaque routine possède un identifiant généré automatiquement.

---

## Évolutions prévues

Plusieurs extensions sont prévues pour enrichir l’application :

* ajout d’une date d’examen associée à une routine
* priorisation automatique des tâches selon la proximité des évaluations
* système de notifications
* stockage persistant (Room, DataStore ou Firebase)

La structure actuelle permet d’intégrer ces fonctionnalités sans modifier l’architecture de base.

---

## État actuel

L’application permet actuellement :

* d’afficher une liste de routines
* de créer une nouvelle routine
* de sauvegarder les données en mémoire
* de visualiser immédiatement les ajouts dans la liste

La base technique du projet est maintenant en place pour continuer les phases suivantes.
