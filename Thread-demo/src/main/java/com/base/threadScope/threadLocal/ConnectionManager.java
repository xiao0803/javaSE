package com.base.threadScope.threadLocal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {//此方法在覆盖之前返回的是null，它是在ThreadLocal.get()方法中被调用（详情看源码）
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/test", "username",
                        "password");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }

    public static void setConnection(Connection conn) {
        connectionHolder.set(conn);
    }
}