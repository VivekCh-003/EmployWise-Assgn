package com.example.EmployWise.service;

import com.example.EmployWise.controller.EmployeeNotFoundException;
import com.example.EmployWise.entity.Employee;
import com.example.EmployWise.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public String saveEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id).
                orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        BeanUtils.copyProperties(updatedEmployee,existingEmployee,"id");

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public Employee findNthLevelManager(String id, int level) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));

        return findNthLevelManagerRecursive(employee,level);
    }

    public Employee findNthLevelManagerRecursive(Employee employee,int level){
        if(level == 0){
            return employee;
        }

        String managerId = employee.getReportsTo();
        if(managerId == null){
            return null;
        }

        Employee manager = employeeRepository.findById(managerId).orElseThrow(() -> new EmployeeNotFoundException("Manager not found with ID: " + managerId));

        return findNthLevelManagerRecursive(manager,level-1);
    }

    @Override
    public Page<Employee> advanceSearch(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }
}
