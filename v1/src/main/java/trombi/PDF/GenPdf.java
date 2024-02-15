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
        GenererPdf pdf = new GenererPdf(destinationFile);

        //pour le test
        ArrayList<String>  nomE = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            int j = i+1;
            nomE.set(i, "Elève "+ j);
        }
        
        //pour le test
         ArrayList<String> mailE = new  ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mailE.set(i, nomE.get(i)+"@etudiant.uuniv-rennes.fr") ;
        }

        //nom de pdf pour le test
        String nomPdf = "Trombi.pdf";

        pdf.manipulatePdf(nomE, mailE, nomPdf);
    }
}
