package mg.taxi_brousse.app.service;

import mg.taxi_brousse.app.model.Client;
import mg.taxi_brousse.app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client creerOuRecupererClient(String nom, String prenom, String contact) {
        Optional<Client> existingClient = clientRepository.findByContact(contact);
        if (existingClient.isPresent()) {
            return existingClient.get();
        }
        Client newClient = new Client(nom, prenom, contact);
        return clientRepository.save(newClient);
    }

    public Optional<Client> getClientByContact(String contact) {
        return clientRepository.findByContact(contact);
    }

    public Optional<Client> getClientById(int id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
}
