package com.proj.itstaym.libsyst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivtyTest {

    public static void main(String[] args) {

        // Database credentials
        String url = "jdbc:mysql://localhost:3308/libsyst";
        String username = "root";
        String password = "root";

        System.out.println("Attempting to connect to the database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection != null && connection.isValid(5)) {
                System.out.println("Success! Connected to the MySQL database.");
                // You can perform a simple query here to further test the connection
                // E.g., connection.createStatement().executeQuery("SELECT 1");
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the MySQL database!");
            System.err.println("Reason: " + e.getMessage());
            // It's helpful to print the stack trace for more detailed errors
            // e.printStackTrace();
        }

    }

}
