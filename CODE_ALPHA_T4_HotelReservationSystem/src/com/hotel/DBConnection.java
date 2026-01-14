package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Oracle Configuration
    // If you are using Oracle Express Edition, "xe" is correct.
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
    private static final String USER = "SYSTEM"; 
    private static final String PASSWORD = "NewPassword123"; // <--- CHANGE THIS TO YOUR PASSWORD!

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}