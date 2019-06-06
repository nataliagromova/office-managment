package com.java_app.dao;

import com.java_app.entity.Employee;
import com.java_app.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {


    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static String sql = "SELECT employee_page.id,employee_page.last_name,employee_page.first_name,\n" +
            "            employee_page.birth,employee_page.email,employee_page.wage,\n" +
            "            profession.proffession_name as profession,\n" +
            "            cabinet.cabinet as cabinet,p.projects as project\n" +
            "            FROM employee_page\n" +
            "            left join cabinet on employee_page.cabinet = cabinet.id\n" +
            "            left join profession on employee_page.profession = profession.id\n" +
            "            left join result_employee_project on employee_page.id = result_employee_project.id_employee\n" +
            "            left join projects p on result_employee_project.id_project = p.id";

    public List<Employee> findAll() {
        return jdbcTemplate.query(sql, new EmployeeMapper());
    }

    @Override
    public Employee getEmployeeById(int id) {
        String sql = this.sql + " WHERE employee_page.id="+id;
        List<Employee> employees = jdbcTemplate.query(sql, new EmployeeMapper());
        if(employees.size()==0)
            return null;
        return jdbcTemplate.query(sql, new EmployeeMapper()).get(0);
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        String sql = this.sql + " WHERE employee_page.last_name=?";
        return jdbcTemplate.query(sql, new EmployeeMapper(), name);
    }

    @Override
    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employee_page (last_name,first_name,birth,email,wage,profession,cabinet) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, employee.getLast_name(), employee.getFirst_name(), employee.getAge(),
                employee.getEmail(),
                employee.getWage(),
                employee.getProfession().getValue(), employee.getNumberOfCabinet().getValue());
    }

    @Override
    public void deleteEmployeeById(int id) {
        String sql = "DELETE FROM employee_page WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateWage(int id, int wage) {
        String sql = "UPDATE employee_page SET wage=? WHERE id=?";
        jdbcTemplate.update(sql, wage, id);
    }

    @Override
    public void addEmployeeById(int id, int project) {
        String sql = "INSERT INTO result_employee_project (id_employee, id_project) VALUES (?,?)";
        jdbcTemplate.update(sql, id, project);
    }

    @Override
    public void deleteEmployeeFromProject(int id, int project) {
        String sql = "DELETE FROM result_employee_project WHERE id_employee=? AND id_project=?";
        jdbcTemplate.update(sql, id, project);
    }

    @Override
    public void updateRoom(int id, int room) {
        String sql = "UPDATE employee_page SET cabinet=? WHERE id=?";
        jdbcTemplate.update(sql, room, id);
    }
}
