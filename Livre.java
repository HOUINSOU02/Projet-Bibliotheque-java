import java.io.Serializable;


public class Livre implements Serializable {

    // Numéro de version pour la sérialisation, évite les InvalidClassException
    private static final long serialVersionUID = 1L;

    private final String identifiantInterne; // Clé primaire, non modifiable
    private String titre;
    private String auteur;
    private String isbn; // ISBN officiel, optionnel et modifiable
    private int anneePublication;

    
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
