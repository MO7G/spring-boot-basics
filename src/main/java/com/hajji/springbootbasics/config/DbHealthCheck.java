package com.hajji.springbootbasics.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DbHealthCheck implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public DbHealthCheck(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }






    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Connected to DB: " + conn.getMetaData().getDatabaseProductName()
                    + " " + conn.getMetaData().getDatabaseProductVersion());

            System.out.println("🔹 JDBC URL: " + conn.getMetaData().getURL());
            System.out.println("🔹 Driver: " + conn.getMetaData().getDriverName()
                    + " " + conn.getMetaData().getDriverVersion());
            System.out.println("🔹 Auto-commit: " + conn.getAutoCommit());
            System.out.println("🔹 Isolation level: " + conn.getTransactionIsolation());
        }

        // If the DataSource is Hikari, we can get pool properties
        if (dataSource instanceof HikariDataSource hikari) {
            System.out.println("🔹 Hikari Pool Name: " + hikari.getPoolName());
            System.out.println("🔹 Max pool size: " + hikari.getMaximumPoolSize());
            System.out.println("🔹 Min idle connections: " + hikari.getMinimumIdle());
        }
    }
}
