package com.avol.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by Lovababu on 6/13/2015.
 */
@Configuration
@ComponentScan(basePackages = "com.avol")
@EnableTransactionManagement
public class AppConfig {


    /**
     * Spring provided HSQL Embedded Database.
     * Read the dbscript and initiates the Database.
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setName("HSQL-Test-DB");
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:db-scripts/my-schema.sql")
                .addScript("classpath:db-scripts/my-test-data.sql").build();
        System.out.println("Database initialization done.");
        return db;
    }
}
