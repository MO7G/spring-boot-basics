package com.hajji.springbootbasics.config;

import com.zaxxer.hikari.HikariDataSource;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

<<<<<<< HEAD
@Slf4j
=======
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca
@Component
public class DbHealthCheck implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public DbHealthCheck(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

<<<<<<< HEAD
    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            log.info("✅ Connected to DB: {} {}",
                    conn.getMetaData().getDatabaseProductName(),
                    conn.getMetaData().getDatabaseProductVersion());

            log.info("🔹 Driver: {} {}",
                    conn.getMetaData().getDriverName(),
                    conn.getMetaData().getDriverVersion());
            log.info("🔹 Auto-commit: {}", conn.getAutoCommit());
            log.info("🔹 Isolation level: {}", conn.getTransactionIsolation());
        }

        // If the DataSource is Hikari, we can log pool properties
        if (dataSource instanceof HikariDataSource hikari) {
            log.info("🔹 Hikari Pool Name: {}", hikari.getPoolName());
            log.info("🔹 Max pool size: {}", hikari.getMaximumPoolSize());
            log.info("🔹 Min idle connections: {}", hikari.getMinimumIdle());
=======





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
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca
        }
    }
}
