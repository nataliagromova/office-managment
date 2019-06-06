package com.java_app.service;

import com.java_app.entity.Employee;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Pattern;

@Service
public class EmployeeValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }
//    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//    FileInputStream fis = new FileInputStream(getClass().getClassLoader().getResource("jdbc.proc").getFile());
//    Properties p = new Properties();
//        p.load(fis);
//    String dname = (String) p.get("Dname");
//    String url = (String) p.get("URL");
//    String username = (String) p.get("Uname");
//    String password = (String) p.get("password");
//        Class.forName(dname);
//        driverManagerDataSource.setUrl(url);
//        driverManagerDataSource.setUsername(username);
//        driverManagerDataSource.setPassword(password);
//        driverManagerDataSource.setDriverClassName(dname);
//        return driverManagerDataSource;

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "last_name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "first_name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "wage", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "age", "name.empty");
        Employee employee = (Employee) o;
        Pattern pattern_email = Pattern.compile("\\w+@\\w+(\\.)\\w+");
        Pattern pattern_birth = Pattern.compile("(\\d){4}-(\\d){1,2}-(\\d){1,2}");
        if (!pattern_email.matcher(employee.getEmail()).matches()) {
            errors.rejectValue("email", "badvalue");
        }
        if (employee.getWage()<0){
            errors.rejectValue("wage", "badvalue");
        }
        if (!pattern_birth.matcher(employee.getAge()).matches()){
            errors.rejectValue("email", "badvalue");
        }
    }
}
