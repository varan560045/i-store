package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.CategoryEntity;
import com.dmdev.i.store.entity.PurchaseStatusEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PurchaseStatusDao {



    public static final PurchaseStatusDao INSTANCE = new PurchaseStatusDao();
    public static final String DELETE_SQL = """
            DELETE FROM purchase_status
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO purchase_status(status) 
            VALUES (?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE purchase_status
            SET status = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    status
            FROM purchase_status
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    status
            FROM purchase_status
            WHERE id = ?
            """;

    private PurchaseStatusDao(){
    }


    public List<PurchaseStatusEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PurchaseStatusEntity> purchaseStatusEntities = new ArrayList<>();
            while (resultSet.next()){
                purchaseStatusEntities.add(buildStatus(resultSet));
            }
            return purchaseStatusEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<PurchaseStatusEntity> findById(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            PurchaseStatusEntity purchaseStatusEntity = null;
            if (resultSet.next()){
                purchaseStatusEntity = buildStatus(resultSet);
            }

            return Optional.ofNullable(purchaseStatusEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static PurchaseStatusEntity buildStatus(ResultSet resultSet) throws SQLException {
        return new PurchaseStatusEntity(
                resultSet.getInt("id"),
                resultSet.getString("status")
        );
    }

    public void update(PurchaseStatusEntity purchaseStatusEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, purchaseStatusEntity.getStatus());
            preparedStatement.setInt(2, purchaseStatusEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public PurchaseStatusEntity save(PurchaseStatusEntity purchaseStatusEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, purchaseStatusEntity.getStatus());


            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                purchaseStatusEntity.setId(generatedKeys.getInt("id"));
            }
            return purchaseStatusEntity;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static PurchaseStatusDao getInstance(){
        return INSTANCE;
    }
}
