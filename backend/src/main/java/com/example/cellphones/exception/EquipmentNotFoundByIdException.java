package com.example.cellphones.exception;

public class EquipmentNotFoundByIdException extends RuntimeException{
    public EquipmentNotFoundByIdException(Long id) {
        super("Equipment not found by " + id);
    }
}
