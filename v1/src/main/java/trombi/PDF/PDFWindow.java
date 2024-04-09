package trombi.PDF;

import io.qt.widgets.QLabel;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QWidget;

import static trombi.APP.MainWindow.cameraWindow;

public class PDFWindow extends QWidget {
    public PDFWindow(QWidget widgetParent){
        QPushButton pdfButton = new QPushButton("Générer trombinoscope",widgetParent);
        pdfButton.move(5, 100);
        pdfButton.resize(150, 40);
        pdfButton.clicked.connect(this, "genererPDF()");
    }

    void genererPDF() {
        pdf.pdf();
        cameraWindow.getCaptureSession().getVideoOutput();
    }
}
