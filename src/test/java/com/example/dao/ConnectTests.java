package com.example.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariJNDIFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java. sql.Connection;
import java.sql.DriverManager;

public class ConnectTests {

    @Test
    public void test1()
    {
        int v1=10;
        int v2=10;

        Assertions.assertEquals(v1, v2);
    }

    @Test
    public void testConnection() throws Exception{
        Class.forName("org.mariadb.jdbc.Driver");

        Connection conn= DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/webdb",
                "ironbear",
                "5959"
        );

        Assertions.assertNotNull(conn);

        conn.close();
    }

    @Test
    public void testHikariCP() throws Exception{
        HikariConfig config=new HikariConfig();

        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("ironbear");
        config.setPassword("5959");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds=new HikariDataSource(config);
        Connection conn=ds.getConnection();
        System.out.println(conn);
        conn.close();
    }
}

















