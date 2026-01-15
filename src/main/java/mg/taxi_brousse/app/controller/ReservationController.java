package mg.taxi_brousse.app.controller;

import mg.taxi_brousse.app.dto.VoyageDetailDTO;
import mg.taxi_brousse.app.model.*;
import mg.taxi_brousse.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PaiementService paiementService;

    @GetMapping
    public String showSearchPage(Model model) {
        // Écran 1: Sélection des gares
        List<VoyageDetailDTO> voyages = voyageService.getAllVoyagesDispo();
        System.out.println(voyages.size());
        model.addAttribute("voyages", voyages);
        return "reservation/search";
    }

    @PostMapping("/confirmer")
    public String confirmReservation(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String contact,
            @RequestParam int id_voyage,
            @RequestParam int nombre_places,
            Model model) {

        try {
            // Vérifier les données
            Optional<Voyage> voyage = voyageService.getVoyageById(id_voyage);
            if (voyage.isEmpty()) {
                model.addAttribute("erreur", "Voyage introuvable");
                return showSearchPage(model);
            }

            // Créer ou récupérer le client
            Client client = clientService.creerOuRecupererClient(nom, prenom, contact);

            // Vérifier la disponibilité
            if (!voyageService.verifierDisponibilite(id_voyage, nombre_places)) {
                model.addAttribute("erreur", "Places insuffisantes");
                return showSearchPage(model);
            }

            // Calculer le montant
            double montant = reservationService.calculerMontantTotal(voyage.get(), nombre_places);

            // Créer la réservation
            Reservation reservation = reservationService.creerReservation(client, voyage.get(), nombre_places, montant);

            // Préparer les données pour la confirmation
            model.addAttribute("reservation", reservation);
            model.addAttribute("voyage", voyage.get());
            model.addAttribute("client", client);
            model.addAttribute("numero", "#RES-" + String.format("%06d", reservation.getId_reservation()));
            model.addAttribute("montant", montant);
            model.addAttribute("nombrePlaces", nombre_places);

            return "reservation/confirmation";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la réservation: " + e.getMessage());
            return showSearchPage(model);
        }
    }

    @PostMapping("/payer")
    public String paiement(
            @RequestParam int id_reservation,
            @RequestParam(required = false) String mode_paiement,
            Model model) {

        try {
            Optional<Reservation> reservation = reservationService.getReservationById(id_reservation);
            if (reservation.isEmpty()) {
                model.addAttribute("erreur", "Réservation introuvable");
                return "redirect:/reservation";
            }

            // Créer le paiement
            String reference = "PAY-" + System.currentTimeMillis();
            Paiement paiement = paiementService.creerPaiement(
                    reservation.get(),
                    reservation.get().getMontant_total(),
                    mode_paiement != null ? mode_paiement : "Espèces",
                    reference);

            // Mettre à jour le statut de la réservation
            reservation.get().setStatut("confirmé");
            reservationService.saveReservation(reservation.get());

            model.addAttribute("paiement", paiement);
            model.addAttribute("reservation", reservation.get());
            return "reservation/paiement-success";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors du paiement: " + e.getMessage());
            return "redirect:/reservation";
        }
    }

    @GetMapping("/list")
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }
}
