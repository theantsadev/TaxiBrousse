package mg.taxi_brousse.app.repository;

import mg.taxi_brousse.app.dto.VoyageDetailDTO;
import mg.taxi_brousse.app.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {
    @Query(value = "SELECT new mg.taxi_brousse.app.dto.VoyageDetailDTO(" +
            "CAST(v.id_voyage AS integer), " +
            "gd.nom, gd.ville, " +
            "ga.nom, ga.ville, " +
            "v.date_depart, v.heure_depart, " +
            "vo.nom, vo.type, " +
            "CAST(vo.capacite AS integer), " +
            "CAST(COALESCE(vo.capacite - COALESCE(SUM(CASE WHEN r.statut != 'annule' THEN r.nombre_places_reservees ELSE 0 END), 0), vo.capacite) AS integer), "
            +
            "CAST(COALESCE(v.tarif_voyage, t.tarif_defaut) AS double), " +
            "v.statut) " +
            "FROM Voyage v " +
            "JOIN v.trajet t " +
            "JOIN t.gare_depart gd " +
            "JOIN t.gare_arrivee ga " +
            "JOIN v.voiture vo " +
            "LEFT JOIN Reservation r ON r.voyage = v " +
            "WHERE v.statut = 'prevu' " +
            "GROUP BY v.id_voyage, gd.nom, gd.ville, ga.nom, ga.ville, v.date_depart, v.heure_depart, vo.nom, vo.type, vo.capacite, v.tarif_voyage, t.tarif_defaut, v.statut "
            +
            "HAVING COALESCE(vo.capacite - COALESCE(SUM(CASE WHEN r.statut != 'annule' THEN r.nombre_places_reservees ELSE 0 END), 0), vo.capacite) > 0")
    List<VoyageDetailDTO> findAllVoyagesDispo();

    List<Voyage> findByStatut(String statut);
}
