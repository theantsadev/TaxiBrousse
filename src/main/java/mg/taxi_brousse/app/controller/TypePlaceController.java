package mg.taxi_brousse.app.controller;

import mg.taxi_brousse.app.model.TypePlace;
import mg.taxi_brousse.app.service.TypePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin/types-places")
public class TypePlaceController {

    @Autowired
    private TypePlaceService typePlaceService;

   

    @GetMapping
    public String listTypes(Model model) {
        List<TypePlace> types = typePlaceService.getTousLesTypes();
        model.addAttribute("types", types);
        model.addAttribute("pageTitle", "Types de Places");
        model.addAttribute("activeMenu", "types");
        return "admin/types-places";
    }

    @PostMapping("/creer")
    public String creerType(@RequestParam String nom, Model model) {
        try {
            typePlaceService.creerTypePlace(nom);
            model.addAttribute("message", "Type de place cree avec succes");
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de la creation: " + e.getMessage());
        }
        return "redirect:/admin/types-places";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerType(@PathVariable int id) {
        try {
            typePlaceService.supprimerTypePlace(id);
        } catch (Exception e) {
            // Gestion de l'erreur
        }
        return "redirect:/admin/types-places";
    }
}
