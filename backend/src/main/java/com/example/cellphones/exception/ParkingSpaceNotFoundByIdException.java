package com.example.cellphones.exception;

public class ParkingSpaceNotFoundByIdException extends RuntimeException{
    public ParkingSpaceNotFoundByIdException(Long id) {
        super("Parking space not found by " + id);
    }
}
