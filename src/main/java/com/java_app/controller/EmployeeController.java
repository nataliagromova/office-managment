package com.java_app.controller;


import com.java_app.entity.Employee;
import com.java_app.entity.NumberOfCabinet;
import com.java_app.entity.Projects;
import com.java_app.service.EmployeeService;
import com.java_app.util.CustomErrorType;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/")
@Api(value = "/", description = "Операции с профилем")
public class EmployeeController {

    @Qualifier("getEmployeeValidator")
    @Autowired
    private Validator personValidator;


    @Qualifier("getEmployeeService")
    @Autowired
    public EmployeeService employeeService;

    @RequestMapping(method = GET, value = "usersnames")
    @Produces("application/json")
    public ResponseEntity<List<String>> getAllUsersNames() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        List<String> names = new ArrayList<>();
        employees.forEach(e -> names.add(e.getLast_name() + " " + e.getFirst_name()));

        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "users")
    @Produces("application/json")
    public ResponseEntity<?> getAllInfoOfUsers() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @RequestMapping(method = GET, value = "user/{id}")
    @Produces("application/json")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomErrorType("Unable to find user. User with id " + id + " not found."), HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "users/{last_name}")
    @Produces("application/json")
    public ResponseEntity<?> getByName(@PathVariable("last_name") String last_name) {
        List<Employee> employees = employeeService.getEmployeeByName(last_name);
        if (employees.size() == 0) {
            return new ResponseEntity(new CustomErrorType("Unable to find one. User with last name " + last_name + " not found."), HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @RequestMapping(method = DELETE, value = "user/{id}")
    @Produces("application/json")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomErrorType("Unable to find user. User with id " + id + " not found."), HttpStatus.NOT_FOUND);

        }
        employeeService.deleteEmployeeById(id);
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @RequestMapping(method = POST, value = "users")
    @Produces("application/json")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> create(@RequestBody Employee employee, UriComponentsBuilder ucBuilder,
                                    BindingResult bindingResult) {

        personValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new CustomErrorType("Unable to add user. " + bindingResult.getFieldError().toString()), HttpStatus.NOT_ACCEPTABLE);
        }
        employeeService.createEmployee(employee);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "addproject/id={id}/project={project}")
    @Produces("application/json")
    public ResponseEntity<?> addEmployee(@PathVariable("id") int id, @PathVariable("project") String project) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomErrorType("Unable to add project. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        try {
            Projects.valueOf(project);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new CustomErrorType("Unable to update project. Project " + project + " is not found."), HttpStatus.NOT_FOUND);
        }
        int project_int = Projects.valueOf(project).getValue();
        employeeService.addEmployeeById(id, project_int);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, value = "deleteproject/id={id}/project={project}")
    @Produces("application/json")
    public ResponseEntity<?> deleteProject(@PathVariable("id") int id, @PathVariable("project") String project) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete project. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        try {
            Projects.valueOf(project);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new CustomErrorType("Unable to delete user from project. Project " + project + " is not found."), HttpStatus.NOT_FOUND);
        }
        int project_int = Projects.valueOf(project).getValue();
        employeeService.deleteEmployeeFromProject(id, project_int);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "updwage/id={id}/wage={wage}")
    @Produces("application/json")
    public ResponseEntity<?> updwage(@PathVariable("id") int id, @PathVariable("wage") int wage) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomErrorType("Unable to updateWage wage. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        if (employee.getWage() >= wage) {
            return new ResponseEntity(new CustomErrorType("Unable to updateWage wage. Wage should be bigger than previous one."), HttpStatus.NOT_ACCEPTABLE);

        }
        employeeService.updateWage(id, wage);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "updroom/id={id}/room={room}")
    @Produces("application/json")
    public ResponseEntity<?> updroom(@PathVariable("id") int id, @PathVariable("room") String room) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity(new CustomErrorType("Unable to update room. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        try {
            NumberOfCabinet.valueOf(room);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(new CustomErrorType("Unable to update room. Room " + room + " is not found."), HttpStatus.NOT_FOUND);
        }
        int room_int = NumberOfCabinet.valueOf(room).getValue();
        employeeService.updateRoom(id, room_int);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
