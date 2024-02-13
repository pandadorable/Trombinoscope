package trombi.APP;

import trombi.CAMERA.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import trombi.BDD.MariaDB;

import trombi.BDD.*;
public class Main {
    public static void main(String[] args) {        
        CameraManager cameraManager = new CameraManager();
        CameraWindow cameraWindow = new CameraWindow(cameraManager);

        String jdbcUrl = "jdbc:mariadb://localhost:3306/datatest";
        String username = "root";
        String password = "trombipw";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

                MariaDB.transformXLSXToBDD(connection, "ESIR.xlsx");

            /*
            // Exécutez votre script SQL ici
            String[] sqlScript = ExcelToSQl.transformXLSXToBDD();
            // check if the file exists at the file path
            File file = new File("BDD.db");
            if(!file.exists()) statement.execute(sqlScript[0]);
            //fill the database
            statement.execute(sqlScript[1]);
            System.out.println("Script SQL exécuté avec succès.");
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}