package org.solution.service.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Основной класс.
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.solution")
public class Application {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
