package trombi.PDF;

import java.util.ArrayList;

import io.qt.widgets.QPushButton;
import io.qt.widgets.QTabWidget;

public class GenPdf {
    private String destinationFile;
    public GenPdf(QTabWidget widgetParent, String destinationFile) {
        this.destinationFile = destinationFile;
        QPushButton btnPdf = new QPushButton("Générer trombinoscope", widgetParent);
        btnPdf.move(188, widgetParent.getY()+2);
        btnPdf.clicked.connect(this, "genererPdf()");
    }

    void genererPdf() throws Exception {
        System.out.println("UwU");
        pdf.pdf();
    }
}
