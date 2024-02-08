package com.example.EmployWise.service;

import com.example.EmployWise.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    String saveEmployee(Employee employee);

    Employee findByName(String name);

    List<Employee> findAll();

    void deleteById(String id);

    Employee updateEmployee(String id, Employee updatedEmployee);

    Employee findNthLevelManager(String id, int level);

    Page<Employee> advanceSearch(int pageNumber, int pageSize, String sortBy);
}
