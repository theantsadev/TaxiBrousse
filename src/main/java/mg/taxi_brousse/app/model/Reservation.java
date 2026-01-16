package mg.taxi_brousse.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_reservation;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    @ManyToOne
    @JoinColumn(name = "id_type_place", nullable = true)
    private TypePlace typePlace;

    private int nombre_places_reservees;
    private LocalDate date_reservation;
    private double montant_total;
    private String statut;

    public Reservation() {}

    public Reservation(Client client, Voyage voyage, TypePlace typePlace, int nombre_places_reservees, LocalDate date_reservation, double montant_total, String statut) {
        this.client = client;
        this.voyage = voyage;
        this.typePlace = typePlace;
        this.nombre_places_reservees = nombre_places_reservees;
        this.date_reservation = date_reservation;
        this.montant_total = montant_total;
        this.statut = statut;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public int getNombre_places_reservees() {
        return nombre_places_reservees;
    }

    public void setNombre_places_reservees(int nombre_places_reservees) {
        this.nombre_places_reservees = nombre_places_reservees;
    }

    public LocalDate getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(LocalDate date_reservation) {
        this.date_reservation = date_reservation;
    }

    public double getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(double montant_total) {
        this.montant_total = montant_total;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
