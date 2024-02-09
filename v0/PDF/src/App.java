package PDF.src;


import org.bridj.util.Pair;
import org.sqlite.core.DB;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class App {
    public static final String DEST = "v0/PDF/src/trombi.pdf"; //PDF final (destination)
    public static final String IMG = "v0/PDF/src/zeldalogo.png";
    private static String nomPdf = "Trombinoscope";

    private static String query = "SELECT Nom,Prénom FROM Eleves";

    public static void queryModifier(String column, String groupe){
        if (!query.contains("WHERE")) query += " WHERE";
        else query += " AND";
        query += " " + column + " = '" + groupe + "'";
        nomPdf += " " + groupe;

    }

    public static void main(String[] args) throws Exception {
        
        //pour le test
        /*String[] nomE = new String[20];
        for (int i = 0; i < 20; i++) {
            int j = i+1;
            nomE[i] = "Elève "+ j;
        }*/

        ArrayList<String> nomE = new ArrayList<>();

        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+"v0/BDD/src/main/java/org/example/"+"BDD.db");
        Statement statement = connection.createStatement();


        //queryModifier("anglaiscom", "G1");

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {               // Position the cursor
            String nom = resultSet.getString("Nom");
            String prenom = resultSet.getString("Prénom");
            System.out.print("Nom = " + nom + " ; Prénom = " + prenom);
            System.out.println();
            nomE.add(nom + " " + prenom);
        }
        resultSet.close();                       // Close the ResultSet
        statement.close();                     // Close the Statement

        //pour le test
        ArrayList<String> photoE = new ArrayList<>();
        for (int i = 0; i < nomE.size(); i++) {
            try{
                String s = "v0/PDF/img/" + nomE.get(i).replace(" ", "").toLowerCase() + ".png";
                FileReader fr = new FileReader(s);
                photoE.add(s);
            }catch (Exception e) {
                System.out.println("Erreur lors de la récupération de l'image");
                photoE.add(IMG);
            }
        }
        //TODO : Récupérer les images dans la BDD

        GenererPdf pdf = new GenererPdf(DEST);
        pdf.manipulatePdf(nomE, photoE, nomPdf);
    }

    
}
