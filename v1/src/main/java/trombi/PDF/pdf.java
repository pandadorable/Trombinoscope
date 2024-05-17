package trombi.PDF;

import java.sql.ResultSet;
import java.util.ArrayList;

import trombi.APP.Main;
import trombi.BDD.MariaDB;

public class pdf {

    public static void pdf(String[] columnCondition, String[] conditions, boolean needTrombi, boolean needEmarg, boolean needMail) {
        ArrayList<String> nomEleve = new ArrayList<>();
        ArrayList<String> mailELeve = new ArrayList<>();
        String[] columnWanted = { "nom", "prenom", "email" };
        String nomPdf = "";
        
        for (int i = 0; i < columnCondition.length; i++) {
                nomPdf += columnCondition[i] + " " + conditions[i];
                if (i != columnCondition.length - 1){
                    nomPdf += " ";
                }
        }

        try {
            ResultSet resultSet = MariaDB.autoRequest(columnWanted, columnCondition, conditions,
                    MariaDB.typeCondition.OR);

            while (resultSet.next()) { // Position the cursor

                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String mail = resultSet.getString("email");
                nomEleve.add(nom + "\n" + prenom);
                mailELeve.add(mail);
            }
            resultSet.close();

            if (needTrombi) {
                GenererPdf trombi = new GenererPdf("Trombinoscope_"+nomPdf.replaceAll(" ", "_")+".pdf");
                trombi.manipulatePdf(nomEleve, mailELeve, "Trombinoscope "+nomPdf, needMail);
            }
            if (needEmarg) {
                GenererEmargement emargement = new GenererEmargement("Emargement_"+nomPdf.replaceAll(" ", "_")+".pdf");
                emargement.manipulatePdf(nomEleve, "Emargement "+nomPdf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PDF généré");
        PDFWindow.verifLabel.setText("Les PDF ont été générés");
    }
}
