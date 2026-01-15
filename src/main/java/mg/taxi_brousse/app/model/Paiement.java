package mg.taxi_brousse.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_paiement;

    @ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    private double montant;
    private String mode_paiement;
    private LocalDate date_paiement;
    private String reference_transaction;

    public Paiement() {}

    public Paiement(Reservation reservation, double montant, String mode_paiement, LocalDate date_paiement, String reference_transaction) {
        this.reservation = reservation;
        this.montant = montant;
        this.mode_paiement = mode_paiement;
        this.date_paiement = date_paiement;
        this.reference_transaction = reference_transaction;
    }

    public int getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(int id_paiement) {
        this.id_paiement = id_paiement;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    public LocalDate getDate_paiement() {
        return date_paiement;
    }

    public void setDate_paiement(LocalDate date_paiement) {
        this.date_paiement = date_paiement;
    }

    public String getReference_transaction() {
        return reference_transaction;
    }

    public void setReference_transaction(String reference_transaction) {
        this.reference_transaction = reference_transaction;
    }
}
