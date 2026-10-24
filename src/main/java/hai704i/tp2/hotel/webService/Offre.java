package hai704i.tp2.hotel.webService;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlSchemaType;

public class Offre {
    private String id;
    private String hotel;
    private String chambre;
    private int etoiles;
    private int capacite;
    private XMLGregorianCalendar arrivee;
    private XMLGregorianCalendar depart;
    private BigDecimal prixTotal;

    public Offre() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getChambre() {
        return chambre;
    }

    public void setChambre(String chambre) {
        this.chambre = chambre;
    }

    public int getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(int etoiles) {
        this.etoiles = etoiles;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getArrivee() {
        return arrivee;
    }

    public void setArrivee(XMLGregorianCalendar arrivee) {
        this.arrivee = arrivee;
    }

    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getDepart() {
        return depart;
    }

    public void setDepart(XMLGregorianCalendar depart) {
        this.depart = depart;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }
}