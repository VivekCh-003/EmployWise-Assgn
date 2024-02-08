package com.example.EmployWise.repository;

import com.example.EmployWise.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Page<Employee> findAll(Pageable page);
    Employee findByName(String name);
}
