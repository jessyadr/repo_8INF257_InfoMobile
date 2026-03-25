README – StudyWisely (PP2 : Gestion des routines)

1. Présentation générale

StudyWisely est une application mobile développée en Kotlin avec Jetpack Compose dans le cadre du cours 8INF257 – Informatique mobile.

L’objectif principal est d’aider les étudiants à organiser leurs routines de travail et de révision en fonction de leurs échéances académiques.

Ce projet correspond à la phase PP2, qui introduit la gestion complète des routines avec persistance des données et logique de priorisation.

------------------------------------------------------------------

2. Objectifs du PP2

Le PP2 vise à implémenter :

- Création de routines via formulaire
- Modification de routines existantes
- Suppression de routines
- Stockage des données en base SQLite
- Navigation entre écrans
- Calcul automatique de la priorité
- Tri des routines selon leur urgence

------------------------------------------------------------------

3. Structure du projet

Le projet est structuré selon une architecture inspirée du modèle MVVM (Model – View – ViewModel).

data/
  model/
    - RoutineVM.kt : modèle de données d’une routine
    - PriorityType.kt : énumération des priorités

  local/
    - RoutineDatabaseHelper.kt : gestion SQLite (CRUD)

viewmodel/
  - ListRoutinesViewModel.kt : gestion de la liste et suppression
  - AddEditRoutineViewModel.kt : gestion ajout/modification et logique métier

presentation/
  list/
    - RoutinesListScreen.kt : écran principal (liste)

  addedit/
    - AddEditRoutineScreen.kt : écran ajout/modification

  components/
    - RoutineCard.kt : affichage d’une routine

navigation/
  - AppNavHost.kt : gestion navigation entre écrans

------------------------------------------------------------------

4. Fonctionnement de l’application

4.1 Ajout d’une routine

L’utilisateur remplit un formulaire comprenant :
- nom
- description
- date de la routine
- date d’examen ou livrable

Les données sont enregistrées dans SQLite.

4.2 Modification d’une routine

- Un clic sur une routine ouvre l’écran d’édition
- Les champs sont automatiquement remplis avec les renseignements concernant la routine sélectionnée
- L’enregistrement met à jour la routine existante

4.3 Suppression

- Bouton de suppression sur chaque routine
- Confirmation avant suppression

------------------------------------------------------------------

5. Base de données

La persistance est assurée via SQLite avec SQLiteOpenHelper.

Table : routines

Champs :
- id (clé primaire)
- title
- description
- routineDateTimeMillis
- examDateTimeMillis
- priority

Fonctions principales :
- insertRoutine
- updateRoutine
- deleteRoutine
- getAllRoutines

------------------------------------------------------------------

6. Logique de priorité

La priorité est calculée automatiquement selon la date d’examen.

Règles :
- ≤ 3 jours → Élevée
- ≤ 7 jours → Moyenne
- > 7 jours → Faible

Implémentation :

val priority = when {
    diffDays <= 0 -> PriorityType.Elevee
    diffDays <= 3 -> PriorityType.Elevee
    diffDays <= 7 -> PriorityType.Moyenne
    else -> PriorityType.Faible
}

------------------------------------------------------------------

7. Tri des routines

Les routines sont triées automatiquement selon la date d’examen :
- les plus urgentes apparaissent en premier

------------------------------------------------------------------

8. Navigation

Navigation avec Jetpack Navigation :

Ajout :
navController.navigate("add_edit_routine?routineId=-1")

Modification :
navController.navigate("add_edit_routine?routineId=${routine.id}")

Le routineId permet de charger une routine existante.

------------------------------------------------------------------

9. Problèmes rencontrés et solutions

1. Duplication des routines
- Cause : insertion au lieu de mise à jour
→ Solution : ajout de updateRoutine()

2. Crash navigation
- Cause : argument non déclaré
→ Solution : ajout navArgument

3. Liste non rafraîchie
→ Solution : ajout de refresh()

------------------------------------------------------------------

10. Support des caractères

- SQLite supporte Unicode
- Les champs acceptent :
  - accents
  - caractères spéciaux



