package com.dmdev.i.store.dao;

import com.dmdev.i.store.dto.PurchaseEntityFilter;
import com.dmdev.i.store.entity.ClientEntity;
import com.dmdev.i.store.entity.PurchaseEntity;
import com.dmdev.i.store.exception.DaoException;
import com.dmdev.i.store.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class PurchaseDao {



    public static final PurchaseDao INSTANCE = new PurchaseDao();
    public static final String DELETE_SQL = """
            DELETE FROM purchase
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO purchase(store_id, product_id, product_price, client_id, purchase_status_id) 
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE purchase
            SET store_id = ?,
                product_id = ?,
                product_price = ?,
                client_id = ?,
                purchase_status_id = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, 
                    store_id, 
                    product_id, 
                    product_price, 
                    client_id,
                    purchase_status_id
            FROM purchase
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, 
                    store_id, 
                    product_id, 
                    product_price, 
                    client_id,
                    purchase_status_id
            FROM purchase
            WHERE id = ?
            """;

    private PurchaseDao(){
    }

    public List<PurchaseEntity> findAll(PurchaseEntityFilter filter){
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.productId() != null){
            whereSql.add("product_id = ?");
            parameters.add(filter.productId());
        }
        if (filter.clientId() != null){
            whereSql.add("client_id = ?");
            parameters.add(filter.clientId());
        }
        if (filter.purchaseStatusId() != null){
            whereSql.add("purchase_status_id = ?");
            parameters.add(filter.purchaseStatusId());
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
            List<PurchaseEntity> purchaseEntities = new ArrayList<>();
            while (resultSet.next()){
                purchaseEntities.add(buildPurchase(resultSet));
            }
            return purchaseEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<PurchaseEntity> findAll(){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PurchaseEntity> purchaseEntities = new ArrayList<>();
            while (resultSet.next()){
                purchaseEntities.add(buildPurchase(resultSet));
            }
            return purchaseEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<PurchaseEntity> findById(Long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            PurchaseEntity purchaseEntity = null;
            if (resultSet.next()){
                purchaseEntity = buildPurchase(resultSet);
            }

            return Optional.ofNullable(purchaseEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private static PurchaseEntity buildPurchase(ResultSet resultSet) throws SQLException {
        return new PurchaseEntity(
                resultSet.getLong("id"),
                resultSet.getInt("store_id"),
                resultSet.getLong("product_id"),
                resultSet.getInt("product_price"),
                resultSet.getLong("client_id"),
                resultSet.getInt("purchase_status_id")
        );
    }

    public void update(PurchaseEntity purchaseEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, purchaseEntity.getStoreId());
            preparedStatement.setLong(2, purchaseEntity.getProductId());
            preparedStatement.setInt(3, purchaseEntity.getProductPrice());
            preparedStatement.setLong(4, purchaseEntity.getClientId());
            preparedStatement.setInt(5, purchaseEntity.getPurchaseStatusId());
            preparedStatement.setLong(6, purchaseEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public PurchaseEntity save(PurchaseEntity purchaseEntity){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, purchaseEntity.getStoreId());
            preparedStatement.setLong(2, purchaseEntity.getProductId());
            preparedStatement.setInt(3, purchaseEntity.getProductPrice());
            preparedStatement.setLong(4, purchaseEntity.getClientId());
            preparedStatement.setInt(5, purchaseEntity.getPurchaseStatusId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                purchaseEntity.setId(generatedKeys.getLong("id"));
            }
            return purchaseEntity;
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

    public static PurchaseDao getInstance(){
        return INSTANCE;
    }
}
