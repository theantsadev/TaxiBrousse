package mg.taxi_brousse.app.service;

import mg.taxi_brousse.app.model.Reservation;
import mg.taxi_brousse.app.model.Voyage;
import mg.taxi_brousse.app.model.Client;
import mg.taxi_brousse.app.model.TypePlace;
import mg.taxi_brousse.app.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private ConfigPlaceVoyageService configPlaceVoyageService;

    public Reservation creerReservation(Client client, Voyage voyage, int nb_places, double montant) {
        if (!voyageService.verifierDisponibilite(voyage.getId_voyage(), nb_places)) {
            throw new RuntimeException("Places insuffisantes pour cette reservation");
        }
        
        Reservation reservation = new Reservation(
            client,
            voyage,
            null,
            nb_places,
            LocalDate.now(),
            montant,
            "en_attente_paiement"
        );
        return reservationRepository.save(reservation);
    }

    public Reservation creerReservationAvecType(Client client, Voyage voyage, TypePlace typePlace, int nb_places, double montant) {
        if (!voyageService.verifierDisponibilite(voyage.getId_voyage(), nb_places)) {
            throw new RuntimeException("Places insuffisantes pour cette reservation");
        }
        
        Reservation reservation = new Reservation(
            client,
            voyage,
            typePlace,
            nb_places,
            LocalDate.now(),
            montant,
            "en_attente_paiement"
        );
        return reservationRepository.save(reservation);
    }

    public double calculerMontantTotal(Voyage voyage, int nb_places) {
        return voyage.getTarif_voyage() * nb_places;
    }

    public double calculerMontantAvecType(int idVoyage, int idTypePlace, int nb_places) {
        Optional<mg.taxi_brousse.app.model.ConfigPlaceVoyage> config = 
            configPlaceVoyageService.getConfigByVoyageAndType(idVoyage, idTypePlace);
        if (config.isPresent()) {
            return config.get().getPrix() * nb_places;
        }
        return 0;
    }

    public void annulerReservation(int id_reservation) {
        Optional<Reservation> reservation = reservationRepository.findById(id_reservation);
        if (reservation.isPresent()) {
            reservation.get().setStatut("annule");
            reservationRepository.save(reservation.get());
        }
    }

    public Optional<Reservation> getReservationById(int id_reservation) {
        return reservationRepository.findById(id_reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
