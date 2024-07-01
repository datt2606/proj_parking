package com.example.cellphones.exception;

public class CategoryNotFoundByIdException extends RuntimeException{
    public CategoryNotFoundByIdException(Long id) {
            super("Vehicle category not found by " + id);
        }
}
