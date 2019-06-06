package com.java_app.service;

import com.java_app.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee getEmployeeById (int id);
    List <Employee> getEmployeeByName (String name);

    void createEmployee(Employee employee);
    void deleteEmployeeById(int id);
    void updateWage(int id, int wage);
    void updateRoom(int id, int room);
    void addEmployeeById(int id,int project);
    void deleteEmployeeFromProject(int id, int project);
}
