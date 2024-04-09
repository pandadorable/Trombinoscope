package trombi.CAMERA;

import io.qt.NonNull;
import io.qt.widgets.*;
import trombi.BDD.MariaDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ImportWindow extends QWidget {
    QLineEdit champEmail;
    private String email;
    private String filePath;
    private boolean correctEmail;

    public ImportWindow(QWidget widgetParent) {
        champEmail = new QLineEdit(widgetParent);
        champEmail.move(5, 150);
        champEmail.resize(150, 40);
        String mail = champEmail.text();

        QLabel verifEmail = new QLabel(widgetParent);
        verifEmail.move(5, 100);
        verifEmail.resize(150,40);
        champEmail.textChanged.connect(this,"verifEmail()");

        QPushButton emailButton = new QPushButton("Valider email", widgetParent);
        emailButton.move(5, 200);
        emailButton.resize(150, 40);
        emailButton.clicked.connect(this, "setEmail()");

        QPushButton photoButton = new QPushButton("Choix photo", widgetParent);
        photoButton.move(150, 200);
        photoButton.resize(150, 40);

        photoButton.clicked.connect(this, "photo()");

        QPushButton valiButton = new QPushButton("Valider", widgetParent);
        valiButton.move(100, 300);
        valiButton.resize(150, 40);

        valiButton.clicked.connect(this, "importPhoto()");

    }

    public void photo() {
        QFileDialog.Result<@NonNull String> file = QFileDialog.getOpenFileName(this, "Importer photo");
        filePath = file.result;
    }

    public void setEmail() {
        email = champEmail.text();
    }

    void importPhoto() {
        // Traitement ... A faire
        try {
            MariaDB.insertImage(email, filePath);
        } catch (IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void verifEmail() throws SQLException, FileNotFoundException {
        correctEmail = MariaDB.isMailExist(email);

    }

}
