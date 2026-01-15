package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Integer> {
}
