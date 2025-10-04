package com.hajji.springbootbasics.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
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
            log.info("✅ Connected to DB: {} {}",
                    conn.getMetaData().getDatabaseProductName(),
                    conn.getMetaData().getDatabaseProductVersion());

            log.info("🔹 JDBC URL: {}", conn.getMetaData().getURL());
            log.info("🔹 Driver: {} {}",
                    conn.getMetaData().getDriverName(),
                    conn.getMetaData().getDriverVersion());
            log.info("🔹 Auto-commit: {}", conn.getAutoCommit());
            log.info("🔹 Isolation level: {}", conn.getTransactionIsolation());
        }

        // If the DataSource is Hikari, log pool properties
        if (dataSource instanceof HikariDataSource hikari) {
            log.info("🔹 Hikari Pool Name: {}", hikari.getPoolName());
            log.info("🔹 Max pool size: {}", hikari.getMaximumPoolSize());
            log.info("🔹 Min idle connections: {}", hikari.getMinimumIdle());
        }
    }
}
