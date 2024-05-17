package trombi.APP;

import java.sql.SQLException;

import trombi.BDD.MariaDB;
import trombi.CAMERA.CameraWindow;

import java.io.FileNotFoundException;

public class Main
    {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        MainWindow.main(args);
        MariaDB.kill();
        CameraWindow.kill();
        System.exit(0);
    }

}