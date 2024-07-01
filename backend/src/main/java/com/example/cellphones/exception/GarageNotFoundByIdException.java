package com.example.cellphones.exception;

public class GarageNotFoundByIdException extends RuntimeException{
    public GarageNotFoundByIdException(Long id) {
        super("Garage not found by " + id);
    }
}
