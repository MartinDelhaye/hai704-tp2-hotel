package hai704i.tp2.hotel.webService.dto.reservation;

public class Voyageur {
    private String nom;
    private String prenom;

    public Voyageur() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}