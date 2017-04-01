package com.vccorp.dao;

import com.vccorp.config.SystemConf;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    private DataSource dataSource;
    private Connection connection = null;

    public DBConnection() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/NewsDb");
            connection = dataSource.getConnection();
        } catch (Exception e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        SystemConf.MYSQL_URL, SystemConf.MYSQL_USERNAME, SystemConf.MYSQL_PASSWORD);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
