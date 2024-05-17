package trombi.PDF;

import java.sql.ResultSet;
import java.util.ArrayList;

import trombi.APP.Main;
import trombi.BDD.MariaDB;

public class pdf {

    public static void pdf(String[] columnCondition, String[] conditions, boolean needTrombi, boolean needEmarg) {
        ArrayList<String> nomEleve = new ArrayList<>();
        ArrayList<String> mailELeve = new ArrayList<>();
        String[] columnWanted = { "nom", "prenom", "email" };
        try {
            ResultSet resultSet = MariaDB.autoRequest(columnWanted, columnCondition, conditions,
                    MariaDB.typeCondition.OR);

            while (resultSet.next()) { // Position the cursor

                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String mail = resultSet.getString("email");
                /*
                 * System.out.print("Nom = " + nom + " ; Prénom = " + prenom);
                 * System.out.println();
                 */
                nomEleve.add(nom + "\n" + prenom);
                mailELeve.add(mail);
            }
            resultSet.close();

            if (needTrombi) {
                GenererPdf trombi = new GenererPdf(Main.path+"/trombi.pdf");
                trombi.manipulatePdf(nomEleve, mailELeve, "Trombinoscope");
            }
            if (needEmarg) {
                GenererEmargement emargement = new GenererEmargement("emargement.pdf");
                emargement.manipulatePdf(nomEleve, "emargement");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PDF généré");
        PDFWindow.verifLabel.setText("Les PDF ont été générés");
    }
}
