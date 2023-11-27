package com.dmdev.i.store.exception;
/**
 * Необходимо выбрать тему, спроектировать схему базы данных для нее
 * и написать DAO с как минимум основными CRUD операциями для каждой сущности.
 */

public class DaoException extends RuntimeException{

    public DaoException(Throwable throwable){
        super(throwable);
    }
}
