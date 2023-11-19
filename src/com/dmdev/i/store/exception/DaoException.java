package com.dmdev.i.store.exception;

public class DaoException extends RuntimeException{

    public DaoException(Throwable throwable){
        super(throwable);
    }
}
