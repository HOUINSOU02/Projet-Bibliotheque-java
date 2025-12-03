import java.io.Serializable;

/**
 * Représente un livre avec ses informations de base.
 * Cette classe est sérialisable pour permettre la persistance de ses instances.
 */
public class Livre implements Serializable {

    // Numéro de version pour la sérialisation, évite les InvalidClassException
    private static final long serialVersionUID = 1L;

    private final String identifiantInterne; // Clé primaire, non modifiable
    private String titre;
    private String auteur;
    private String isbn; // ISBN officiel, optionnel et modifiable
    private int anneePublication;

    /**
     * Construit une nouvelle instance de Livre.
     *
     * @param identifiantInterne L'identifiant unique généré par le système.
     * @param titre Le titre du livre.
     * @param auteur L'auteur du livre.
     * @param isbn L'ISBN officiel du livre (peut être vide).
     * @param anneePublication L'année de publication du livre.
     */
    public Livre(String identifiantInterne, String titre, String auteur, String isbn, int anneePublication) {
        this.identifiantInterne = identifiantInterne;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.anneePublication = anneePublication;
    }

    // --- Getters ---
    public String getIdentifiantInterne() { return identifiantInterne; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getIsbn() { return isbn; }
    public int getAnneePublication() { return anneePublication; }
    
    // --- Setters ---
    public void setTitre(String titre) { this.titre = titre; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public void setIsbn(String isbn) { this.isbn = isbn; } // L'ISBN peut être modifié
    public void setAnneePublication(int anneePublication) { this.anneePublication = anneePublication; }

    @Override
    public String toString() {
        String isbnDisplay = (isbn == null || isbn.isEmpty()) ? "Non renseigné" : isbn;
        return String.format("Livre [ID: %s, Titre: %s, Auteur: %s, ISBN: %s, Année: %d]",
                identifiantInterne, titre, auteur, isbnDisplay, anneePublication);
    }
}