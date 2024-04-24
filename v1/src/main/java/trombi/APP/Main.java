package trombi.APP;

import java.sql.SQLException;
import java.io.FileNotFoundException;

public class Main
    {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        // // Initialise l'application Qt
        // QApplication.initialize(args);

        // //Fenetre principale
        // MainWindow mainWindow = new MainWindow();
        // mainWindow.resize(800, 600);
        // mainWindow.show();


        // // Lance l'application
        // QApplication.exec();
        // QApplication.shutdown();
        MainWindow.main(args);
    }

}