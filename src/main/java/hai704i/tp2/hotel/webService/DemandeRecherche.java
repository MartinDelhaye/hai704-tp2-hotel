package hai704i.tp2.hotel.webService;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlSchemaType;

public class DemandeRecherche {
    private String ville;
    private XMLGregorianCalendar arrivee;
    private XMLGregorianCalendar depart;
    private int personnes;
    private int etoilesMin;
    private BigDecimal prixMax;

    public DemandeRecherche() {
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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

    public int getPersonnes() {
        return personnes;
    }

    public void setPersonnes(int personnes) {
        this.personnes = personnes;
    }

    public int getEtoilesMin() {
        return etoilesMin;
    }

    public void setEtoilesMin(int etoilesMin) {
        this.etoilesMin = etoilesMin;
    }

    public BigDecimal getPrixMax() {
        return prixMax;
    }

    public void setPrixMax(BigDecimal prixMax) {
        this.prixMax = prixMax;
    }
}