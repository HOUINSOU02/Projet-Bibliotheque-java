import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Bibliotheque implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String NOM_FICHIER = "bibliotheque.ser";

    private final ArrayList<Livre> listeLivres;

    
    public Bibliotheque() {
        this.listeLivres = new ArrayList<>();
    }

    
    public void ajouterLivre(Livre livre) {
        if (livre != null) {
            listeLivres.add(livre);
        }
    }

    
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

    
    public Livre rechercherLivre(String id) {
        for (Livre livre : listeLivres) {
            if (livre.getIdentifiantInterne().equals(id)) {
                return livre;
            }
        }
        return null; // Non trouvé
    }

    
    public List<Livre> rechercherParTitre(String titre) {
        return listeLivres.stream()
                .filter(livre -> livre.getTitre().toLowerCase().contains(titre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livre> rechercherParAuteur(String auteur) {
        return listeLivres.stream()
                .filter(livre -> livre.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .collect(Collectors.toList());
    }


    
    public boolean supprimerLivre(String id) {
        Livre livreASupprimer = rechercherLivre(id);
        if (livreASupprimer != null) {
            listeLivres.remove(livreASupprimer);
            return true;
        }
        return false;
    }

    
    public void sauvegarder() {
        try (FileOutputStream fos = new FileOutputStream(NOM_FICHIER);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this); // 'this' est l'instance courante de Bibliotheque
            System.out.println("La bibliothèque a été sauvegardée avec succès dans " + NOM_FICHIER);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la bibliothèque : " + e.getMessage());
        }
    }

    
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
    
    
    public boolean estVide() {
        return listeLivres.isEmpty();
    }
}
