package trombi.APP;

import trombi.CAMERA.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import trombi.BDD.*;
public class Main {
    public static void main(String[] args) {        
        CameraManager cameraManager = new CameraManager();
        CameraWindow cameraWindow = new CameraWindow(cameraManager);

        String url = "jdbc:sqlite:"+ExcelToSQl.exemplePath+"BDD.db"; // Change to your SQLite database URL
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Exécutez votre script SQL ici
            String[] sqlScript = ExcelToSQl.transformXSLXToBDD();
            // check if the file exists at the file path
            File file = new File(ExcelToSQl.exemplePath+"BDD.db");
            if(!file.exists()) statement.execute(sqlScript[0]);
            //fill the database
            statement.execute(sqlScript[1]);
            System.out.println("Script SQL exécuté avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}