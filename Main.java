import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principale pour tester le système de gestion de bibliothèque.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bibliotheque maBibliotheque = Bibliotheque.charger();

        // Si c'est la première exécution (le fichier n'existe pas), ajoutez quelques objets Livre.
        if (maBibliotheque.estVide()) {
            System.out.println("Première exécution. Ajout de livres initiaux.");
            maBibliotheque.ajouterLivre(new Livre(genererIdentifiant(), "L'Étranger", "Albert Camus", "978-2070360024", 1942));
            maBibliotheque.ajouterLivre(new Livre(genererIdentifiant(), "1984", "George Orwell", "978-2070368228", 1949));
            maBibliotheque.ajouterLivre(new Livre(genererIdentifiant(), "Le Seigneur des Anneaux", "J.R.R. Tolkien", "978-2070612825", 1954));
        }

        while (true) {
            afficherMenu();
            System.out.print("Votre choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> maBibliotheque.afficherLivres();
                case "2" -> ajouterLivreManuellement(scanner, maBibliotheque);
                case "3" -> menuRecherche(scanner, maBibliotheque);
                case "4" -> modifierLivre(scanner, maBibliotheque);
                case "5" -> supprimerLivre(scanner, maBibliotheque);
                case "0" -> {
                    System.out.println("Sauvegarde de la bibliothèque et fermeture de l'application.");
                    maBibliotheque.sauvegarder();
                    scanner.close();
                    return; // Quitte le programme
                }
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
            System.out.println(); // Ligne vide pour l'aération
        }
    }

    private static void afficherMenu() {
        System.out.println("--- Menu de la Bibliothèque ---");
        System.out.println("1: Afficher tous les livres");
        System.out.println("2: Ajouter un livre");
        System.out.println("3: Rechercher un livre");
        System.out.println("4: Modifier un livre");
        System.out.println("5: Supprimer un livre");
        System.out.println("0: Quitter et sauvegarder");
        System.out.println("-----------------------------");
    }

    private static void ajouterLivreManuellement(Scanner scanner, Bibliotheque bibliotheque) {
        System.out.println("\n--- Ajout d'un nouveau livre ---");

        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();
        System.out.print("ISBN (optionnel, laissez vide si inconnu) : ");
        String isbn = scanner.nextLine();
        int annee;
        try {
            System.out.print("Année de publication : ");
            annee = scanner.nextInt();
            scanner.nextLine(); // Consomme le retour à la ligne restant
        } catch (InputMismatchException e) {
            System.out.println("Année invalide. L'ajout est annulé.");
            scanner.nextLine(); // Nettoie le scanner
            return;
        }

        // Génération de l'identifiant interne unique
        String idInterne = genererIdentifiant();

        Livre nouveauLivre = new Livre(idInterne, titre, auteur, isbn, annee);
        bibliotheque.ajouterLivre(nouveauLivre);

        System.out.println("Livre ajouté avec succès avec l'ID interne : " + idInterne);
    }

    private static String genererIdentifiant() {
        // Utiliser les nanosecondes pour garantir une haute probabilité d'unicité
        return "ID-" + System.nanoTime();
    }

    private static void menuRecherche(Scanner scanner, Bibliotheque bibliotheque) {
        System.out.println("\n--- Menu Recherche ---");
        System.out.println("1: Par ID Interne");
        System.out.println("2: Par titre");
        System.out.println("3: Par auteur");
        System.out.println("0: Retourner au menu principal");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();
        
        switch (choix) {
            case "1" -> {
                System.out.print("\nEntrez l'ID interne du livre à rechercher : ");
                String id = scanner.nextLine();
                Livre livreTrouve = bibliotheque.rechercherLivre(id);
                if (livreTrouve != null) {
                    System.out.println("Livre trouvé : " + livreTrouve);
                } else {
                    System.out.println("Aucun livre ne correspond à cet ID.");
                }
            }
            case "2" -> {
                System.out.print("\nEntrez le titre à rechercher : ");
                String titre = scanner.nextLine();
                java.util.List<Livre> livresParTitre = bibliotheque.rechercherParTitre(titre);
                afficherResultatsRecherche(livresParTitre);
            }
            case "3" -> {
                System.out.print("\nEntrez l'auteur à rechercher : ");
                String auteur = scanner.nextLine();
                java.util.List<Livre> livresParAuteur = bibliotheque.rechercherParAuteur(auteur);
                afficherResultatsRecherche(livresParAuteur);
            }
            case "0" -> {
                return; // On retourne simplement au menu principal
            }
            default -> System.out.println("Choix de recherche invalide.");
        }
    }

    private static void afficherResultatsRecherche(java.util.List<Livre> livres) {
        if (livres.isEmpty()) {
            System.out.println("Aucun livre trouvé pour ce critère.");
        } else {
            System.out.println("--- Résultats de la recherche ---");
            livres.forEach(System.out::println);
            System.out.println("---------------------------------");
        }
    }

    /**
     * Affiche un menu interactif pour permettre à l'utilisateur de trouver et de sélectionner un livre.
     * @param scanner Le scanner pour la saisie utilisateur.
     * @param bibliotheque L'instance de la bibliothèque.
     * @return Le livre sélectionné, ou null si l'utilisateur annule ou si aucun livre n'est trouvé.
     */
    private static Livre selectionnerLivre(Scanner scanner, Bibliotheque bibliotheque) {
        System.out.println("\n--- Comment trouver le livre ? ---");
        System.out.println("1: Par son ID interne");
        System.out.println("2: En recherchant par titre");
        System.out.println("0: Annuler et retourner au menu");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1":
                System.out.print("Entrez l'ID interne du livre : ");
                String id = scanner.nextLine();
                return bibliotheque.rechercherLivre(id);
            case "2":
                System.out.print("Entrez un mot du titre à rechercher : ");
                String termeRecherche = scanner.nextLine();
                java.util.List<Livre> resultats = bibliotheque.rechercherParTitre(termeRecherche);

                if (resultats.isEmpty()) {
                    return null; // Aucun livre trouvé
                }

                if (resultats.size() == 1) {
                    return resultats.get(0); // Un seul résultat, on le retourne directement
                }

                // Plusieurs résultats, on demande à l'utilisateur de choisir
                System.out.println("Plusieurs livres trouvés. Veuillez en sélectionner un :");
                for (int i = 0; i < resultats.size(); i++) {
                    System.out.println((i + 1) + ": " + resultats.get(i));
                }
                System.out.println("0: Annuler");
                System.out.print("Votre choix : ");
                try {
                    int indexChoisi = scanner.nextInt();
                    scanner.nextLine(); // Consomme le retour à la ligne
                    if (indexChoisi > 0 && indexChoisi <= resultats.size()) {
                        return resultats.get(indexChoisi - 1);
                    }
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Nettoie le scanner
                }
                return null; // Choix invalide ou annulation
            case "0":
            default:
                return null; // Annulation ou choix invalide
        }
    }

    private static void modifierLivre(Scanner scanner, Bibliotheque bibliotheque) {
        System.out.println("\n--- Modification d'un livre ---");
        Livre livreAModifier = selectionnerLivre(scanner, bibliotheque);

        if (livreAModifier == null) {
            System.out.println("Aucun livre sélectionné ou trouvé. Opération annulée.");
            return;
        }

        System.out.println("\nModification du livre : " + livreAModifier);
        System.out.println("Laissez le champ vide pour ne pas modifier une information.");

        System.out.print("Nouveau titre [" + livreAModifier.getTitre() + "] : ");
        String nouveauTitre = scanner.nextLine();
        if (!nouveauTitre.trim().isEmpty()) {
            livreAModifier.setTitre(nouveauTitre);
        }

        System.out.print("Nouvel auteur [" + livreAModifier.getAuteur() + "] : ");
        String nouvelAuteur = scanner.nextLine();
        if (!nouvelAuteur.trim().isEmpty()) {
            livreAModifier.setAuteur(nouvelAuteur);
        }

        System.out.print("Nouvel ISBN officiel [" + livreAModifier.getIsbn() + "] : ");
        String nouvelIsbn = scanner.nextLine();
        if (!nouvelIsbn.trim().isEmpty()) {
            livreAModifier.setIsbn(nouvelIsbn);
        }

        System.out.print("Nouvelle année de publication [" + livreAModifier.getAnneePublication() + "] : ");
        String anneeStr = scanner.nextLine();
        if (!anneeStr.trim().isEmpty()) {
            try {
                int nouvelleAnnee = Integer.parseInt(anneeStr);
                livreAModifier.setAnneePublication(nouvelleAnnee);
            } catch (NumberFormatException e) {
                System.out.println("Format de l'année invalide. Cette information n'a pas été modifiée.");
            }
        }

        System.out.println("\nLivre mis à jour avec succès !");
    }

    private static void supprimerLivre(Scanner scanner, Bibliotheque bibliotheque) {
        System.out.println("\n--- Suppression d'un livre ---");
        Livre livreASupprimer = selectionnerLivre(scanner, bibliotheque);

        if (livreASupprimer == null) {
            System.out.println("Aucun livre sélectionné ou trouvé. Opération annulée.");
            return;
        }

        System.out.println("\nVous êtes sur le point de supprimer le livre suivant :");
        System.out.println(livreASupprimer);
        System.out.print("Confirmez-vous la suppression ? (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            if (bibliotheque.supprimerLivre(livreASupprimer.getIdentifiantInterne())) {
                System.out.println("Le livre a été supprimé avec succès.");
            } else {
                // Ce cas est peu probable si la sélection a bien fonctionné
                System.out.println("Erreur inattendue lors de la suppression.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }
}