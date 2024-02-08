package com.example.EmployWise.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document()
public class Employee {
    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String reportsTo;
    private String profileUrl;

}
