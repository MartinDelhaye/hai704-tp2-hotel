package hai704i.tp2.hotel.webService.dto.reservation;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"idDemande", "identification", "idOffre", "voyageur", "paiement"})
public class DemandeReservation {
    private String idDemande;
    private IdentificationAgence identification;
    private String idOffre;
    private Voyageur voyageur;
    private Paiement paiement;

    public DemandeReservation() {
    }

    public String getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(String idDemande) {
        this.idDemande = idDemande;
    }

    public IdentificationAgence getIdentification() {
        return identification;
    }

    public void setIdentification(IdentificationAgence identification) {
        this.identification = identification;
    }

    public String getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(String idOffre) {
        this.idOffre = idOffre;
    }

    public Voyageur getVoyageur() {
        return voyageur;
    }

    public void setVoyageur(Voyageur voyageur) {
        this.voyageur = voyageur;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
}