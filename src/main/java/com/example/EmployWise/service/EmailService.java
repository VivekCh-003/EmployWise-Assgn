package com.example.EmployWise.service;

public interface EmailService {
    void sendNewEmployeeEmail(String managerEmail, String newEmployeeAdded, String emailText);
}
