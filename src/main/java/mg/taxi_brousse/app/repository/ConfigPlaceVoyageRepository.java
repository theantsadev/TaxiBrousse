package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.ConfigPlaceVoyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigPlaceVoyageRepository extends JpaRepository<ConfigPlaceVoyage, Integer> {
    List<ConfigPlaceVoyage> findByVoyage_Id_voyage(int idVoyage);
    Optional<ConfigPlaceVoyage> findByVoyage_Id_voyageAndTypePlace_Id_type_place(int idVoyage, int idTypePlace);
}
