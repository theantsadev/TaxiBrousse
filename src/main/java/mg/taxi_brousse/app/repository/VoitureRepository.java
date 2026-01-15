package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
}
