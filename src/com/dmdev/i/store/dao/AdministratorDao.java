package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.AdministratorEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorDao {

    public static final AdministratorDao INSTANCE = new AdministratorDao();

    public static final String DELETE_SQL = """
            DELETE FROM administrator
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO administrator(purchase_id, actions, admission_id) 
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE administrator
            SET purchase_id = ?,
                actions = ?,
                admission_id = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    purchase_id, 
                    actions,  
                    admission_id
            FROM administrator
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    purchase_id, 
                    actions,  
                    admission_id
            FROM administrator
            WHERE id = ?
            """;

    private AdministratorDao(){
    }

    public List<AdministratorEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AdministratorEntity> administrators = new ArrayList<>();
            while (resultSet.next()){
                administrators.add(buildAdmin(resultSet));
            }
            return administrators;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<AdministratorEntity> findById(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            AdministratorEntity administrators = null;
            if (resultSet.next()){
                administrators = buildAdmin(resultSet);
            }

            return Optional.ofNullable(administrators);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static AdministratorEntity buildAdmin(ResultSet resultSet) throws SQLException {
        return new AdministratorEntity(
                resultSet.getInt("id"),
                resultSet.getLong("purchase_id"),
                resultSet.getString("actions"),
                resultSet.getInt("admission_id")
        );
    }

    public void update(AdministratorEntity administrator){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, administrator.getPurchaseId());
            preparedStatement.setString(2, administrator.getActions());
            preparedStatement.setInt(3, administrator.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public AdministratorEntity save(AdministratorEntity administrator){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, administrator.getPurchaseId());
            preparedStatement.setString(2, administrator.getActions());
            preparedStatement.setInt(3, administrator.getAdmissionId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                administrator.setId(generatedKeys.getInt("id"));
            }
            return administrator;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }
    public static AdministratorDao getInstance(){
        return INSTANCE;
    }
}
