package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.TypePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypePlaceRepository extends JpaRepository<TypePlace, Integer> {
    Optional<TypePlace> findByNom(String nom);
}
