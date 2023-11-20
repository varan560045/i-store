package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.AdmissionEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class AdmissionDao {

    public static final AdmissionDao INSTANCE = new AdmissionDao();
    public static final String DELETE_SQL = """
            DELETE FROM admission
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO admission(id, status, permissions) 
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE admission
            SET id = ?,
                status = ?,
                permissions = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    status, 
                    permissions
            FROM admission
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id,
                    status, 
                    permissions
            FROM admission
            WHERE id = ?
            """;

    private AdmissionDao(){
    }



    public List<AdmissionEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AdmissionEntity> admissions = new ArrayList<>();
            while (resultSet.next()){
                admissions.add(buildAdmission(resultSet));
            }
            return admissions;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<AdmissionEntity> findById(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            AdmissionEntity admissions = null;
            if (resultSet.next()){
                admissions = buildAdmission(resultSet);
            }

            return Optional.ofNullable(admissions);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static AdmissionEntity buildAdmission(ResultSet resultSet) throws SQLException {
        return new AdmissionEntity(
                resultSet.getInt("id"),
                resultSet.getString("status"),
                resultSet.getString("permissions")
        );
    }

    public void update(AdmissionEntity admission){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, admission.getId());
            preparedStatement.setString(2, admission.getStatus());
            preparedStatement.setString(3, admission.getPermissions());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public AdmissionEntity save(AdmissionEntity admission){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, admission.getId());
            preparedStatement.setString(2, admission.getStatus());
            preparedStatement.setString(3, admission.getPermissions());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                admission.setId(generatedKeys.getInt("id"));
            }
            return admission;
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

    public static AdmissionDao getInstance(){
        return INSTANCE;
    }
}
