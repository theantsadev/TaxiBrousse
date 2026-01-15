package mg.taxi_brousse.app.service;

import mg.taxi_brousse.app.model.Paiement;
import mg.taxi_brousse.app.model.Reservation;
import mg.taxi_brousse.app.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    public Paiement creerPaiement(Reservation reservation, double montant, String mode_paiement, String reference) {
        Paiement paiement = new Paiement(
            reservation,
            montant,
            mode_paiement,
            LocalDate.now(),
            reference
        );
        return paiementRepository.save(paiement);
    }

    public Optional<Paiement> getPaiementById(int id) {
        return paiementRepository.findById(id);
    }

    public Paiement savePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }
}
