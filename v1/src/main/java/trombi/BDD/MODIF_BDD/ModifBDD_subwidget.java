package trombi.BDD.MODIF_BDD;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import trombi.BDD.listType.colonnes;

public class ModifBDD_subwidget extends Pane {

    public ModifBDD_subwidget(String titreString) {
        Label titre = new Label();
        titre.setLayoutX(5);
        titre.setLayoutY(5);
        titre.setText(titreString);

        List<String> col_name = new ArrayList<String>();
        col_name.add("");
        for (colonnes iterable_element : colonnes.values()) {
            col_name.add(iterable_element.toString());
        }
        ComboBox<String> listCol = new ComboBox<String>(FXCollections.observableList(col_name));
        listCol.setLayoutX(5);
        listCol.setLayoutY(40);

        Label valeur = new Label();
        valeur.setText("Quelle valeur de condition: ");
        valeur.setLayoutX(120);
        valeur.setLayoutY(45);

        TextArea champsModif = new TextArea();
        champsModif.setPrefRowCount(1);
        champsModif.setPrefWidth(300);
        champsModif.setLayoutX(280);
        champsModif.setLayoutY(40);

        this.getChildren().add(titre);
        this.getChildren().add(listCol);
        this.getChildren().add(valeur);
        this.getChildren().add(champsModif);
    }

    @SuppressWarnings("unchecked")
    public String getColonne() {
        if(((ComboBox<String>)this.getChildren().get(1)).getSelectionModel().getSelectedItem() == null) return "";
        return ((ComboBox<String>)this.getChildren().get(1)).getSelectionModel().getSelectedItem().toString();
    }

    public String getValeur() {
        return ((TextArea)this.getChildren().get(3)).getText();
    }
}
