package Service;

import Domain.Movie;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {
    private Repository<Long, Movie> repository;

    public MovieService(Repository<Long, Movie> repository) {
        this.repository = repository;
    }
    public void addMovie(Movie movie) throws ValidatorException {
        repository.save(movie);
    }

    public Set<Movie> getAllMovies() {
        Iterable<Movie> movies = repository.findAll();
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
    }
    public void updateMovie(Movie movie) throws ValidatorException {
        repository.update(movie);
    }
    public void deleteMovie(Long id) throws ValidatorException {
        repository.delete(id);
    }
    public Movie getMovieById(Long id) {
        return repository.findOne(id).orElse(null);
    }

}
