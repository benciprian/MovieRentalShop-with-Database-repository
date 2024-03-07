package Repository;

import Domain.Movie;
import Domain.Validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieRepositoryImpl implements Repository<Long, Movie> {
    private Map<Long, Movie> entities;
    public MovieRepositoryImpl() {
        this.entities = new HashMap<>();
    }

    @Override
    public Optional<Movie> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }


    @Override
    public Iterable<Movie> findAll() {
        Set<Movie> movies = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return movies;
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return entities.putIfAbsent(entity.getId(), entity) == null ? Optional.empty() : Optional.of(entity);
    }


    @Override
    public Optional<Movie> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }


    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return entities.containsKey(entity.getId()) ? Optional.empty() : Optional.of(entities.put(entity.getId(), entity));
    }


}
