package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.ConfigPlaceVoyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigPlaceVoyageRepository extends JpaRepository<ConfigPlaceVoyage, Integer> {
    
    @Query("SELECT c FROM ConfigPlaceVoyage c WHERE c.voyage.id_voyage = :idVoyage")
    List<ConfigPlaceVoyage> findByVoyageIdVoyage(@Param("idVoyage") int idVoyage);
    
    @Query("SELECT c FROM ConfigPlaceVoyage c WHERE c.voyage.id_voyage = :idVoyage AND c.typePlace.id_type_place = :idTypePlace")
    Optional<ConfigPlaceVoyage> findByVoyageIdVoyageAndTypePlaceIdTypePlace(@Param("idVoyage") int idVoyage, @Param("idTypePlace") int idTypePlace);
}
