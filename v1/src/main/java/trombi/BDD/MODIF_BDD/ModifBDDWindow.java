package trombi.BDD.MODIF_BDD;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import trombi.BDD.MariaDB;

public class ModifBDDWindow extends Pane {

    public static ModifBDD_subwidget tSubwidget;
    public static ArrayList<ModifBDD_subwidget> listCond;
    public static int nb_cond_bdd;

    List<String> verif_colC;
    List<String> verif_cond;

    public ModifBDDWindow() {

        tSubwidget = new ModifBDD_subwidget("Quelle colonne voulez vous modifier : ");
        ((Label) tSubwidget.getChildren().get(2)).setText("Quelle valeur y mettre : ");
        this.getChildren().add(tSubwidget);
        Label condiLabel = new Label();
        condiLabel.setLayoutX(5);
        condiLabel.setLayoutY(95);
        condiLabel.setText("Pour quelle(s) condition(s) : ");
        this.getChildren().add(condiLabel);

        listCond = new ArrayList<>();
        init_list_cond();
        for (ModifBDD_subwidget iterable_element : listCond) {
            this.getChildren().add(iterable_element);
        }
        ModifBDD_plus_ou_moins_subwidget line = new ModifBDD_plus_ou_moins_subwidget();
        this.getChildren().add(line);

        // Séparateur
        Line line2 = new Line(620, 20, 620, 520);
        this.getChildren().add(line2);

        // Label de vérification
        Label verif_label = new Label();
        verif_label.setLayoutX(650);
        verif_label.setLayoutY(60);
        verif_label.setVisible(false);
        this.getChildren().add(verif_label);

        // Bouton de vérification
        Button verifButton = new Button();
        verifButton.setText("VERIFICATION");
        verifButton.setLayoutX(650);
        verifButton.setLayoutY(20);
        verifButton.setOnAction((event) -> {
            if (checkAllBoxusable()) {
                verif_label.setText("Vérification en cours...");
                verif_label.setVisible(true);
                String[] colC = new String[nb_cond_bdd];
                String[] cond = new String[nb_cond_bdd];
                verif_colC = new ArrayList<>();
                verif_cond = new ArrayList<>();
                for (int i = 0; i < nb_cond_bdd; i++) {
                    colC[i] = listCond.get(i).getColonne();
                    cond[i] = listCond.get(i).getValeur();
                    verif_colC.add(colC[i]);
                    verif_cond.add(cond[i]);
                }
                try {
                    verif_label.setText("Cette modification va altérer "
                            + MariaDB.howMuchDataExist(colC, cond, MariaDB.typeCondition.AND)
                            + " Étudiants.\nSi vous êtes sûr appuyez sur confirmer.");
                    verif_label.setVisible(true);
                } catch (FileNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            } else {
                verif_label.setText("Des cases ne sont pas remplies");
                verif_label.setVisible(true);
            }
        });
        this.getChildren().add(verifButton);

        // Bouton de confirmation
        Button confirmButton = new Button();
        confirmButton.setText("CONFIRMATION");
        confirmButton.setLayoutX(780);
        confirmButton.setLayoutY(20);
        confirmButton.setOnAction((event) -> {
            if (checkAllBoxusable()) {
                if (verif_colC != null && nb_cond_bdd == verif_colC.size()) {
                    String[] colC = new String[nb_cond_bdd];
                    String[] cond = new String[nb_cond_bdd];
                    for (int i = 0; i < nb_cond_bdd; i++) {
                        colC[i] = listCond.get(i).getColonne();
                        cond[i] = listCond.get(i).getValeur();
                    }
                    boolean isTheSame = true;
                    for (int i = 0; i < nb_cond_bdd; i++) {
                        isTheSame = isTheSame && colC[i].equals(verif_colC.get(i)) && cond[i].equals(verif_cond.get(i));
                    }
                    if (isTheSame) {
                        verif_label.setText("Les modifications ont été envoyées");
                        verif_label.setVisible(true);
                        try {
                            MariaDB.modifRequest(tSubwidget.getColonne(), tSubwidget.getValeur(), colC, cond,
                                    MariaDB.typeCondition.AND);
                        } catch (FileNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        verif_label.setText(
                                "Les modifications actuelles n'ont pas été vérifiées, veuillez cliquer sur vérifier");
                        verif_label.setVisible(true);
                    }
                } else {
                    verif_label.setText(
                            "Les modifications actuelles n'ont pas été vérifiées, veuillez cliquer sur vérifier");
                    verif_label.setVisible(true);
                }
            } else {
                verif_label.setText("Des cases ne sont pas remplies");
                verif_label.setVisible(true);
            }
        });
        this.getChildren().add(confirmButton);

        // Label de confirmation
        Label confirm_label = new Label();
        confirm_label.setLayoutX(910);
        confirm_label.setLayoutY(500);
        this.getChildren().add(confirm_label);

        // Import XLSX
        Button importXLS = new Button();
        importXLS.setText("IMPORTER XLSX");
        importXLS.setLayoutX(650);
        importXLS.setLayoutY(500);
        importXLS.setOnAction((event) -> {
            JFileChooser dialogue = new JFileChooser(".");
            File fichier;

            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fichier = dialogue.getSelectedFile();
                try {
                    // export de l'ancien état de la BDD au cas ou
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                    Date date = new Date();
                    MariaDB.transformBDDtoXLS("ETAT_BASE_DE_DONNEES_("+dateFormat.format(date)+").xlsx", confirm_label);
                    confirm_label.setText("Base de données en cours de mise à jour");
                    MariaDB.transformXLSXToBDD(fichier.getAbsolutePath(), confirm_label);

                } catch (FileNotFoundException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        this.getChildren().add(importXLS);

        // Export XLSX
        Button exportXLS = new Button();
        exportXLS.setText("EXPORTER XLSX");
        exportXLS.setLayoutX(780);
        exportXLS.setLayoutY(500);
        exportXLS.setOnAction((event) -> {
            MariaDB.transformBDDtoXLS("SAUVEGARDE.xlsx", confirm_label);

        });
        this.getChildren().add(exportXLS);

    }

    public static void init_list_cond() {
        nb_cond_bdd = 0;
        for (int i = 0; i < 5; i++) {
            ModifBDD_subwidget tmp = new ModifBDD_subwidget("");
            tmp.setLayoutY(95 + i * 50);
            tmp.setVisible(false);
            ModifBDDWindow.listCond.add(tmp);
        }
    }

    public static boolean checkAllBoxusable() {
        boolean titre = !tSubwidget.getColonne().equals("") && !tSubwidget.getValeur().equals("");
        boolean cond = true;
        for (int i = 0; i < nb_cond_bdd; i++) {
            cond = cond && !listCond.get(i).getColonne().equals("") && !listCond.get(i).getValeur().equals("");
        }
        return titre && cond;
    }

}