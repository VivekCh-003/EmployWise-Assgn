package com.example.EmployWise.controller;

import com.example.EmployWise.entity.Employee;
import com.example.EmployWise.service.EmailService;
import com.example.EmployWise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    private String save(@RequestBody Employee employee){
        if(Objects.equals(employee.getReportsTo(), "manager")){
            sendNewEmployeeDetails(employee);
        }
        if(employee.getReportsTo() != null && !employee.getReportsTo().isEmpty()){
            Employee manager = employeeService.findByName(employee.getReportsTo());
            if (manager != null) {
                employee.setReportsTo(manager.getId());
            } else {
                return "Manager with name " + employee.getReportsTo() + " not found.";
            }
        }
        employee.setId(java.util.UUID.randomUUID().toString());

        employeeService.saveEmployee(employee);

        return "Employee added with Id: "+employee.getId();
    }

    private void sendNewEmployeeDetails(Employee employee) {
        String managerEmail = "nilanjanaghosal001@gmail.com";

        String emailText = String.format(
                "%s will now work under you. "+"Mobile number is %s and email is %s."
                ,employee.getName(),employee.getPhoneNumber(),employee.getEmail()
        );

        try{
            emailService.sendNewEmployeeEmail(managerEmail,"New Employee Added",emailText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/findAll")
    private List<Employee> getAllEmployees(){
        return employeeService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    private void deleteById(@PathVariable String id){
        employeeService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<Employee> updateEmployee(@PathVariable String id,@RequestBody Employee updatedEmployee){
        try{
            if(updatedEmployee.getReportsTo() != null && !updatedEmployee.getReportsTo().isEmpty()){
                Employee higherAuthority = employeeService.findByName(updatedEmployee.getReportsTo());
                if(higherAuthority != null){
                    updatedEmployee.setReportsTo(higherAuthority.getId());
                }
            }
            Employee updated = employeeService.updateEmployee(id,updatedEmployee);
            return ResponseEntity.ok(updated);
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/manager/{level}")
    private ResponseEntity<Employee> getNthLevelManager(@PathVariable String id,@PathVariable int level){
        try{
            Employee nthLevelManager = employeeService.findNthLevelManager(id,level);
            return ResponseEntity.ok(nthLevelManager);
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/advanceSearch/{pageNumber}/{pageSize}/{sortBy}")
    private ResponseEntity<Page<Employee>> advanceSearch(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String sortBy){
        try{
            Page<Employee> employees = employeeService.advanceSearch(pageNumber,pageSize,sortBy);
            return ResponseEntity.ok(employees);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
