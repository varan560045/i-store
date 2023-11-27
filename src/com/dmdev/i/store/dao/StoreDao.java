package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.PurchaseStatusEntity;
import com.dmdev.i.store.entity.StoreEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreDao {




    public static final StoreDao INSTANCE = new StoreDao();
    public static final String DELETE_SQL = """
            DELETE FROM store
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO store(store_name) 
            VALUES (?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE store
            SET store_name = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    store_name
            FROM store
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    store_name
            FROM store
            WHERE id = ?
            """;

    private StoreDao(){
    }


    public List<StoreEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<StoreEntity> storeEntities = new ArrayList<>();
            while (resultSet.next()){
                storeEntities.add(buildStore(resultSet));
            }
            return storeEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<StoreEntity> findById(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            StoreEntity storeEntity = null;
            if (resultSet.next()){
                storeEntity = buildStore(resultSet);
            }

            return Optional.ofNullable(storeEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static StoreEntity buildStore(ResultSet resultSet) throws SQLException {
        return new StoreEntity(
                resultSet.getInt("id"),
                resultSet.getString("store_name")
        );
    }

    public void update(StoreEntity storeEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, storeEntity.getStoreName());
            preparedStatement.setInt(2, storeEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public StoreEntity save(StoreEntity storeEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, storeEntity.getStoreName());


            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                storeEntity.setId(generatedKeys.getInt("id"));
            }
            return storeEntity;
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

    public static StoreDao getInstance(){
        return INSTANCE;
    }
}
