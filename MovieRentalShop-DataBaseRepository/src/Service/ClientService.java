package Service;

import Domain.Movie;
import Domain.Client;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService{
    private Repository<Long, Client> repository;

    public ClientService(Repository<Long, Client> repository) {
        this.repository = repository;
    }
    public void addClient(Client client) throws ValidatorException {
        repository.save(client);
    }

    public Set<Client> getAllClients() {
        Iterable<Client> clients = repository.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }
    public void updateClient(Client client) throws ValidatorException {
        repository.update(client);
    }
    public void deleteClient(Long id) throws ValidatorException {
        repository.delete(id);
    }
}

