import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import trombi.BDD.MariaDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MariaDBtest {

    @Mock
    Connection connectionMock;

    @Test
    public void testNoConnectionException(){
        String jdbcUrl = "jdbc:mariadb://localhost:3306/datatest";
        String username = "mauvais user";
        String password = "nopassword";
        assertThrows(SQLException.class, ()->{MariaDB.transformXLSXToBDD(DriverManager.getConnection(jdbcUrl, username, password), "ESIR.xlsx");});
    }

    @Test
    public void testNoXLSXException(){
        assertThrows(IOException.class, ()->{MariaDB.transformXLSXToBDD(connectionMock, "unknown.xlsx");});
    }
}
