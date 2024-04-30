package trombi.PDF;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PDF_plus_ou_moins_subwidget extends Pane {

    public static int start_offset = 45;
    public static int offset_per_cond = 60;
    public Button plus;
    public Button moins;

    public PDF_plus_ou_moins_subwidget() {
        plus = new Button();
        plus.setText("+");
        plus.resize(45, 25);
        plus.setLayoutX(25);
        plus.setOnAction((event) -> {
            System.out.println("plus");
            if (PDFWindow.nb_cond_trombi < PDFWindow.listCond.size()) {
                PDFWindow.listCond.get(PDFWindow.nb_cond_trombi).setVisible(true);
                PDFWindow.nb_cond_trombi++;
                refreshPosition();
            }
        });

        moins = new Button();
        moins.setText("-");
        moins.resize(45, 25);
        moins.setLayoutX(75);
        moins.setOnAction((event) -> {
            System.out.println("moins");
            if (PDFWindow.nb_cond_trombi > 1) {
                PDFWindow.nb_cond_trombi--;
                PDFWindow.listCond.get(PDFWindow.nb_cond_trombi).setVisible(false);
                refreshPosition();
            }
        });

        refreshPosition();
        PDFWindow.listCond.get(PDFWindow.nb_cond_trombi).setVisible(true);
        PDFWindow.nb_cond_trombi++;
        refreshPosition();
        this.getChildren().add(plus);
        this.getChildren().add(moins);
    }

    public void refreshPosition() {
        this.setLayoutY(start_offset + PDFWindow.nb_cond_trombi * offset_per_cond);
    }
}
