package Repository;

import Domain.BaseEntity;
import Domain.Movie;
import Domain.Validators.ValidatorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresMovieRepository implements Repository<Long, Movie> {
    private final String url = "jdbc:postgresql://localhost:5432/MovieRental";
    private final String username = "postgres";
    private final String password = "admin";

    @Override
    public Optional<Movie> findOne(Long id) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM movies WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("year"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("rentalPrice")
                );
                return Optional.of(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM movies";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("year"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("rentalPrice")
                );
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO movies (title, year, genre, rentalPrice) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getYear());
            statement.setString(3, entity.getGenre());
            statement.setDouble(4, entity.getRentalPrice());

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
    public Optional<Movie> delete(Long id) {
        Optional<Movie> existingMovie = findOne(id);
        if (existingMovie.isPresent()) {
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "DELETE FROM movies WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existingMovie;
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE movies SET title = ?, year = ?, genre = ?, rentalPrice = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getYear());
            statement.setString(3, entity.getGenre());
            statement.setDouble(4, entity.getRentalPrice());
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
}


