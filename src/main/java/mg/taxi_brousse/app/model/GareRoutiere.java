package mg.taxi_brousse.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gare_routiere")
public class GareRoutiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_gare;

    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    public GareRoutiere() {}

    public GareRoutiere(String nom, String ville, String adresse, String telephone) {
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public int getId_gare() {
        return id_gare;
    }

    public void setId_gare(int id_gare) {
        this.id_gare = id_gare;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return nom + " (" + ville + ")";
    }
}
