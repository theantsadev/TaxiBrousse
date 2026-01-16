package mg.taxi_brousse.app.controller;

import mg.taxi_brousse.app.model.ConfigPlaceVoyage;
import mg.taxi_brousse.app.model.Voyage;
import mg.taxi_brousse.app.model.TypePlace;
import mg.taxi_brousse.app.service.ConfigPlaceVoyageService;
import mg.taxi_brousse.app.service.VoyageService;
import mg.taxi_brousse.app.service.TypePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/config-places")
public class ConfigPlaceVoyageController {

    @Autowired
    private ConfigPlaceVoyageService configPlaceVoyageService;

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping
    public String listConfigs(Model model) {
        List<Voyage> voyages = voyageService.getAllVoyages();
        model.addAttribute("voyages", voyages);
        model.addAttribute("pageTitle", "Configuration Places");
        model.addAttribute("activeMenu", "config");
        return "admin/config-places-layout";
    }

    @GetMapping("/voyage/{id}")
    public String configsByVoyage(@PathVariable int id, Model model) {
        Optional<Voyage> voyage = voyageService.getVoyageById(id);
        if (voyage.isEmpty()) {
            return "redirect:/admin/config-places";
        }

        List<ConfigPlaceVoyage> configs = configPlaceVoyageService.getConfigsByVoyage(id);
        List<TypePlace> allTypes = typePlaceService.getTousLesTypes();
        List<Integer> usedTypes = configs.stream()
                .map(c -> c.getTypePlace().getId_type_place())
                .collect(Collectors.toList());

        // Calculate total places
        int totalPlaces = configs.stream()
                .mapToInt(ConfigPlaceVoyage::getNombre)
                .sum();
        int remainingPlaces = voyage.get().getVoiture().getCapacite() - totalPlaces;

        model.addAttribute("voyage", voyage.get());
        model.addAttribute("configs", configs);
        model.addAttribute("recetteMaxTheorique", configPlaceVoyageService.getRecetteMaxTheorique(configs));
        model.addAttribute("totalPlaces", totalPlaces);
        model.addAttribute("remainingPlaces", remainingPlaces);
        model.addAttribute("pageTitle", "Configuration Voyage");
        model.addAttribute("activeMenu", "config");
        model.addAttribute("availableTypes", allTypes.stream()
                .filter(t -> !usedTypes.contains(t.getId_type_place()))
                .collect(Collectors.toList()));

        return "admin/config-place-detail-layout";
    }

    @PostMapping("/creer")
    public String creerConfig(
            @RequestParam int id_voyage,
            @RequestParam int id_type_place,
            @RequestParam int nombre,
            @RequestParam double prix,
            Model model) {
        try {
            Optional<Voyage> voyage = voyageService.getVoyageById(id_voyage);
            Optional<TypePlace> typePlace = typePlaceService.getTypePlaceById(id_type_place);

            if (voyage.isEmpty() || typePlace.isEmpty()) {
                model.addAttribute("erreur", "Donnees invalides");
                return "redirect:/admin/config-places/voyage/" + id_voyage;
            }

            configPlaceVoyageService.creerConfig(voyage.get(), typePlace.get(), nombre, prix);
            model.addAttribute("message", "Configuration creee avec succes");
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur: " + e.getMessage());
        }
        return "redirect:/admin/config-places/voyage/" + id_voyage;
    }

    @PostMapping("/modifier/{id}")
    public String modifierConfig(
            @PathVariable int id,
            @RequestParam int nombre,
            @RequestParam double prix) {
        try {
            configPlaceVoyageService.updateConfig(id, nombre, prix);
        } catch (Exception e) {
            // Gestion de l'erreur
        }
        return "redirect:/admin/config-places";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerConfig(@PathVariable int id) {
        try {
            configPlaceVoyageService.supprimerConfig(id);
        } catch (Exception e) {
            // Gestion de l'erreur
        }
        return "redirect:/admin/config-places";
    }
}
