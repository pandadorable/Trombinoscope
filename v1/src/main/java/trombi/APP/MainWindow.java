package trombi.APP;

import io.qt.widgets.QTabWidget;
import io.qt.widgets.QWidget;
import trombi.BDD.ImportXlsx;
import trombi.CAMERA.CameraWindow;
import trombi.PDF.GenPdf;

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
        ImportXlsx btnXlsx = new ImportXlsx(listTab);
        GenPdf btnPdf = new GenPdf(listTab, "Trombi.pdf");

        

    }
   
}