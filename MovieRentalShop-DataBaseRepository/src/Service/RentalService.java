package Service;

import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import  Repository.PostgresRentalRepository;

public class RentalService {
    private Repository<Long, Rental> rentalRepository;


    public RentalService(Repository<Long, Rental> rentalRepository) {
        this.rentalRepository = rentalRepository;

    }

    public void addRental(Rental rental) throws ValidatorException {
        rentalRepository.save(rental);
    }

    public Set<Rental> getAllRentals() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        return StreamSupport.stream(rentals.spliterator(), false).collect(Collectors.toSet());
    }

    public void updateRental(Rental rental) throws ValidatorException {
        rentalRepository.update(rental);
    }

    public void deleteRental(Long id) throws ValidatorException {
        rentalRepository.delete(id);
    }

    // Additional service methods can be implemented here
    // For example, methods for generating reports or complex queries involving rentals

    public List<Movie> getAllMoviesRentedByClient(Long clientId) {
        // Cast the generic Repository to PostgresRentalRepository to access the specific method
        if (rentalRepository instanceof PostgresRentalRepository) {
            PostgresRentalRepository rentalRepo = (PostgresRentalRepository) rentalRepository;
            return rentalRepo.findAllMoviesRentedByClient(clientId);
        }
        return new ArrayList<>(); // return an empty list if the cast is not successful
    }

    // Method to find all clients who rented a given movie
    public List<Client> findAllClientsWhoRentedMovie(Long movieId) {
        // Cast the generic Repository to PostgresRentalRepository to access the specific method
        if (rentalRepository instanceof PostgresRentalRepository) {
            PostgresRentalRepository rentalRepo = (PostgresRentalRepository) rentalRepository;
            return rentalRepo.findAllClientsWhoRentedMovie(movieId);
        }
        return new ArrayList<>(); // return an empty list if the cast is not successful
    }

    // Method to print movies rented within a date range
    public void printMoviesRentedInInterval(LocalDateTime startDate, LocalDateTime endDate) {
        StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                .filter(rental -> rental.getRentalDate().isAfter(startDate) && rental.getRentalDate().isBefore(endDate))
                .filter(Objects::nonNull)
                .distinct()
                .forEach(System.out::println);
    }

}

