package com.example.cellphones.exception;

public class VehicleNotFoundByIdException extends RuntimeException{
    public VehicleNotFoundByIdException(Long id) {
        super("Vehicle not found by " + id);
    }
}
