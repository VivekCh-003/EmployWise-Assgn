package com.example.EmployWise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendNewEmployeeEmail(String managerEmail, String newEmployeeAdded, String emailText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(managerEmail);
        message.setSubject(newEmployeeAdded);
        message.setText(emailText);

        javaMailSender.send(message);
    }
}
