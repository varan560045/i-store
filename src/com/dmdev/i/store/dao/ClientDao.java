package com.dmdev.i.store.dao;

import com.dmdev.i.store.dto.ClientEntityFilter;
import com.dmdev.i.store.entity.ClientEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class ClientDao {

    public static final ClientDao INSTANCE = new ClientDao();
    public static final String DELETE_SQL = """
            DELETE FROM client
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO client(f_name, l_name, email, admission_id) 
            VALUES (?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE client
            SET f_name = ?,
                l_name = ?,
                email = ?,
                admission_id = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    f_name, 
                    l_name, 
                    email, 
                    admission_id
            FROM client
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    f_name, 
                    l_name, 
                    email, 
                    admission_id
            FROM client
            WHERE id = ?
            """;

    private ClientDao(){
    }

    public List<ClientEntity> findAll(ClientEntityFilter filter){
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.email() != null){
            whereSql.add("email LIKE ?");
            parameters.add("%" + filter.email() + "%");
        }
        if (filter.fName() != null){
            whereSql.add("f_name = ?");
            parameters.add(filter.fName());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));

        String sql = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ClientEntity> clients = new ArrayList<>();
            while (resultSet.next()){
                clients.add(buildClient(resultSet));
            }
            return clients;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<ClientEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ClientEntity> clients = new ArrayList<>();
            while (resultSet.next()){
                clients.add(buildClient(resultSet));
            }
            return clients;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<ClientEntity> findById(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ClientEntity clientEntity = null;
            if (resultSet.next()){
                clientEntity = buildClient(resultSet);
            }

            return Optional.ofNullable(clientEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static ClientEntity buildClient(ResultSet resultSet) throws SQLException {
        return new ClientEntity(
                resultSet.getLong("id"),
                resultSet.getString("f_name"),
                resultSet.getString("l_name"),
                resultSet.getString("email"),
                resultSet.getInt("admission_id")
        );
    }

    public void update(ClientEntity client){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, client.getfName());
            preparedStatement.setString(2, client.getlName());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setInt(4, client.getAdmissionId());
            preparedStatement.setLong(5, client.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public ClientEntity save(ClientEntity client){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getfName());
            preparedStatement.setString(2, client.getlName());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setInt(4, client.getAdmissionId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                client.setId(generatedKeys.getLong("id"));
            }
            return client;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static ClientDao getInstance(){
        return INSTANCE;
    }
}
