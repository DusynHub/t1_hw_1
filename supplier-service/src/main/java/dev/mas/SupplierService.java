package dev.mas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@ComponentScan(basePackages = {"dev"})
@EnableWebMvc
public class SupplierService {
    public static void main(String[] args) {
        SpringApplication.run(SupplierService.class, args);
    }
}
