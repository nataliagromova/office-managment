package com.java_app.config;


import com.java_app.dao.EmployeeDao;
import com.java_app.dao.EmployeeDaoImpl;
import com.java_app.service.EmployeeService;
import com.java_app.service.EmployeeServiceImpl;
import com.java_app.service.EmployeeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

@EnableWebMvc
@ComponentScan(basePackages = "com.java_app")
@Configuration
@EnableSwagger2
public class SpringConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public JdbcTemplate getJdbcTemplate() throws IOException, ClassNotFoundException {
        return new JdbcTemplate(getDataSource());

    }

    @Bean
    public DataSource getDataSource() throws IOException, ClassNotFoundException {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/employee"+
//                "?verifyServerCertificate=false"+
//                "&useSSL=false"+
//                "&requireSSL=false"+
//                "&useLegacyDatetimeCode=false"+
//                "&amp"+
//                "&serverTimezone=UTC");
//        driverManagerDataSource.setUsername("root");
//        driverManagerDataSource.setPassword("admin");
//        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        return driverManagerDataSource;
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        FileInputStream fis = new FileInputStream(getClass().getClassLoader().getResource("jdbc.prop").getFile());
        Properties p = new Properties();
        p.load(fis);
        fis.close();
        String dname = p.getProperty("Dname");
        String url = p.getProperty("URL");
        String username = p.getProperty("Uname");
        String password = p.getProperty("password");
        Class.forName(dname);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        driverManagerDataSource.setDriverClassName(dname);
        return driverManagerDataSource;
    }

    @Bean
    public EmployeeDao getEmployeeDao() throws IOException, ClassNotFoundException {
        return new EmployeeDaoImpl(getJdbcTemplate());
    }

    @Bean
    public EmployeeService getEmployeeService() {
        return new EmployeeServiceImpl();
    }

    @Bean
    public EmployeeValidator getEmployeeValidator() {
        return new EmployeeValidator();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo(
                        "Spring REST API",
                        "Example of REST API",
                        "1.0.0",
                        null,
                        null,
                        null,
                        null,
                        new ArrayList<>()
                ));
    }
}
