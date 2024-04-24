package trombi.BDD.MODIF_BDD;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import trombi.BDD.listsType.colonnes;

public class ModifBDD_subwidget extends Pane {
    Label titre;
    List col_name;
    ComboBox listCol;
    Label valeur;
    TextArea champsModif;

    public ModifBDD_subwidget(String titreString) {
        titre = new Label();
        titre.setText(titreString);

        col_name = new ArrayList<String>();
        col_name.add("");
        for (colonnes iterable_element : colonnes.values()) {
            col_name.add(iterable_element.toString());
        }
        listCol = new ComboBox<String>(FXCollections.observableList(col_name));

        valeur = new Label();
        valeur.setText("Quelles valeurs y mettre : ");

        champsModif = new TextArea();
        champsModif.setPrefRowCount(1);
        champsModif.setPrefWidth(300);
        move(0, 0);

        this.getChildren().add(titre);
        this.getChildren().add(listCol);
        this.getChildren().add(valeur);
        this.getChildren().add(champsModif);
    }



    public void move(int x, int y) {
        titre.setLayoutX(5+x);
        titre.setLayoutY(5+y);
        listCol.setLayoutX(5+x);
        listCol.setLayoutY(40+y);
        valeur.setLayoutX(120+x);
        valeur.setLayoutY(45+y);
        champsModif.setLayoutX(280+x);
        champsModif.setLayoutY(40+y);
    }

    public String getColonne() {
        return listCol.getSelectionModel().getSelectedItem().toString();
    }

    public String getValeur() {
        return champsModif.getText();
    }
}
