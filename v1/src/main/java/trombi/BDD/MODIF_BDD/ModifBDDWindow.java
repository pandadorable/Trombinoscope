package trombi.BDD.MODIF_BDD;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import trombi.BDD.MariaDB;

public class ModifBDDWindow extends Pane {

    public static ModifBDD_subwidget tSubwidget;
    public static ArrayList<ModifBDD_subwidget> listCond;
    public static int nb_cond_bdd;

    public ModifBDDWindow() {

        tSubwidget = new ModifBDD_subwidget("Quelle colonne voulez vous modifier : ");
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

        Button verifButton = new Button();
        verifButton.setText("VERIFICATION");
        verifButton.setLayoutX(450);
        verifButton.setLayoutY(10);
        verifButton.setOnAction((event) -> {
            if (checkAllBoxusable()) {
                String[] colC = new String[nb_cond_bdd];
                String[] cond = new String[nb_cond_bdd];
                for (int i = 0; i < nb_cond_bdd; i++) {
                    colC[i] = listCond.get(i).getColonne();
                    cond[i] = listCond.get(i).getValeur();
                }
                try {
                    System.out.println(MariaDB.howMuchDataExist(colC, cond, MariaDB.typeCondition.AND));
                } catch (FileNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Des cases ne sont pas remplies");
            }
        });
        this.getChildren().add(verifButton);
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