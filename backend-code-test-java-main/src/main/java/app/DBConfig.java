package app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConfig {

    private DBConfig() {}

    public static HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false ");
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setUsername("test");
        hikariConfig.setPassword("test");
        return hikariConfig;
    }

    public static HikariDataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }
}
