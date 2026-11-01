package hai704i.tp2.hotel.webService.dto;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlSchemaType;

public class Confirmation {
    private String referenceReservation;
    private String idOffre;
    private String hotel;
    private String chambre;
    private XMLGregorianCalendar arrivee;
    private XMLGregorianCalendar depart;
    private BigDecimal prixTotal;
    private boolean rejeu;

    public Confirmation() {
    }

    public String getReferenceReservation() {
        return referenceReservation;
    }

    public void setReferenceReservation(String r) {
        this.referenceReservation = r;
    }

    public String getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(String idOffre) {
        this.idOffre = idOffre;
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

    public boolean isRejeu() {
        return rejeu;
    }

    public void setRejeu(boolean rejeu) {
        this.rejeu = rejeu;
    }
}