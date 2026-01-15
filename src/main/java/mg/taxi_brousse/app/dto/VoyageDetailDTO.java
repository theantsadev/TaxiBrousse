package mg.taxi_brousse.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoyageDetailDTO {
    private Integer id_voyage;
    private String gare_depart_nom;
    private String gare_depart_ville;
    private String gare_arrivee_nom;
    private String gare_arrivee_ville;
    private LocalDate date_depart;
    private LocalTime heure_depart;
    private String nom_voiture;
    private String type_voiture;
    private Integer capacite;
    private Integer places_disponibles;
    private Double tarif;
    private String statut;

    // Constructeur par défaut
    public VoyageDetailDTO() {
    }

    // Constructeur avec TOUS les paramètres dans l'ORDRE EXACT de la query
    public VoyageDetailDTO(Integer id_voyage,
            String gare_depart_nom,
            String gare_depart_ville,
            String gare_arrivee_nom,
            String gare_arrivee_ville,
            LocalDate date_depart,
            LocalTime heure_depart,
            String nom_voiture,
            String type_voiture,
            Integer capacite,
            Integer places_disponibles,
            Double tarif,
            String statut) {
        this.id_voyage = id_voyage;
        this.gare_depart_nom = gare_depart_nom;
        this.gare_depart_ville = gare_depart_ville;
        this.gare_arrivee_nom = gare_arrivee_nom;
        this.gare_arrivee_ville = gare_arrivee_ville;
        this.date_depart = date_depart;
        this.heure_depart = heure_depart;
        this.nom_voiture = nom_voiture;
        this.type_voiture = type_voiture;
        this.capacite = capacite;
        this.places_disponibles = places_disponibles;
        this.tarif = tarif;
        this.statut = statut;
    }
}