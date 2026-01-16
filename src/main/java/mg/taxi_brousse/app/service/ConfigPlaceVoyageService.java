package mg.taxi_brousse.app.service;

import mg.taxi_brousse.app.model.ConfigPlaceVoyage;
import mg.taxi_brousse.app.model.Voyage;
import mg.taxi_brousse.app.model.TypePlace;
import mg.taxi_brousse.app.repository.ConfigPlaceVoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigPlaceVoyageService {
    
    @Autowired
    private ConfigPlaceVoyageRepository configPlaceVoyageRepository;

    public ConfigPlaceVoyage creerConfig(Voyage voyage, TypePlace typePlace, int nombre, double prix) {
        ConfigPlaceVoyage config = new ConfigPlaceVoyage(voyage, typePlace, nombre, prix);
        return configPlaceVoyageRepository.save(config);
    }

    public List<ConfigPlaceVoyage> getConfigsByVoyage(int idVoyage) {
        return configPlaceVoyageRepository.findByVoyage_Id_voyage(idVoyage);
    }

    public Optional<ConfigPlaceVoyage> getConfigByVoyageAndType(int idVoyage, int idTypePlace) {
        return configPlaceVoyageRepository.findByVoyage_Id_voyageAndTypePlace_Id_type_place(idVoyage, idTypePlace);
    }

    public void updateConfig(int id, int nombre, double prix) {
        Optional<ConfigPlaceVoyage> config = configPlaceVoyageRepository.findById(id);
        if (config.isPresent()) {
            ConfigPlaceVoyage c = config.get();
            c.setNombre(nombre);
            c.setPrix(prix);
            configPlaceVoyageRepository.save(c);
        }
    }

    public void supprimerConfig(int id) {
        configPlaceVoyageRepository.deleteById(id);
    }

    public int getTotalPlacesVoyage(int idVoyage) {
        List<ConfigPlaceVoyage> configs = getConfigsByVoyage(idVoyage);
        return configs.stream().mapToInt(ConfigPlaceVoyage::getNombre).sum();
    }
}
