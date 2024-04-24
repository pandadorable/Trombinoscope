package trombi.APP;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import trombi.BDD.ImportWindow;
import trombi.BDD.MODIF_BDD.ModifBDDWindow;
import trombi.CAMERA.CameraWindow;
import trombi.PDF.PDFWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;


public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TROMBESIR");
        // PAGE AVEC DES ONGLETS
        TabPane root = new TabPane();
        // ON NE PEUT PAS FERMER LES ONGLETS
        root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        // CAMERA
        Tab tab1 = new Tab();
        tab1.setText("CAMERA");
        CameraWindow onglet_camera = new CameraWindow();
        tab1.setContent(onglet_camera);
        root.getTabs().add(tab1);

        // MODIF BDD
        Tab tab2 = new Tab();
        tab2.setText("MODIFFICATION BDD");
        ModifBDDWindow onglet_modif = new ModifBDDWindow();
        tab2.setContent(onglet_modif);
        root.getTabs().add(tab2);

        // IMPORT IMAGE
        Tab tab3 = new Tab();
        tab3.setText("IMPORTER IMAGE");
        ImportWindow onglet_import = new ImportWindow();
        tab3.setContent(onglet_import);
        root.getTabs().add(tab3);

        // GENERER PDF
        Tab tab4 = new Tab();
        tab4.setText("GENERER PDF");
        PDFWindow onglet_pdf = new PDFWindow();
        tab4.setContent(onglet_pdf);
        root.getTabs().add(tab4);

        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }

    // public MainWindow() {

    // // Creer listTab
    // QTabWidget listTab = new QTabWidget(this);
    // listTab.resize(getMaximumSize());

    // /*
    // * CAMERA
    // */
    // // Creer tab camera
    // QWidget widCam = new QWidget();

    // // Ajouter la tab au Qtabwidg
    // listTab.addTab(widCam, "Caméra");

    // // Onglet Camera
    // cameraWindow = new CameraWindow(widCam);

    // /*
    // * MODIF BDD
    // */
    // // Creer tab modif BDD
    // QWidget widBDD = new QWidget();

    // // Ajouter la tab au Qtabwidg
    // listTab.addTab(widBDD, "Modification BDD");

    // modifBDDWindow = new ModifBDDWindow(widBDD);

    // /*
    // * IMPORT IMAGE
    // */
    // // Creer tab modif BDD
    // QWidget widImport = new QWidget();

    // // Ajouter la tab au Qtabwidg
    // listTab.addTab(widImport, "Importer photo");

    // ImportWindow importWindow = new ImportWindow(widImport);

    // /*
    // * GENERER PDF
    // */
    // // Creer tab modif BDD
    // QWidget widPDF = new QWidget();

    // // Ajouter la tab au Qtabwidg
    // listTab.addTab(widPDF, "Générer trombinoscope");

    // PDFWindow pdfWindow = new PDFWindow(widPDF);

    // /*
    // * BOUTONS
    // */
    // // Bouton Xlsx
    // QPushButton btnXlsx = new QPushButton("Importer XLSX", listTab);
    // btnXlsx.move(460, listTab.getY() + 2);
    // // Action xlsx
    // // Selectionner le fichier
    // btnXlsx.clicked.connect(this, "openFile()");
    // }

    // void openFile() {
    // @NonNull
    // Result<@NonNull String> file = QFileDialog.getOpenFileName(this, "Open
    // Xlsx");
    // if (file != null) {
    // // Chemin du fichier excel
    // String filePath = file.result;

    // // Traitement ... A faire
    // try {
    // MariaDB.transformXLSXToBDD(filePath);
    // } catch (SQLException e) {
    // e.printStackTrace();
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
}
