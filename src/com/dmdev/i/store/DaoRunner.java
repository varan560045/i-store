package com.dmdev.i.store;

import com.dmdev.i.store.dao.ClientDao;
import com.dmdev.i.store.dto.ClientEntityFilter;
import com.dmdev.i.store.entity.ClientEntity;

import java.util.List;
import java.util.Optional;
/**
 * Необходимо выбрать тему, спроектировать схему базы данных для нее
 * и написать DAO с как минимум основными CRUD операциями для каждой сущности.
 */

public class DaoRunner {

    public static void main(String[] args) {

        ClientEntityFilter clientEntityFilter = new ClientEntityFilter(100, 0, null, "ejov@gmail.com");
        List<ClientEntity> clients = ClientDao.getInstance().findAll(clientEntityFilter);
        System.out.println(clients);
//        updateTest();
//        deleteTest();
//        saveTest()
    }

    private static void updateTest() {
        ClientDao clientDao = ClientDao.getInstance();
        Optional<ClientEntity> maybeClient = clientDao.findById(1L);
        System.out.println(maybeClient);

        maybeClient.ifPresent(client -> {
            client.setEmail("test@test.com");
            clientDao.update(client);
        });
    }

    private static void deleteTest() {
        ClientDao clientDao1 = ClientDao.getInstance();
        boolean deleteResult = clientDao1.delete(4L);
        System.out.println(deleteResult);
    }


    private static void saveTest() {
        ClientDao clientDao = ClientDao.getInstance();
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setfName("Юрий");
        clientEntity.setlName("Юрьев");
        clientEntity.setEmail("uryev@ya.ru");
        clientEntity.setAdmissionId(2);

        ClientEntity savedClient = clientDao.save(clientEntity);
        System.out.println(savedClient);
    }
}