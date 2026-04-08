package com.example.Accenture.domain.exception;

public class BranchNotFoundException extends BusinessException {
    public BranchNotFoundException(String id) {
        super("Branch not found: " + id);
    }
}