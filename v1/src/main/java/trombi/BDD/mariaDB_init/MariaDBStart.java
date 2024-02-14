package trombi.BDD.mariaDB_init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MariaDBStart {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mariadb://localhost:3306/datatest";
        String username = "root";
        String password = "trombipw";

        try {
            Connection connection =
                    DriverManager.getConnection(jdbcUrl, username, password);
            // Now you can use 'connection' to execute SQL queries.
            String insertQuery = "REPLACE ELEVE (nom, prenom, email) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, "Quoicoup");
            preparedStatement.setString(2, "BebUwU");
            preparedStatement.setString(3, "QuoicoupBebUwU@mail.com");
            preparedStatement.executeUpdate();
            // Don't forget to close the connection when you're done.
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
