package trombi.CAMERA;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import io.qt.core.QList;
import io.qt.multimedia.QCamera;
import io.qt.multimedia.QCameraDevice;
import io.qt.multimedia.QImageCapture;
import io.qt.multimedia.QMediaCaptureSession;
import io.qt.multimedia.QMediaDevices;
import io.qt.multimedia.widgets.QVideoWidget;
import io.qt.widgets.QComboBox;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QTabWidget;
import io.qt.widgets.QWidget;
import trombi.BDD.MariaDB;

public class CameraWindow {
    private QImageCapture imageCapture;

    public CameraWindow(QWidget widgetParent) {
        // Liste des cameras disponibles
        QList<QCameraDevice> cameras = new QList<QCameraDevice>(QMediaDevices.getVideoInputs());
        QComboBox cameraList = new QComboBox(widgetParent);
        cameraList.resize(widgetParent.getWidth(), 35);
        for (QCameraDevice camera : cameras) {
            cameraList.addItem(camera.getDescription());
        }

        QMediaCaptureSession captureSession = new QMediaCaptureSession();
        if (!cameras.isEmpty()) {
            QCamera camera = new QCamera(cameras.get(0));
            captureSession.setCamera(camera);
            QVideoWidget viewfinder = new QVideoWidget(widgetParent);
            viewfinder.show();
            captureSession.setVideoOutput(viewfinder);

            imageCapture = new QImageCapture(camera);
            captureSession.setImageCapture(imageCapture);
            camera.start();

            // Taille
            viewfinder.resize(500, 400);

            // Position
            viewfinder.move(50, 50);
        }

        // Onglet nom et bouton de capture
        QTabWidget listTabcapture = new QTabWidget(widgetParent);
        QWidget widCapture = new QWidget();
        listTabcapture.addTab(widCapture, "Capture");
        listTabcapture.move(640, 0);
        listTabcapture.resize(160, widgetParent.getMaximumHeight());
        QPushButton capButton = new QPushButton("Capture", widCapture);
        capButton.move(5, 200);
        capButton.resize(150, 40);

        capButton.clicked.connect(this, "capture()");

    }

    public void capture() {
        // mettre chemin absolue du rep du projet dans les parenthèses
        // /cheminabsolue/nomDuFichier
        if(this.imageCapture == null) 
        {
            try {
                MariaDB.insertImage("mario.bros@univ-rennes.fr", "mario.png");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            int capture = this.imageCapture.captureToFile("/home/rolyster/Documents/ProjetSI/Trombinoscope/");
            String id = "image_";
            if(capture < 1000) id+='0';
            if(capture < 100) id+='0';
            if(capture < 10) id+='0';
            id += capture+".jpg";
            File imageExist = new File(id);
            if(!imageExist.exists()) id = "mario.png";
            try {
                MariaDB.insertImage("mario.bros@univ-rennes.fr", id);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } 
    }

}
