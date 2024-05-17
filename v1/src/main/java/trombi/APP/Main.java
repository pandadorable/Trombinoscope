package trombi.APP;

import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;

public class Main
    {
    public static String path;
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        path = System.getProperty("user.dir");
        MainWindow.main(args);
    }

}