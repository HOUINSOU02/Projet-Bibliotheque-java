import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gère une collection de livres et assure la persistance des données
 * par sérialisation.
 */
public class Bibliotheque implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String NOM_FICHIER = "bibliotheque.ser";

    private final ArrayList<Livre> listeLivres;

    /**
     * Constructeur privé pour initialiser la liste de livres.
     */
    public Bibliotheque() {
        this.listeLivres = new ArrayList<>();
    }

    /**
     * Ajoute un livre à la collection.
     * @param livre Le livre à ajouter.
     */
    public void ajouterLivre(Livre livre) {
        if (livre != null) {
            listeLivres.add(livre);
        }
    }

    /**
     * Affiche tous les livres de la collection.
     */
    public void afficherLivres() {
        if (listeLivres.isEmpty()) {
            System.out.println("La bibliothèque est vide.");
        } else {
            System.out.println("--- Contenu de la bibliothèque ---");
            for (Livre livre : listeLivres) {
                System.out.println(livre);
            }
            System.out.println("---------------------------------");
        }
    }

    /**
     * Recherche un livre par son identifiant interne unique.
     * @param id L'identifiant interne du livre à rechercher.
     * @return Le Livre trouvé ou null si aucun livre ne correspond.
     */
    public Livre rechercherLivre(String id) {
        for (Livre livre : listeLivres) {
            if (livre.getIdentifiantInterne().equals(id)) {
                return livre;
            }
        }
        return null; // Non trouvé
    }

    /**
     * Recherche des livres par titre (insensible à la casse).
     * @param titre Le titre (ou une partie du titre) à rechercher.
     * @return Une liste de livres correspondants.
     */
    public List<Livre> rechercherParTitre(String titre) {
        return listeLivres.stream()
                .filter(livre -> livre.getTitre().toLowerCase().contains(titre.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche des livres par auteur (insensible à la casse).
     * @param auteur L'auteur (ou une partie du nom) à rechercher.
     * @return Une liste de livres correspondants.
     */
    public List<Livre> rechercherParAuteur(String auteur) {
        return listeLivres.stream()
                .filter(livre -> livre.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .collect(Collectors.toList());
    }


    /**
     * Supprime un livre de la collection en se basant sur son identifiant interne.
     * @param id L'identifiant interne du livre à supprimer.
     * @return true si le livre a été trouvé et supprimé, false sinon.
     */
    public boolean supprimerLivre(String id) {
        Livre livreASupprimer = rechercherLivre(id);
        if (livreASupprimer != null) {
            listeLivres.remove(livreASupprimer);
            return true;
        }
        return false;
    }

    /**
     * Sauvegarde l'état actuel de la bibliothèque dans un fichier.
     */
    public void sauvegarder() {
        try (FileOutputStream fos = new FileOutputStream(NOM_FICHIER);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this); // 'this' est l'instance courante de Bibliotheque
            System.out.println("La bibliothèque a été sauvegardée avec succès dans " + NOM_FICHIER);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la bibliothèque : " + e.getMessage());
        }
    }

    /**
     * Charge l'état de la bibliothèque depuis un fichier.
     * Si le fichier n'existe pas, une nouvelle bibliothèque est créée.
     * @return Une instance de Bibliotheque.
     */
    public static Bibliotheque charger() {
        File fichier = new File(NOM_FICHIER);
        if (fichier.exists()) {
            try (FileInputStream fis = new FileInputStream(fichier);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                Bibliotheque biblio = (Bibliotheque) ois.readObject();
                System.out.println("La bibliothèque a été chargée avec succès depuis " + NOM_FICHIER);
                return biblio;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Impossible de charger la bibliothèque. Une nouvelle sera créée. Erreur : " + e.getMessage());
                return new Bibliotheque();
            }
        } else {
            System.out.println("Aucun fichier de sauvegarde trouvé. Création d'une nouvelle bibliothèque.");
            return new Bibliotheque();
        }
    }
    
    /**
     * Vérifie si la bibliothèque est vide.
     * @return true si aucun livre n'est présent, false sinon.
     */
    public boolean estVide() {
        return listeLivres.isEmpty();
    }
}