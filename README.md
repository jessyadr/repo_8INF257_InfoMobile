# StudyWisely – PP1
8INF257 – Informatique mobile  
Hiver 2026  

## Objectif du projet (PP1)

Ce premier prototype (PP1) a pour objectif de poser les bases de l’application **StudyWisely**, une application mobile permettant la création et la gestion de routines d’étude simples.

L’objectif principal est de mettre en place :

- La structure initiale du projet Android
- Une interface utilisateur fonctionnelle
- L’affichage dynamique de données
- Une architecture propre avec ViewModel

---

## 1. Prototype Figma

Un prototype interactif a été réalisé sur Figma afin de :

- Définir la structure visuelle de l’application
- Valider l’expérience utilisateur
- Simuler la navigation entre les écrans

Le design comprend :

- Une page d’accueil affichant la liste des routines
- Un bouton pour ajouter une nouvelle routine (fonctionnalité future)

Le visuel du prototype a été respecté dans l’implémentation Android.

---

## 2. Configuration initiale

Le projet a été créé avec :

- **Android Studio**
- **Kotlin**
- **Jetpack Compose**
- Architecture MVVM simplifiée

Configuration réalisée :

- Création du projet Android
- Activation de Jetpack Compose
- Organisation des packages :
  - `navigation`
  - `presentation`
  - `viewmodel`
  - `data`
  - `ui.theme`

---

## 3. Interface utilisateur

L’interface a été développée entièrement avec **Jetpack Compose**.

### Écran principal :

- Affichage d’une liste de routines existantes
- Chaque routine est représentée par un composant `RoutineCard`
- Design conforme au prototype Figma
- Utilisation d’un `Scaffold`
- Respect du thème personnalisé (couleurs + typographie)

---

## 4. Gestion des routines

Les routines sont actuellement :

- Stockées dans un fichier utilitaire (`RoutineUtils`)
- Chargées via un `ViewModel`
- Exposées via un `State<List<RoutineVM>>`
- Rafraîchies dynamiquement

Architecture utilisée :

- `ListRoutinesViewModel`
- Séparation UI / logique
- Respect des bonnes pratiques Compose

---

