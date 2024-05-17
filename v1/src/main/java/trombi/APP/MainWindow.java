package trombi.APP;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import trombi.CAMERA.ImportWindow;
import trombi.BDD.MODIF_BDD.ModifBDDWindow;
import trombi.CAMERA.CameraWindow;
import trombi.CAMERA.ImportWindow;
import trombi.PDF.PDFWindow;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;


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
        tab2.setText("MODIFICATION BDD");
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
}
