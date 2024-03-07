package Repository;

import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Domain.Validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RentalRepositoryImpl implements Repository<Long, Rental> {
    private Map<Long, Rental> entities;
    public RentalRepositoryImpl() {
        this.entities = new HashMap<>();
    }
    @Override
    public Optional<Rental> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }


    @Override
    public Iterable<Rental> findAll() {
        Set<Rental> rental = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return rental;
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return entities.putIfAbsent(entity.getId(), entity) == null ? Optional.empty() : Optional.of(entity);
    }


    @Override
    public Optional<Rental> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }


    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return entities.containsKey(entity.getId()) ? Optional.empty() : Optional.of(entities.put(entity.getId(), entity));
    }


}
