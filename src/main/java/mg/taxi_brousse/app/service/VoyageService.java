package mg.taxi_brousse.app.service;

import mg.taxi_brousse.app.model.Voyage;
import mg.taxi_brousse.app.dto.VoyageDetailDTO;
import mg.taxi_brousse.app.repository.VoyageRepository;
import mg.taxi_brousse.app.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<VoyageDetailDTO> getAllVoyagesDispo() {
        return voyageRepository.findAllVoyagesDispo();
    }

    public Optional<Voyage> getVoyageById(int id_voyage) {
        return voyageRepository.findById(id_voyage);
    }

    public int getPlacesDisponibles(int id_voyage) {
        Optional<Voyage> voyage = voyageRepository.findById(id_voyage);
        if (voyage.isEmpty()) {
            return 0;
        }
        int capacite = voyage.get().getVoiture().getCapacite();
        int reservees = reservationRepository.countReservedPlaces(id_voyage);
        return capacite - reservees;
    }

    public boolean verifierDisponibilite(int id_voyage, int nb_places) {
        return getPlacesDisponibles(id_voyage) >= nb_places;
    }

    public Voyage saveVoyage(Voyage voyage) {
        return voyageRepository.save(voyage);
    }
}
