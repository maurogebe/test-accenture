package com.example.Accenture.domain.exception;

public class FranchiseNotFoundException extends BusinessException {
    public FranchiseNotFoundException(String id) {
        super("Franchise not found: " + id);
    }
}