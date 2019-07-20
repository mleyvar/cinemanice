package com.marcopololeyva.cinemanice.data.api;

public abstract class Callback<T> {

    public abstract void returnResult(T t);
    public abstract void returnError(String message);
}
