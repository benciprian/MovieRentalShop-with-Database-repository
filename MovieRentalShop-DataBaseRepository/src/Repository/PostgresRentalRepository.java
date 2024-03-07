package Repository;

import Domain.Client;
import Domain.Movie;
import Domain.Rental;
import Domain.Validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresRentalRepository implements Repository<Long, Rental> {
    private final String url = "jdbc:postgresql://localhost:5432/MovieRental";
    private final String username = "postgres";
    private final String password = "admin";

    @Override
    public Optional<Rental> findOne(Long id) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM rentals WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Rental rental = new Rental(
                        resultSet.getLong("id"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("movie_id"),
                        resultSet.getTimestamp("rental_date").toLocalDateTime(),
                        resultSet.getTimestamp("return_date").toLocalDateTime()

                );
                return Optional.of(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Rental> findAll() {
        List<Rental> rentals = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM rentals";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Rental rental = new Rental(
                        resultSet.getLong("id"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("movie_id"),
                        resultSet.getTimestamp("rental_date").toLocalDateTime(),
                        resultSet.getTimestamp("return_date").toLocalDateTime()
                );
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentals;
    }

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            //String sql = "INSERT INTO rentals (client_id, movie_id, rental_date, return_date) VALUES (?, ?) RETURNING id";
            String sql = "INSERT INTO rentals (client_id, movie_id, rental_date, return_date) VALUES (?, ?, ?, ?) RETURNING id";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, entity.getClientId());
            statement.setLong(2, entity.getMovieId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getRentalDate()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.getReturnDate()));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                entity.setId(id);
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Rental> delete(Long id) {
        Optional<Rental> existingRental = findOne(id);
        if (existingRental.isPresent()) {
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "DELETE FROM rentals WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existingRental;
    }

    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE rentals SET client_id = ?, movie_id = ?, rental_date = ?, return_date = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, entity.getClientId());
            statement.setLong(2, entity.getMovieId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getRentalDate()));
            statement.setTimestamp(4, Timestamp.valueOf(entity.getReturnDate()));
            statement.setLong(5, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    public List<Movie> findAllMoviesRentedByClient(Long clientId) {
        List<Movie> rentedMovies = new ArrayList<>();
        String sql = "SELECT m.* FROM movies m " +
                "INNER JOIN rentals r ON m.id = r.movie_id " +
                "WHERE r.client_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("year"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("rentalPrice")
                );
                rentedMovies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentedMovies;
    }

    public List<Client> findAllClientsWhoRentedMovie(Long movieId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT c.* FROM clients c " +
                "INNER JOIN rentals r ON c.id = r.client_id " +
                "WHERE r.movie_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setLong(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("city")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }


}

