# Projet de Gestion d'une Bibliothèque en Java

Ce projet est une application console simple développée en Java pour gérer une collection de livres. Il met en œuvre les principes de la programmation orientée objet et utilise la sérialisation Java pour assurer la persistance des données (sauvegarde et chargement de l'état de la bibliothèque dans un fichier).

## Prérequis

Pour compiler et exécuter ce projet, vous devez avoir un **Java Development Kit (JDK)** installé sur votre machine (version 8 ou supérieure recommandée).

- Pour vérifier si Java est installé, ouvrez un terminal (ou une invite de commandes) et tapez :
  ```bash
  java -version
  ```

## Installation

Aucune installation complexe n'est requise. Il vous suffit de cloner ce dépôt ou de télécharger les trois fichiers source Java dans un même répertoire :

- `Livre.java`
- `Bibliotheque.java`
- `Main.java`

## Utilisation

Suivez ces étapes pour compiler et exécuter l'application.

### Étape 1 : Compilation

1. Ouvrez un terminal ou une invite de commandes.
2. Naviguez jusqu'au répertoire où vous avez placé les fichiers `.java`.
3. Compilez les fichiers source avec la commande suivante :

   ```bash
   javac Livre.java Bibliotheque.java Main.java
   ```

   Cette commande générera les fichiers `.class` correspondants (`Livre.class`, `Bibliotheque.class`, `Main.class`).

### Étape 2 : Exécution

Une fois la compilation réussie, lancez l'application avec la commande :

```bash
java Main
```

L'application démarre et vous présente un menu interactif :

```
--- Menu de la Bibliothèque ---
1: Afficher tous les livres
2: Ajouter un livre
3: Rechercher un livre
4: Supprimer un livre
0: Quitter et sauvegarder
-----------------------------
Votre choix : 
```

- **Lors du premier lancement**, comme aucun fichier de sauvegarde (`bibliotheque.ser`) n'existe, l'application ajoutera automatiquement 3 livres de démonstration.
- **Lors des lancements suivants**, l'application chargera les livres depuis le fichier `bibliotheque.ser`.

Vous pouvez utiliser les différentes options du menu pour gérer votre bibliothèque. Lorsque vous choisirez l'option **0**, toutes vos modifications (ajouts, suppressions) seront sauvegardées dans le fichier `bibliotheque.ser` avant que l'application ne se ferme.
