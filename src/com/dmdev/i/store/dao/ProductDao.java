package com.dmdev.i.store.dao;

import com.dmdev.i.store.dto.ProductEntityFilter;
import com.dmdev.i.store.entity.ProductEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class ProductDao {



    public static final ProductDao INSTANCE = new ProductDao();
    public static final String DELETE_SQL = """
            DELETE FROM product
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO product(product_name, price, category_id, manufacturer_id, quantity) 
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE product
            SET product_name = ?,
                price = ?,
                category_id = ?,
                manufacturer_id = ?,
                quantity = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    product_name, 
                    price, 
                    category_id, 
                    manufacturer_id,
                    quantity
            FROM product
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    product_name, 
                    price, 
                    category_id, 
                    manufacturer_id,
                    quantity
            FROM product
            WHERE id = ?
            """;

    private ProductDao(){
    }

    public List<ProductEntity> findAll(ProductEntityFilter filter){
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.productName() != null){
            whereSql.add("product_name = ?");
            parameters.add(filter.productName());
        }
        if (filter.price() != 0){
            whereSql.add("price = ?");
            parameters.add(filter.price());
        }
        if (filter.categoryId() != 0){
            whereSql.add("category_id = ?");
            parameters.add(filter.categoryId());
        }
        if (filter.manufacturerId() != 0){
            whereSql.add("manufacturer_id = ?");
            parameters.add(filter.manufacturerId());
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
            List<ProductEntity> productEntities = new ArrayList<>();
            while (resultSet.next()){
                productEntities.add(buildProduct(resultSet));
            }
            return productEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<ProductEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProductEntity> productEntities = new ArrayList<>();
            while (resultSet.next()){
                productEntities.add(buildProduct(resultSet));
            }
            return productEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<ProductEntity> findById(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ProductEntity productEntity = null;
            if (resultSet.next()){
                productEntity = buildProduct(resultSet);
            }

            return Optional.ofNullable(productEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static ProductEntity buildProduct(ResultSet resultSet) throws SQLException {
        return new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("product_name"),
                resultSet.getInt("price"),
                resultSet.getInt("category_id"),
                resultSet.getInt("manufacturer_id"),
                resultSet.getInt("quantity")
        );
    }

    public void update(ProductEntity productEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, productEntity.getProductName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setInt(3, productEntity.getCategoryId());
            preparedStatement.setInt(4, productEntity.getManufacturerId());
            preparedStatement.setInt(5, productEntity.getQuantity());
            preparedStatement.setLong(6, productEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public ProductEntity save(ProductEntity productEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, productEntity.getProductName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setInt(3, productEntity.getCategoryId());
            preparedStatement.setInt(4, productEntity.getManufacturerId());
            preparedStatement.setInt(5, productEntity.getQuantity());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                productEntity.setId(generatedKeys.getLong("id"));
            }
            return productEntity;
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

    public static ProductDao getInstance(){
        return INSTANCE;
    }
}
