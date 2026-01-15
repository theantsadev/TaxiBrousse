package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.GareRoutiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GareRoutiereRepository extends JpaRepository<GareRoutiere, Integer> {
}
