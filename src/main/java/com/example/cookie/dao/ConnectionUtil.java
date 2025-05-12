package com.example.cookie.dao;

import java.sql.Connection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public enum ConnectionUtil {
    INSTANCE;

    private HikariDataSource ds;

    ConnectionUtil()
    {
        HikariConfig config=new HikariConfig();

        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("ironbear");
        config.setPassword("5959");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds=new HikariDataSource(config);
    }

    public Connection getConnection() throws Exception{
        return ds.getConnection();
    }

}
