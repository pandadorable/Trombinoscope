package trombi.PDF;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import trombi.BDD.MariaDB;

public class pdf {

    public static void pdf() {
        ArrayList<String> nomEleve = new ArrayList<>();
        ArrayList<String> mailELeve = new ArrayList<>();
        try {
            String[] collomnWanted = {"nom","prenom","email"};
            String[] collomnCondition = {"ang","ang"};
            String[] conditions = {"G1","G3"};
            ResultSet resultSet = MariaDB.autoRequest(collomnWanted, collomnCondition, conditions,MariaDB.typeCondition.OR);

            while (resultSet.next()) {               // Position the cursor
                
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String mail = resultSet.getString("email");
                /*
                System.out.print("Nom = " + nom + " ; Prénom = " + prenom);
                System.out.println();
                */
                nomEleve.add(nom + " " + prenom);
                mailELeve.add(mail);
            }
            resultSet.close();

            GenererPdf trombi = new GenererPdf("OUTPUT/trombi.pdf");
            trombi.manipulatePdf(nomEleve, mailELeve, "Trombinoscope");
            GenererEmargement emargement = new GenererEmargement("OUTPUT/emargement.pdf");
            emargement.manipulatePdf(nomEleve, "emargement");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PDF généré");
    }
}
