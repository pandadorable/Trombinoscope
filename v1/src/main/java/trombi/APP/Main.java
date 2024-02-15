package trombi.APP;

import trombi.BDD.MariaDB;
import trombi.PDF.pdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import io.qt.widgets.*;

public class Main {
    public static void main(String[] args) {
        // Initialise l'application Qt
        QApplication.initialize(args);

        //Fenetre principale
        MainWindow mainWindow = new MainWindow();
        mainWindow.resize(800, 600);
        mainWindow.show();

        // Lance l'application
        QApplication.exec();
        QApplication.shutdown();

        try{

            MariaDB.transformXLSXToBDD("ESIR.xlsx");
            MariaDB.insertImage("mario.bros@univ-rennes.fr", "uwu.jpg");
            byte[] image = MariaDB.getImage("mario.bros@univ-rennes.fr");
            File fichierImage = new File("ImageRecuperer.jpg");// ou jpg
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(image)), "jpg", fichierImage);
            pdf.pdf();
            /*
             * // Exécutez votre script SQL ici
             * String[] sqlScript = ExcelToSQl.transformXLSXToBDD();
             * // check if the file exists at the file path
             * File file = new File("BDD.db");
             * if(!file.exists()) statement.execute(sqlScript[0]);
             * //fill the database
             * statement.execute(sqlScript[1]);
             * System.out.println("Script SQL exécuté avec succès.");
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
}

}