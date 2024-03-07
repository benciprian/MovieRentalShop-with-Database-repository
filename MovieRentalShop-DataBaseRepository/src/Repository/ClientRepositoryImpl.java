package Repository;

import Domain.Client;
import Domain.Movie;
import Domain.Validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements Repository<Long, Client>{
    private Map<Long, Client> entities;
    public ClientRepositoryImpl() {
        this.entities = new HashMap<>();
    }

    @Override
    public Optional<Client> findOne(Long id) {
        if (id == null) {throw new RuntimeException("not yet implemented");}

        //return entities.get(id);
        return Optional.empty();
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return clients;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        //return entities.putIfAbsent(entity.getId(), entity);
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }


    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return entities.containsKey(entity.getId()) ? Optional.empty() : Optional.of(entities.put(entity.getId(), entity));
    }

}

