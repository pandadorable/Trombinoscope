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
import io.qt.multimedia.QImageCapture.Quality;
import io.qt.multimedia.widgets.QVideoWidget;
import io.qt.widgets.*;
import trombi.BDD.MariaDB;

public class CameraWindow {
    private QImageCapture imageCapture;
    QLineEdit champEmail;
    private String email;
    QMediaCaptureSession captureSession;

    public CameraWindow(QWidget widgetParent) {
        // Liste des cameras disponibles
        QList<QCameraDevice> cameras = new QList<QCameraDevice>(QMediaDevices.getVideoInputs());
        QComboBox cameraList = new QComboBox(widgetParent);
        cameraList.resize(widgetParent.getWidth(), 35);
        for (QCameraDevice camera : cameras) {
            cameraList.addItem(camera.getDescription());
        }

        captureSession = new QMediaCaptureSession();
        if (!cameras.isEmpty()) {
            QCamera camera = new QCamera(cameras.get(0));
            captureSession.setCamera(camera);
            QVideoWidget viewfinder = new QVideoWidget(widgetParent);
            viewfinder.show();
            captureSession.setVideoOutput(viewfinder);
            imageCapture = new QImageCapture(camera);
            traitementImage(imageCapture);
            /*imageCapture.setResolution(500, 500); //Resolution des images
            imageCapture.setQuality(Quality.VeryLowQuality);  //Gestion de la qualité*/
            captureSession.setImageCapture(imageCapture);
            camera.start();

            // Taille
            viewfinder.resize(500, 400);

            // Position
            viewfinder.move(50, 50);

            champEmail = new QLineEdit(widgetParent);
            champEmail.move(5, 500);
            champEmail.resize(150, 40);
            String mail = champEmail.text();

            QPushButton emailButton = new QPushButton("Valider email", widgetParent);
            emailButton.move(160, 500);
            emailButton.resize(150, 40);
            emailButton.clicked.connect(this, "setEmail()");
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

    public void traitementImage(QImageCapture image, int width, int height, Quality quality) {
        imageCapture.setResolution(width, height); //Resolution des images
        imageCapture.setQuality(quality);  //Qualité des images
    }

    public void traitementImage(QImageCapture image) {
        imageCapture.setResolution(500, 280); //Resolution des images
        imageCapture.setQuality(Quality.VeryHighQuality);  //Qualité des images
    }

    public void traitementImage(QImageCapture image, Quality quality) {
        imageCapture.setResolution(500, 500); //Resolution des images
        imageCapture.setQuality(quality);  //Qualité des images
    }

    public QMediaCaptureSession getCaptureSession() {
        return captureSession;
    }

    public void setEmail() {
        email = champEmail.text();
    }

    public void capture() {
        // mettre chemin absolue du rep du projet dans les parenthèses
        // /cheminabsolue/nomDuFichier
        if(this.imageCapture == null) 
        {
            try {
                MariaDB.insertImage(email, "mario.png");
            } catch (IOException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            int capture = this.imageCapture.captureToFile();
            String id = "image_";
            if(capture < 1000) id+='0';
            if(capture < 100) id+='0';
            if(capture < 10) id+='0';
            id += capture+".jpg";
            File imageExist = new File(id);
            if(!(imageExist.exists())) {id = "mario.png";}
            /*try {
                System.out.println("ici");
                MariaDB.insertImage(email, id);
            } catch (IOException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        } 
    }
}
