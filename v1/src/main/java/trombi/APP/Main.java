package trombi.APP;

import trombi.BDD.MariaDB;
import trombi.PDF.pdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import io.qt.widgets.*;

public class Main 
    {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        // Initialise l'application Qt
        QApplication.initialize(args);

        //Fenetre principale
        MainWindow mainWindow = new MainWindow();
        mainWindow.resize(800, 600);
        mainWindow.show();

        // Lance l'application
        QApplication.exec();
        QApplication.shutdown();
    }

}