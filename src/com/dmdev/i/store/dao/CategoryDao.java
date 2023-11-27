package com.dmdev.i.store.dao;

import com.dmdev.i.store.entity.CategoryEntity;
import com.dmdev.i.store.entity.ClientEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class CategoryDao {

    public static final CategoryDao INSTANCE = new CategoryDao();
    public static final String DELETE_SQL = """
            DELETE FROM category
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO category(category_name) 
            VALUES (?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE category
            SET category_name = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    category_name
            FROM category
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    category_name
            FROM category
            WHERE id = ?
            """;

    private CategoryDao(){
    }


    public List<CategoryEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CategoryEntity> categories = new ArrayList<>();
            while (resultSet.next()){
                categories.add(buildCategory(resultSet));
            }
            return categories;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<CategoryEntity> findById(Integer id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            CategoryEntity categories = null;
            if (resultSet.next()){
                categories = buildCategory(resultSet);
            }

            return Optional.ofNullable(categories);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static CategoryEntity buildCategory(ResultSet resultSet) throws SQLException {
        return new CategoryEntity(
                resultSet.getInt("id"),
                resultSet.getString("category_name")
        );
    }

    public void update(CategoryEntity categoryEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, categoryEntity.getCategoryName());
            preparedStatement.setInt(2, categoryEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public CategoryEntity save(CategoryEntity categoryEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, categoryEntity.getCategoryName());


            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                categoryEntity.setId(generatedKeys.getInt("id"));
            }
            return categoryEntity;
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

    public static CategoryDao getInstance(){
        return INSTANCE;
    }
}
