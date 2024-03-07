package Repository;

import Domain.Client;
import Domain.Validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresClientRepository implements Repository<Long,Client> {

    private final String url = "jdbc:postgresql://localhost:5432/MovieRental";
    private final String username = "postgres";
    private final String password = "admin";

    @Override
    public Optional<Client> findOne(Long id) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM clients WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("city")
                );
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM clients";
            PreparedStatement statement = conn.prepareStatement(sql);
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

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO clients (firstName, lastName, phoneNumber,email, city) VALUES (?, ?, ?, ?,?) RETURNING id";


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhoneNumber());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getCity());

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
    public Optional<Client> delete(Long id) {
        Optional<Client> existingClient = findOne(id);
        if (existingClient.isPresent()) {
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "DELETE FROM clients WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existingClient;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE clients SET firstName = ?, lastName = ?, phoneNumber = ?, email = ?, city=? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhoneNumber());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getCity());
            statement.setLong(6, entity.getId());


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


