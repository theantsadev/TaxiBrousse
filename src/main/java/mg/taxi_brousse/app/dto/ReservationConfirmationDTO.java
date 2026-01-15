package mg.taxi_brousse.app.dto;

public class ReservationConfirmationDTO {
    private int id_reservation;
    private String numero_reservation;
    private String client_nom;
    private String client_prenom;
    private String client_contact;
    private String trajet;
    private String date_depart;
    private String heure_depart;
    private int nombre_places;
    private double montant_total;
    private String statut;

    public ReservationConfirmationDTO() {
    }

    public ReservationConfirmationDTO(int id_reservation, String numero_reservation, String client_nom,
            String client_prenom, String client_contact, String trajet,
            String date_depart, String heure_depart, int nombre_places,
            double montant_total, String statut) {
        this.id_reservation = id_reservation;
        this.numero_reservation = numero_reservation;
        this.client_nom = client_nom;
        this.client_prenom = client_prenom;
        this.client_contact = client_contact;
        this.trajet = trajet;
        this.date_depart = date_depart;
        this.heure_depart = heure_depart;
        this.nombre_places = nombre_places;
        this.montant_total = montant_total;
        this.statut = statut;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public String getNumero_reservation() {
        return numero_reservation;
    }

    public void setNumero_reservation(String numero_reservation) {
        this.numero_reservation = numero_reservation;
    }

    public String getClient_nom() {
        return client_nom;
    }

    public void setClient_nom(String client_nom) {
        this.client_nom = client_nom;
    }

    public String getClient_prenom() {
        return client_prenom;
    }

    public void setClient_prenom(String client_prenom) {
        this.client_prenom = client_prenom;
    }

    public String getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(String client_contact) {
        this.client_contact = client_contact;
    }

    public String getTrajet() {
        return trajet;
    }

    public void setTrajet(String trajet) {
        this.trajet = trajet;
    }

    public String getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(String date_depart) {
        this.date_depart = date_depart;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public int getNombre_places() {
        return nombre_places;
    }

    public void setNombre_places(int nombre_places) {
        this.nombre_places = nombre_places;
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
