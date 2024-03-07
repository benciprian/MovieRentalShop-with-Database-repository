import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Domain.Validators.*;
import Repository.PostgresMovieRepository;
import Repository.PostgresClientRepository;
import Repository.PostgresRentalRepository;
import Repository.Repository;
import Service.ClientService;
import Service.MovieService;
import Service.RentalService;
import UI.Console;
import  Repository.MovieRepositoryImpl;
import  Repository.ClientRepositoryImpl;
import  Repository.RentalRepositoryImpl;

public class Main {
    public static void main(String[] args) throws ValidatorException {

        Repository<Long, Movie> movieRepository = new PostgresMovieRepository();
        Repository<Long, Client> clientRepository = new PostgresClientRepository();
        Repository<Long, Rental> rentalRepository = new PostgresRentalRepository();

        Repository<Long, Movie> movieMemoryRepository = new MovieRepositoryImpl();
        Repository<Long, Client> clientMemoryRepository = new ClientRepositoryImpl();
        Repository<Long,Rental>  rentalMemoryRepository = new RentalRepositoryImpl();

        Validator<Movie> movieValidator = new MovieValidator();
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Rental> rentalValidator = new RentalValidator();

        MovieService movieService = new MovieService(movieRepository);

        MovieService movieMemoryService = new MovieService(movieMemoryRepository);
        ClientService clientMemoryService = new ClientService(clientMemoryRepository);
        RentalService rentalMemoryService = new RentalService(rentalMemoryRepository);

        ClientService clientService = new ClientService(clientRepository);
        RentalService rentalService = new RentalService(rentalRepository);

        Console console = new Console(movieService,clientService,rentalService);
        Console consoleMemory = new Console(movieMemoryService,clientMemoryService,rentalService);

        //console.run();
        consoleMemory.run();


        System.out.println("bye");
    }
}
