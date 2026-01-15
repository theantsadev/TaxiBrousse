package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT COALESCE(SUM(r.nombre_places_reservees), 0) FROM Reservation r WHERE r.voyage.id_voyage = :voyageId AND r.statut != 'annulé'")
    int countReservedPlaces(@Param("voyageId") int voyageId);

    List<Reservation> findByStatut(String statut);
}
