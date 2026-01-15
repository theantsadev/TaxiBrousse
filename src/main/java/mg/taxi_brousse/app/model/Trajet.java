package mg.taxi_brousse.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trajet")
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_trajet;

    @ManyToOne
    @JoinColumn(name = "id_gare_depart", nullable = false)
    private GareRoutiere gare_depart;

    @ManyToOne
    @JoinColumn(name = "id_gare_arrivee", nullable = false)
    private GareRoutiere gare_arrivee;

    private double distance_km;
    private int duree_estimee;
    private double tarif_defaut;

    public Trajet() {}

    public Trajet(GareRoutiere gare_depart, GareRoutiere gare_arrivee, double distance_km, int duree_estimee, double tarif_defaut) {
        this.gare_depart = gare_depart;
        this.gare_arrivee = gare_arrivee;
        this.distance_km = distance_km;
        this.duree_estimee = duree_estimee;
        this.tarif_defaut = tarif_defaut;
    }

    public int getId_trajet() {
        return id_trajet;
    }

    public void setId_trajet(int id_trajet) {
        this.id_trajet = id_trajet;
    }

    public GareRoutiere getGare_depart() {
        return gare_depart;
    }

    public void setGare_depart(GareRoutiere gare_depart) {
        this.gare_depart = gare_depart;
    }

    public GareRoutiere getGare_arrivee() {
        return gare_arrivee;
    }

    public void setGare_arrivee(GareRoutiere gare_arrivee) {
        this.gare_arrivee = gare_arrivee;
    }

    public double getDistance_km() {
        return distance_km;
    }

    public void setDistance_km(double distance_km) {
        this.distance_km = distance_km;
    }

    public int getDuree_estimee() {
        return duree_estimee;
    }

    public void setDuree_estimee(int duree_estimee) {
        this.duree_estimee = duree_estimee;
    }

    public double getTarif_defaut() {
        return tarif_defaut;
    }

    public void setTarif_defaut(double tarif_defaut) {
        this.tarif_defaut = tarif_defaut;
    }
}
