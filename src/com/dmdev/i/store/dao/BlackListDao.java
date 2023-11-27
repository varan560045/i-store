package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.BlackListEntity;
import com.dmdev.i.store.entity.ClientEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class BlackListDao {

    public static final BlackListDao INSTANCE = new BlackListDao();
    public static final String DELETE_SQL = """
            DELETE FROM black_list
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO black_list(client_id, state) 
            VALUES (?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE black_list
            SET client_id = ?,
                state = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    client_id, 
                    state
            FROM black_list
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    client_id, 
                    state
            FROM black_list
            WHERE id = ?
            """;

    private BlackListDao(){
    }


    public List<BlackListEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BlackListEntity> blockedClients = new ArrayList<>();
            while (resultSet.next()){
                blockedClients.add(buildBlackList(resultSet));
            }
            return blockedClients;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<BlackListEntity> findById(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            BlackListEntity blackListEntity = null;
            if (resultSet.next()){
                blackListEntity = buildBlackList(resultSet);
            }

            return Optional.ofNullable(blackListEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static BlackListEntity buildBlackList(ResultSet resultSet) throws SQLException {
        return new BlackListEntity(
                resultSet.getLong("id"),
                resultSet.getLong("client_id"),
                resultSet.getString("state")
        );
    }

    public void update(BlackListEntity blackListEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, blackListEntity.getClientId());
            preparedStatement.setString(2, blackListEntity.getState());
            preparedStatement.setLong(3, blackListEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public BlackListEntity save(BlackListEntity blackListEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, blackListEntity.getClientId());
            preparedStatement.setString(2, blackListEntity.getState());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                blackListEntity.setId(generatedKeys.getLong("id"));
            }
            return blackListEntity;
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

    public static BlackListDao getInstance(){
        return INSTANCE;
    }
}
