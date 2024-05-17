import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import trombi.BDD.MariaDB;
import trombi.BDD.mariaDB_init.MariaDBStart;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MariaDBtest {

    @Mock
    Connection connectionMock;

    /**
    @Test
    public void testNoConnectionException() {
        String jdbcUrl = "jdbc:mariadb://localhost:3306/datatest";
        String username = "mauvais user";
        String password = "nopassword";
        assertThrows(SQLException.class, () -> {
            MariaDB.transformXLSXToBDD(DriverManager.getConnection(jdbcUrl, username, password), "ESIR.xlsx");
        });
    }

    @Test
    public void testNoXLSXException() {
        assertThrows(IOException.class, () -> {
            MariaDB.transformXLSXToBDD(connectionMock, "unknown.xlsx");
        });
    }

    @Test
    public void testAjoutLine() throws SQLException {
        String jdbcUrl = "jdbc:mariadb://localhost:3306/datatest";
        String username = "root";
        String password = "trombipw";
        MariaDBStart.main(null); // insert / replace a new line
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ELEVE WHERE email=?");
        preparedStatement.setString(1, "QuoicoupBebUwU@mail.com");
        ResultSet resultSet = preparedStatement.executeQuery();
        int nbLine = 0;
        while (resultSet.next()) nbLine++;
        assertEquals(nbLine,1);
    }
    */
}
