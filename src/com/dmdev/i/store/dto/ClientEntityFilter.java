package com.dmdev.i.store.dto;
/**
 * Необходимо выбрать тему, спроектировать схему базы данных для нее
 * и написать DAO с как минимум основными CRUD операциями для каждой сущности.
 */

public record ClientEntityFilter(int limit,
                                 int offset,
                                 String fName,
                                 String email) {


}
