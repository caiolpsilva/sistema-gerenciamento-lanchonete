package br.com.snackshop.exception;

public class OrderNotStartedException extends RuntimeException{
    public OrderNotStartedException(String message){
        super(message);
    }
}
