package trombi.APP;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import trombi.BDD.MariaDB;
import trombi.CAMERA.CameraWindow;

public class Main
    {
    public static void main(String[] args) throws SQLException, IOException {
        MainWindow.main(args);
        MariaDB.kill(); // end connection with BDD
        CameraWindow.kill(); // close all connection with webcams
        cleanFiles("last_compressed_img.jpg"); //clean temporary file use for compression
        
        System.exit(0);
    }

    public static void cleanFiles(String file)
    {
        File myObj = new File(file);
        myObj.delete();
    }
}