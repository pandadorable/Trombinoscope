package trombi.APP;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import io.qt.NonNull;
import io.qt.widgets.QFileDialog;
import io.qt.widgets.QFileDialog.Result;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QTabWidget;
import io.qt.widgets.QWidget;
import trombi.BDD.MariaDB;
import trombi.BDD.MODIF_BDD.ModifBDDWindow;
import trombi.CAMERA.CameraWindow;
import trombi.CAMERA.ImportWindow;
import trombi.PDF.PDFWindow;
import trombi.PDF.pdf;

public class MainWindow extends QWidget {

    public static CameraWindow cameraWindow;
    public static ModifBDDWindow modifBDDWindow;

    public MainWindow() {

        // Creer listTab
        QTabWidget listTab = new QTabWidget(this);
        listTab.resize(getMaximumSize());

        /*
         * CAMERA
         */
        // Creer tab camera
        QWidget widCam = new QWidget();

        // Ajouter la tab au Qtabwidg
        listTab.addTab(widCam, "Caméra");

        // Onglet Camera
        cameraWindow = new CameraWindow(widCam);

        /*
         * MODIF BDD
         */
        // Creer tab modif BDD
        QWidget widBDD = new QWidget();

        // Ajouter la tab au Qtabwidg
        listTab.addTab(widBDD, "Modification BDD");

        modifBDDWindow = new ModifBDDWindow(widBDD);

        /*
         * IMPORT IMAGE
         */
        // Creer tab modif BDD
        QWidget widImport = new QWidget();

        // Ajouter la tab au Qtabwidg
        listTab.addTab(widImport, "Importer photo");

        ImportWindow importWindow = new ImportWindow(widImport);

        /*
         * GENERER PDF
         */
        // Creer tab modif BDD
        QWidget widPDF = new QWidget();

        // Ajouter la tab au Qtabwidg
        listTab.addTab(widPDF, "Générer trombinoscope");

        PDFWindow pdfWindow = new PDFWindow(widPDF);

        /*
         * BOUTONS
         */
        // Bouton Xlsx
        QPushButton btnXlsx = new QPushButton("Importer XLSX", listTab);
        btnXlsx.move(460, listTab.getY() + 2);
        // Action xlsx
        // Selectionner le fichier
        btnXlsx.clicked.connect(this, "openFile()");
    }

    void openFile() {
        @NonNull
        Result<@NonNull String> file = QFileDialog.getOpenFileName(this, "Open Xlsx");
        if (file != null) {
            // Chemin du fichier excel
            String filePath = file.result;

            // Traitement ... A faire
            try {
                MariaDB.transformXLSXToBDD(filePath);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
