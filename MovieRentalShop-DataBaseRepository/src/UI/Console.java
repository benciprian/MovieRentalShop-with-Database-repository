package UI;

import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Domain.Validators.ValidatorException;
import Service.ClientService;
import Service.MovieService;
import Service.RentalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Console {

    private MovieService movieService;
    private ClientService clientService;
    private RentalService rentalService;

    private Scanner scanner;

    public Console(MovieService movieService,ClientService clientService, RentalService rentalService) {
        this.movieService = movieService;
        this.clientService = clientService;
        this.rentalService = rentalService;
        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("1. Movie rent CRUD");
        System.out.println("2. Client CRUD");
        System.out.println("3. Rental CRUD");
        System.out.println("0. Exit");
    }

    public void run() throws ValidatorException {
        while (true) {
            showMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    runMovieCrud();
                    break;
                case "2":
                    runClientCrud();
                    break;
                case "3":
                    runRentalCrud();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runMovieCrud() {
        while (true) {
            System.out.println("1. Add movie rental");
            System.out.println("2. View all movies rental");
            System.out.println("3. Update movie rental");
            System.out.println("4. Remove movies rental");
            System.out.println("9. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addMovieRental();
                    break;
                case "2":
                    printMovies();
                    break;
                case "3":
                    updateMovieRental();
                    break;
                case "4":
                    deleteMovieRental();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runClientCrud() throws ValidatorException {
        while (true) {
            System.out.println("1. Add client");
            System.out.println("2. View all clients");
            System.out.println("3. Update client");
            System.out.println("4. Remove client");
            System.out.println("9. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addClient();
                    break;
                case "2":
                    printAllClients();
                    break;
                case "3":
                    updateClient();
                    break;
                case "4":
                    deleteClient();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runRentalCrud() throws ValidatorException {
        while (true) {
            System.out.println("1. Add rental");
            System.out.println("2. View all rentals");
            System.out.println("3. Update rental");
            System.out.println("4. Remove rental");
            System.out.println("5. View all movies rented by a client");
            System.out.println("6. View all clients who rented a movie");
            System.out.println("7. View all movies rented in a date range");
            System.out.println("9. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addRental();
                    break;
                case "2":
                    printRentals();
                    break;
                case "3":
                    updateRental();
                    break;
                case "4":
                    deleteRental();
                    break;
                case "5":
                    viewMoviesRentedByClient();
                    break;
                case "6":
                    findAllClientsWhoRentedMovie();
                    break;
                case "7":
                    printMoviesInDateRange();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    // Movie CRUD

    //add movie method
    private void addMovieRental() {
        Movie movie = readMovie();
        if (movie != null) {
            try {
                movieService.addMovie(movie);
                System.out.println("Movie added successfully.");
            } catch (ValidatorException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private Movie readMovie() {
        System.out.println("Enter movie details:");
        Long id = readLong("Enter ID: ");
        String title = readString("Enter title: ");
        int year = readInt("Enter year: ");
        String genre = readString("Enter genre: ");
        double rentalPrice = readDouble("Enter rental price: ");

        Movie movie = new Movie(id, title, year, genre, rentalPrice);
        return movie;
    }

    //auxiliary methods for reading data from the console
    private Long readLong(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readLong(message);
        }
    }

    private int readInt(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(bufferedReader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readInt(message);
        }
    }

    private double readDouble(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return Double.parseDouble(bufferedReader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readDouble(message);
        }
    }

    private String readString(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Invalid input. Please enter a valid string.");
            return readString(message);
        }
    }

    //display all movies
    private void printMovies() {
        System.out.println("List of Movies:");
        for (Movie movie : movieService.getAllMovies()) {
            System.out.println(movie);
        }
    }


    //update movie method
    private void updateMovieRental() {
        Long id = readLong("Enter the ID of the movie you want to update: ");
        Movie existingMovie = movieService.getAllMovies()
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingMovie == null) {
            System.out.println("Movie not found.");
            return;
        }

        Movie updatedMovie = readMovie();
        updatedMovie.setId(id);

        try {
            movieService.updateMovie(updatedMovie);
            System.out.println("Movie updated successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //delete movie method
    private void deleteMovieRental() {
        Long id = readLong("Enter the ID of the movie you want to delete: ");
        Movie existingMovie = movieService.getAllMovies()
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingMovie == null) {
            System.out.println("Movie not found.");
            return;
        }

        try {
            movieService.deleteMovie(id);
            System.out.println("Movie deleted successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Client CRUD

    //add client method
    private void addClient() {
        Client client = readClient();
        if (client != null) {
            try {
                clientService.addClient(client);
                System.out.println("Client added successfully.");
            } catch (ValidatorException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private Client readClient() {
        System.out.println("Enter client details:");
        Long id = readLong("Enter ID: ");
        String firstName = readString("Enter first name: ");
        String lastName = readString("Enter last name: ");
        String email = readString("Enter email: ");
        String phoneNumber = readString("Enter phone number: ");
        String city = readString("Enter city: ");

        Client client = new Client(id, firstName, lastName, email, phoneNumber, city);
        return client;
    }

  //display all clients
    private void printAllClients() {
        System.out.println("List of Clients:");
        for (Client client : clientService.getAllClients()) {
            System.out.println(client);
        }
    }

    //update client method
    private void updateClient() {
        Long id = readLong("Enter the ID of the client you want to update: ");
        Client existingClient = clientService.getAllClients()
                .stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingClient == null) {
            System.out.println("Client not found.");
            return;
        }

        Client updatedClient = readClient();
        updatedClient.setId(id);

        try {
            clientService.updateClient(updatedClient);
            System.out.println("Client updated successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

  //delete client method
    private void deleteClient() throws ValidatorException {
        Long id = readLong("Enter the ID of the client you want to delete: ");
        Client existingClient = clientService.getAllClients()
                .stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingClient == null) {
            System.out.println("Client not found.");
            return;
        }
        try {
            clientService.deleteClient(id);
            System.out.println("Client deleted successfully.");

        }
        catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    // Rental CRUD

    //add rental method

    private void addRental() {
        Rental rental = readRental();
        if (rental != null) {
            try {
                rentalService.addRental(rental);
                System.out.println("Rental added successfully.");
            } catch (ValidatorException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Rental readRental() {
        System.out.println("Enter rental details:");
        Long id = readLong("Enter rental ID: ");
        Long clientId = readLong("Enter client ID: ");
        Long movieId = readLong("Enter movie ID: ");
        LocalDateTime rentalDate = readDateTime("Enter rental date (dd.MM.yyyy HH:mm): ");
        LocalDateTime returnDate = readDateTime("Enter return date (dd.MM.yyyy HH:mm): ");

        return new Rental(id, clientId, movieId, rentalDate, returnDate);
    }

    private LocalDateTime readDateTime(String message) {
        while (true) {
            try {
                System.out.print(message);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                return LocalDateTime.parse(bufferedReader.readLine(), formatter);
            } catch (IOException | DateTimeParseException e) {
                System.out.println("Invalid input. Please enter a valid date and time in the format dd.MM.yyyy HH:mm.");
            }
        }
    }


    //display all rentals
    private void printRentals() {
        System.out.println("List of Rentals:");
        for (Rental rental : rentalService.getAllRentals()) {
            System.out.println(rental);
        }
    }

    //update rental method
    private void updateRental() {
        Long id = readLong("Enter the ID of the rental you want to update: ");
        Rental existingRental = rentalService.getAllRentals()
                .stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingRental == null) {
            System.out.println("Rental not found.");
            return;
        }

        Rental updatedRental = readRental();
        updatedRental.setId(id);

        try {
            rentalService.updateRental(updatedRental);
            System.out.println("Rental updated successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


   //delete rental method
    private void deleteRental() {
        Long id = readLong("Enter the ID of the rental you want to delete: ");
        Rental existingRental = rentalService.getAllRentals()
                .stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingRental == null) {
            System.out.println("Rental not found.");
            return;
        }

        try {
            rentalService.deleteRental(id);
            System.out.println("Rental deleted successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Additional methods for complex queries and reports

    //method to find all movies rented by a client
    private void viewMoviesRentedByClient() {
        Long clientId = readLong("Enter client ID: ");
        List<Movie> rentedMovies = rentalService.getAllMoviesRentedByClient(clientId);
        if (rentedMovies.isEmpty()) {
            System.out.println("No movies found for this client.");
        } else {
            System.out.println("Movies rented by Client ID " + clientId + ":");
            for (Movie movie : rentedMovies) {
                System.out.println(movie);
            }
        }
    }

    //method to find all clients who rented a movie
    private void findAllClientsWhoRentedMovie() {
        Long movieId = readLong("Enter the ID of the movie: ");
        List<Client> clients = rentalService.findAllClientsWhoRentedMovie(movieId);

        if (clients.isEmpty()) {
            System.out.println("No clients found for this movie.");
        } else {
            System.out.println("Clients who rented the movie ID " + movieId + ":");
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

   //method to print movies rented within a date range
    private void printMoviesInDateRange() {
        LocalDateTime startDate = readDateTime("Enter start date (dd.MM.yyyy HH:mm): ");
        LocalDateTime endDate = readDateTime("Enter end date (dd.MM.yyyy HH:mm): ");
        rentalService.printMoviesRentedInInterval(startDate, endDate);
    }


}

