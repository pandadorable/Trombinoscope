package trombi.BDD.MODIF_BDD;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ModifBDD_plus_ou_moins_subwidget extends Pane {

    public static int start_offset = 125;
    public static int offset_per_cond = 50;
    public static Button plus;
    public static Button moins;

    public ModifBDD_plus_ou_moins_subwidget() {
        plus = new Button();
        plus.setText("+");
        plus.resize(45, 25);
        plus.setOnAction((event) -> {
            System.out.println("plus");
            if (ModifBDDWindow.nb_cond_bdd < ModifBDDWindow.listCond.size()) {
                ModifBDDWindow.listCond.get(ModifBDDWindow.nb_cond_bdd).setVisible(true);
                ModifBDDWindow.nb_cond_bdd++;
                refreshPosition();
            }
        });
        this.getChildren().add(plus);

        moins = new Button();
        moins.setText("-");
        moins.resize(45, 25);
        moins.setOnAction((event) -> {
            System.out.println("moins");
            if (ModifBDDWindow.nb_cond_bdd > 0) {
                ModifBDDWindow.nb_cond_bdd--;
                ModifBDDWindow.listCond.get(ModifBDDWindow.nb_cond_bdd).setVisible(false);
                refreshPosition();
            }
        });
        this.getChildren().add(moins);

        refreshPosition();
    }

    public void refreshPosition() {
        plus.setLayoutX(25);
        plus.setLayoutY(start_offset + ModifBDDWindow.nb_cond_bdd * offset_per_cond);
        moins.setLayoutX(75);
        moins.setLayoutY(start_offset + ModifBDDWindow.nb_cond_bdd * offset_per_cond);
    }
}
