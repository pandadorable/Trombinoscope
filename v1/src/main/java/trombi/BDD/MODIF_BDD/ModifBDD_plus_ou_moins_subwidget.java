package trombi.BDD.MODIF_BDD;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ModifBDD_plus_ou_moins_subwidget extends Pane {

    public static int start_offset = 125;
    public static int offset_per_cond = 50;
    public Button plus;
    public Button moins;

    public ModifBDD_plus_ou_moins_subwidget() {
        plus = new Button();
        plus.setText("+");
        plus.resize(45, 25);
        plus.setLayoutX(25);
        plus.setOnAction((event) -> {
            System.out.println("plus");
            if (ModifBDDWindow.nb_cond_bdd < ModifBDDWindow.listCond.size()) {
                ModifBDDWindow.listCond.get(ModifBDDWindow.nb_cond_bdd).setVisible(true);
                ModifBDDWindow.nb_cond_bdd++;
                refreshPosition();
            }
        });
        

        moins = new Button();
        moins.setText("-");
        moins.resize(45, 25);
        moins.setLayoutX(75);
        moins.setOnAction((event) -> {
            System.out.println("moins");
            if (ModifBDDWindow.nb_cond_bdd > 0) {
                ModifBDDWindow.nb_cond_bdd--;
                ModifBDDWindow.listCond.get(ModifBDDWindow.nb_cond_bdd).setVisible(false);
                refreshPosition();
            }
        });
        

        refreshPosition();
        this.getChildren().add(plus);
        this.getChildren().add(moins);
    }

    public void refreshPosition() {
        this.setLayoutY(start_offset + ModifBDDWindow.nb_cond_bdd * offset_per_cond);
    }
}
