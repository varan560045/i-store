package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.CategoryEntity;
import com.dmdev.i.store.entity.ManufacturerEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerDao {



    public static final ManufacturerDao INSTANCE = new ManufacturerDao();
    public static final String DELETE_SQL = """
            DELETE FROM manufacturer
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO manufacturer(manufacturer_name) 
            VALUES (?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE manufacturer
            SET manufacturer_name = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    manufacturer_name
            FROM manufacturer
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    manufacturer_name
            FROM manufacturer
            WHERE id = ?
            """;

    private ManufacturerDao(){
    }


    public List<ManufacturerEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ManufacturerEntity> manufacturerEntities = new ArrayList<>();
            while (resultSet.next()){
                manufacturerEntities.add(buildManufacturer(resultSet));
            }
            return manufacturerEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<ManufacturerEntity> findById(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ManufacturerEntity manufacturerEntity = null;
            if (resultSet.next()){
                manufacturerEntity = buildManufacturer(resultSet);
            }

            return Optional.ofNullable(manufacturerEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static ManufacturerEntity buildManufacturer(ResultSet resultSet) throws SQLException {
        return new ManufacturerEntity(
                resultSet.getInt("id"),
                resultSet.getString("manufacturer_name")
        );
    }

    public void update(ManufacturerEntity manufacturerEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, manufacturerEntity.getManufacturerName());
            preparedStatement.setInt(2, manufacturerEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public ManufacturerEntity save(ManufacturerEntity manufacturerEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturerEntity.getManufacturerName());


            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                manufacturerEntity.setId(generatedKeys.getInt("id"));
            }
            return manufacturerEntity;
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

    public static ManufacturerDao getInstance(){
        return INSTANCE;
    }
}
