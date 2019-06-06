package com.java_app.mapper;

import com.java_app.entity.Employee;
import com.java_app.entity.NumberOfCabinet;
import com.java_app.entity.Profession;
import com.java_app.entity.Projects;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


public class EmployeeMapper implements ResultSetExtractor<List<Employee>> {
    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Employee> employees = new ArrayList<>();
        Set<Integer> employees_names = new HashSet<>();
        while (resultSet.next()) {
            Employee employee = new Employee();
            boolean f = false;
            employee.setId(resultSet.getInt("id"));
            employee.setLast_name(resultSet.getString("last_name"));
            employee.setFirst_name(resultSet.getString("first_name"));
            if (employees_names.contains((int)employee.getId())) {
                f = true;
            } else
                employees_names.add((int) employee.getId());

            employee.setWage(resultSet.getInt("wage"));
            employee.setEmail(resultSet.getString("email"));
            employee.setAge(String.valueOf(Period.between(Date.valueOf(resultSet.getString("birth")).toLocalDate(), LocalDate.now()).getYears()));
            employee.setProfession(Profession.valueOf(resultSet.getString("profession")));
            employee.setNumberOfCabinet(NumberOfCabinet.valueOf(resultSet.getString("cabinet")));
            String projectString = resultSet.getString("project");

            Projects project ;

//            long id = resultSet.getInt("id");
//            String last_name = resultSet.getString("last_name");
//            String first_name = resultSet.getString("first_name");
//            int wage = resultSet.getInt("wage");
//            String email = resultSet.getString("email");
//            String age = String.valueOf(Period.between(Date.valueOf(resultSet.getString("birth")).toLocalDate(), LocalDate.now()).getYears());
//            int nameOfProject = resultSet.getInt("project");
//            Profession profession = Profession.valueOf(resultSet.getString("profession"));
//            NumberOfCabinet numberOfCabinet = NumberOfCabinet.valueOf(resultSet.getString("cabinet"));
//            String country = resultSet.getString("country_name");
//            data.putIfAbsent(country, new ArrayList<>());
//            String city = resultSet.getString("city_name");
//            data.get(country).add(city);i
            if(projectString!= null){
                project = Projects.valueOf(resultSet.getString("project"));
                if (f) {
                    for (Employee idx : employees) {
                        if ((idx.getId()) == (employee.getId())) {
                            idx.getProjectsList().add(project);
                        }
                    }
                } else {
                    employee.setProjectsList(new LinkedList<>());
                    employee.getProjectsList().add(project);
                    employees.add(employee);
                }
            }
            else employees.add(employee);

        }
        return employees;
    }

}
//public class EmployeeMapper implements RowMapper<Employee> {
//    @Override
//    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
//        Employee employee = new Employee();
//        employee.setId(resultSet.getInt("id"));
//        employee.setLast_name(resultSet.getString("last_name"));
//        employee.setFirst_name(resultSet.getString("first_name"));
//        employee.setWage(resultSet.getInt("wage"));
//        employee.setEmail(resultSet.getString("email"));
////        employee.setAge(resultSet.getString("birth"));
//        employee.setAge(String.valueOf(Period.between(Date.valueOf(resultSet.getString("birth")).toLocalDate(), LocalDate.now()).getYears()));
//        employee.setNameOfProject(resultSet.getInt("project"));
//        employee.setProfession(Profession.valueOf(resultSet.getString("profession")));
//        employee.setNumberOfCabinet(NumberOfCabinet.valueOf(resultSet.getString("cabinet")));
//        return employee;
//    }

//    @Override
//    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
//        HashMap<Long,Employee> employeeHashMap = new HashMap<>();
//        List<Employee> employees = new ArrayList<>();
//        Employee current_employee = null;
//        while (resultSet.next()){
//            long id = resultSet.getInt("id");
//            String last_name = resultSet.getString("last_name");
//            String first_name = resultSet.getString("first_name");
//            int wage = resultSet.getInt("wage");
//            String email = resultSet.getString("email");
//            String age = String.valueOf(Period.between(Date.valueOf(resultSet.getString("birth")).toLocalDate(), LocalDate.now()).getYears());
//            int nameOfProject = resultSet.getInt("project");
//            Profession profession = Profession.valueOf(resultSet.getString("profession"));
//            NumberOfCabinet numberOfCabinet = NumberOfCabinet.valueOf(resultSet.getString("cabinet"));
//
//            if(current_employee == null) {
//                current_employee = new Employee(id, last_name, first_name, age, email, wage, nameOfProject, numberOfCabinet);
//                employeeHashMap.put(id, current_employee);
//            }
//            current_employee.
//
//
//            }
//        }


