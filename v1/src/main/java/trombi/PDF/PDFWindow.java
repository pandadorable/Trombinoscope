package trombi.PDF;

import io.qt.widgets.QCheckBox;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QWidget;

import static trombi.APP.MainWindow.cameraWindow;

public class PDFWindow extends QWidget {
    // TODO mettre les colonnes voulues
    // TODO mettre les colonnes de condition
    // TODO mettre les conditions
    String[] columnWanted;
    String[] columnCondition;
    String[] conditions;
    public PDFWindow(QWidget widgetParent){
        QPushButton pdfButton = new QPushButton("Générer trombinoscope",widgetParent);
        pdfButton.move(5, 500);
        pdfButton.resize(150, 40);
        pdfButton.clicked.connect(this, "genererPDF()");

        QCheckBox wanted = new QCheckBox("Colonnes voulues", widgetParent);
        wanted.move(5, 100);
        wanted.resize(150, 100);
    }

    void genererPDF() {
        pdf.pdf(columnWanted,columnCondition,conditions);
        cameraWindow.getCaptureSession().getVideoOutput();
    }
}
