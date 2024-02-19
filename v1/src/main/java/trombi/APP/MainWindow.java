package trombi.APP;

import java.sql.SQLException;

import io.qt.NonNull;
import io.qt.widgets.QFileDialog;
import io.qt.widgets.QFileDialog.Result;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QTabWidget;
import io.qt.widgets.QWidget;
import trombi.BDD.MariaDB;
import trombi.CAMERA.CameraWindow;
import trombi.PDF.pdf;

public class MainWindow extends QWidget{

    public MainWindow() {
        
       //Creer listTab
       QTabWidget listTab = new QTabWidget(this);
       listTab.resize(getMaximumSize());

       //Creer tab camera
       QWidget widCam = new QWidget();

       //Ajouter la tab au Qtabwidg
       listTab.addTab(widCam, "Caméra");

       //Onlget Camera
       CameraWindow cameraWindow = new CameraWindow(widCam);
        
        //Boutons pour le Xlsx et Pdf 

        QPushButton btnXlsx = new QPushButton("Importer XLSX", listTab);
        btnXlsx.move(78, listTab.getY() + 2);
        // Action xlsx
        // Selectionner le fichier
        btnXlsx.clicked.connect(this, "openFile()");

        QPushButton btnPdf = new QPushButton("Générer trombinoscope", listTab);
        btnPdf.move(188, listTab.getY()+2);
        btnPdf.clicked.connect(this, "genererPdf()");
    }

    void genererPdf() {
        pdf.pdf();
    }

    void openFile() {
        @NonNull
        Result<@NonNull String> file = QFileDialog.getOpenFileName(this,"Open Xlsx");
        if (file != null) {
            // Chemin du fichier excel
            String filePath = file.result;
            System.out.println(filePath);

            // Traitement ... A faire
            try {
                MariaDB.transformXLSXToBDD(filePath);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
   
}