package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controller"})
public class PapelariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PapelariaApplication.class, args);
    }
}
