package trombi.PDF;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

public class PDFWindow extends Pane {
    // TODO mettre les colonnes de condition
    // TODO mettre les conditions
    String[] columnCondition = {"SPECIALITE"};
    String[] conditions = {"informatique"};
    public PDFWindow(){
        Button pdfButton = new Button("Générer trombinoscope");
        pdfButton.setLayoutX(5);
        pdfButton.setLayoutY(500);
        pdfButton.resize(150, 40);
        pdfButton.setOnAction((envent) -> { pdf.pdf(columnCondition, conditions); });
        this.getChildren().add(pdfButton);

        CheckBox wanted = new CheckBox("Colonnes voulues");
        wanted.setLayoutX(5);
        wanted.setLayoutY(100);
        wanted.resize(150, 100);
        this.getChildren().add(wanted);
    }
}
