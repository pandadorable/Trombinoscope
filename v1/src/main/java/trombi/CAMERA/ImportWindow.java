package trombi.CAMERA;

import io.qt.NonNull;
import io.qt.core.QSize;
import io.qt.gui.QImage;
import io.qt.gui.QImageReader;
import io.qt.widgets.*;
import trombi.BDD.MariaDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ImportWindow extends QWidget {
    QLineEdit champEmail;
    private QLabel verifEmail;
    private String email;
    private String filePath;

    public ImportWindow(QWidget widgetParent) {
        champEmail = new QLineEdit(widgetParent);
        champEmail.move(5, 150);
        champEmail.resize(150, 40);

        verifEmail = new QLabel(widgetParent);
        verifEmail.move(5, 100);
        verifEmail.resize(150, 40);
        champEmail.textEdited.connect(this, "verifEmail()");

        QPushButton photoButton = new QPushButton("Choix photo", widgetParent);
        photoButton.move(5, 200);
        photoButton.resize(150, 40);
        photoButton.clicked.connect(this, "photo()");

        QPushButton valiButton = new QPushButton("Valider", widgetParent);
        valiButton.move(5, 300);
        valiButton.resize(150, 40);
        valiButton.clicked.connect(this, "importPhoto()");
    }

    /**
     * Choix de la photo à associer
     */
    public void photo() {
        QFileDialog.Result<@NonNull String> file = QFileDialog.getOpenFileName(this, "Importer photo");
        filePath = file.result;
        traitementImageImport(filePath, 500, 280);
    }

    /**
     * Association de la photo à l'adresse email
     */
    void importPhoto() {
        // Traitement ... A faire
        try {
            MariaDB.insertImage(email, filePath);
        } catch (IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Vérification de la présence de l'email entré dans la BDD
     *
     * @throws SQLException
     * @throws FileNotFoundException
     */
    void verifEmail() throws SQLException, FileNotFoundException {
        if (MariaDB.isMailExist(champEmail.text())) {
            email = champEmail.text();
            verifEmail.setText("Email valide Bravo :D");
        } else {
            verifEmail.setText("Email invalide, veuillez réessayer :/");
        }
    }

    void traitementImageImport(String filePath, int width, int height) {
        QImageReader imageReader = new QImageReader(filePath);
        imageReader.setScaledSize(new QSize(width, height));
        QImage pic = imageReader.read();
        pic.save("import.png");
    }
}
