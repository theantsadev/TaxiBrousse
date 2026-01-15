package mg.taxi_brousse.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "voyage")
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_voyage;

    @ManyToOne
    @JoinColumn(name = "id_trajet", nullable = false)
    private Trajet trajet;

    private LocalDate date_depart;
    private LocalTime heure_depart;

    @ManyToOne
    @JoinColumn(name = "id_voiture", nullable = false)
    private Voiture voiture;

    private Double tarif_voyage;
    private String statut;

    public Voyage() {
    }

    public Voyage(Trajet trajet, LocalDate date_depart, LocalTime heure_depart, Voiture voiture, Double tarif_voyage,
            String statut) {
        this.trajet = trajet;
        this.date_depart = date_depart;
        this.heure_depart = heure_depart;
        this.voiture = voiture;
        this.tarif_voyage = tarif_voyage;
        this.statut = statut;
    }

    public int getId_voyage() {
        return id_voyage;
    }

    public void setId_voyage(int id_voyage) {
        this.id_voyage = id_voyage;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public LocalDate getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(LocalDate date_depart) {
        this.date_depart = date_depart;
    }

    public LocalTime getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(LocalTime heure_depart) {
        this.heure_depart = heure_depart;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Double getTarif_voyage() {
        return tarif_voyage != null ? tarif_voyage : trajet.getTarif_defaut();
    }

    public void setTarif_voyage(Double tarif_voyage) {
        this.tarif_voyage = tarif_voyage;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
