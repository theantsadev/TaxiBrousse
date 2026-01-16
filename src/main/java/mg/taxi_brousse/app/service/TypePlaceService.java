package mg.taxi_brousse.app.service;

import mg.taxi_brousse.app.model.TypePlace;
import mg.taxi_brousse.app.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypePlaceService {
    
    @Autowired
    private TypePlaceRepository typePlaceRepository;

    public TypePlace creerTypePlace(String nom) {
        Optional<TypePlace> existing = typePlaceRepository.findByNom(nom);
        if (existing.isPresent()) {
            return existing.get();
        }
        TypePlace typePlace = new TypePlace(nom);
        return typePlaceRepository.save(typePlace);
    }

    public List<TypePlace> getTousLesTypes() {
        return typePlaceRepository.findAll();
    }

    public Optional<TypePlace> getTypePlaceById(int id) {
        return typePlaceRepository.findById(id);
    }

    public Optional<TypePlace> getTypePlaceByNom(String nom) {
        return typePlaceRepository.findByNom(nom);
    }

    public void supprimerTypePlace(int id) {
        typePlaceRepository.deleteById(id);
    }
}
