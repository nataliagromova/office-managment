package com.java_app.service;

import com.java_app.dao.EmployeeDao;
import com.java_app.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public EmployeeDao employeeDao;

    public List<Employee> findAll() {

        return employeeDao.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {

        return employeeDao.getEmployeeByName(name);
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeDao.createEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeDao.deleteEmployeeById(id);
    }

    @Override
    public void updateWage(int id, int wage) {
        employeeDao.updateWage(id,wage);
    }

    @Override
    public void addEmployeeById(int id, int project) {
        employeeDao.addEmployeeById(id,project);
    }

    @Override
    public void deleteEmployeeFromProject(int id, int project) {
        employeeDao.deleteEmployeeFromProject(id,project);
    }

    @Override
    public void updateRoom(int id, int room) {
        employeeDao.updateRoom(id,room);
    }
}
