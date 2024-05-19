package trombi.APP;

import java.io.IOException;
import java.sql.SQLException;

import trombi.BDD.MariaDB;
import trombi.CAMERA.CameraWindow;

public class Main
    {
    public static void main(String[] args) throws SQLException, IOException {
        MainWindow.main(args);
        MariaDB.kill();
        CameraWindow.kill();
        
        System.exit(0);
    }
}