package org.example;

import org.example.configs.database.TablesStatements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static final TablesStatements tablesStatements = new TablesStatements();
    public static void main(String[] args) {
        tablesStatements.createTables();
        SpringApplication.run(Main.class, args);
    }
}