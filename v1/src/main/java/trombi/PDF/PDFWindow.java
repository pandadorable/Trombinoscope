package trombi.PDF;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import trombi.BDD.MariaDB;
import trombi.BDD.MODIF_BDD.ModifBDDWindow;
import trombi.BDD.MODIF_BDD.ModifBDD_subwidget;

public class PDFWindow extends Pane {

    static int max_cond = 6;
    static int nb_cond_trombi = 0;
    static List<PDF_subwidget> listCond;

    static Label verifLabel;

    public PDFWindow() {
        // Séparateur
        Line line2 = new Line(620, 20, 620, 520);
        this.getChildren().add(line2);

        Label Title = new Label("GÉNÉRATION TROMBINOSCOPE / ÉMARGEMENT");
        Title.setLayoutX(20);
        Title.setLayoutY(20);
        this.getChildren().add(Title);

        // list condition
        listCond = new ArrayList<>();
        for (int i = 0; i < max_cond; i++) {
            PDF_subwidget tmp = new PDF_subwidget("");
            tmp.setLayoutY(15 + i * 60);
            tmp.setVisible(false);
            listCond.add(tmp);
        }
        for (PDF_subwidget iterable_element : listCond) {
            this.getChildren().add(iterable_element);
        }

        PDF_plus_ou_moins_subwidget line = new PDF_plus_ou_moins_subwidget();
        this.getChildren().add(line);

        // label
        verifLabel = new Label("...");
        verifLabel.setLayoutX(270);
        verifLabel.setLayoutY(505);
        verifLabel.setVisible(true);
        this.getChildren().add(verifLabel);

        CheckBox mailCheck = new CheckBox("Ajouter les mails en bas de page");
        mailCheck.setLayoutX(20);
        mailCheck.setLayoutY(460);
        this.getChildren().add(mailCheck);

        CheckBox trombiCheck = new CheckBox("Trombinoscope");
        trombiCheck.setLayoutX(20);
        trombiCheck.setLayoutY(490);
        this.getChildren().add(trombiCheck);

        CheckBox emargCheck = new CheckBox("Émargement");
        emargCheck.setLayoutX(20);
        emargCheck.setLayoutY(520);
        this.getChildren().add(emargCheck);

        Button createPDF = new Button("Générer");
        createPDF.setLayoutX(180);
        createPDF.setLayoutY(500);
        createPDF.setOnAction((envent) -> {
            if (checkAllBoxusable()) {
                if (trombiCheck.isSelected() || emargCheck.isSelected()) {
                    verifLabel.setText("Vérification en cours...");
                    String[] colC = new String[nb_cond_trombi];
                    String[] cond = new String[nb_cond_trombi];
                    for (int i = 0; i < nb_cond_trombi; i++) {
                        colC[i] = listCond.get(i).getColonne();
                        cond[i] = listCond.get(i).getValeur();
                    }
                    verifLabel.setText("Génération du Trombinoscope en cours....");
                    pdf.pdf(colC, cond, trombiCheck.isSelected(), emargCheck.isSelected(), mailCheck.isSelected(),"");
                } else {
                    verifLabel.setText("Aucune des sorties ne sont sélectionnées");
                }
            } else {
                verifLabel.setText("Des cases ne sont pas remplies");
            }

        });
        this.getChildren().add(createPDF);
    }

    public static boolean checkAllBoxusable() {
        boolean cond = true;
        for (int i = 0; i < nb_cond_trombi; i++) {
            cond = cond && !listCond.get(i).getColonne().equals("") && !listCond.get(i).getValeur().equals("");
        }
        return cond;
    }
}
