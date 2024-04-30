package trombi.CAMERA;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.JFileChooser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import trombi.BDD.MariaDB;

public class ImportWindow extends Pane {
    TextArea champEmail;
    private Label verifEmail;
    private String email;
    private String filePath;

    ImageView image_temp = new ImageView();

    public ImportWindow() {
        champEmail = new TextArea();
        champEmail.setPrefRowCount(1);
        champEmail.setLayoutX(5);
        champEmail.setLayoutY(150);
        champEmail.resize(150, 40);
        this.getChildren().add(champEmail);

        verifEmail = new Label();
        verifEmail.setLayoutX(5);
        verifEmail.setLayoutY(100);
        verifEmail.resize(150, 40);
        this.getChildren().add(verifEmail);

        Button photoButton = new Button("Choix photo");
        photoButton.setLayoutX(5);
        photoButton.setLayoutY(200);
        photoButton.resize(150, 40);
        photoButton.setOnAction((envent) -> {
            JFileChooser dialogue = new JFileChooser(".");
            File fichier;

            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fichier = dialogue.getSelectedFile();
                image_temp.setImage(new Image(fichier.toURI().toString()));
            }
        });
        this.getChildren().add(photoButton);

        Button valiButton = new Button("Valider");
        valiButton.setLayoutX(5);
        valiButton.setLayoutY(300);
        valiButton.resize(150, 40);
        valiButton.setOnAction((event) -> {
            if(MariaDB.isMailExist(champEmail.getText()))
            {
                MariaDB.insertImage(champEmail.getText(), image_temp);
            }
        });
        this.getChildren().add(valiButton);
    }

    /**
     * Choix de la photo à associer
     */
    public void photo() {

        // FileDialog.Result<@NonNull String> file = FileDialog.getOpenFileName(this,
        // "Importer photo");
        // filePath = file.result;
        traitementImageImport(filePath, 500, 280);
    }

    /**
     * Association de la photo à l'adresse email
     */
    void importPhoto() {
        // Traitement ... A faire

        if (MariaDB.isMailExist(champEmail.getText())) {
            email = champEmail.getText();
            verifEmail.setText("Email valide Bravo :D");

            MariaDB.insertImage(email, new ImageView());
        } else {
            verifEmail.setText("Email invalide, veuillez réessayer :/");
        }
    }

    /**
     * Vérification de la présence de l'email entré dans la BDD
     *
     * @throws SQLException
     * @throws FileNotFoundException
     */
    void verifEmail() throws SQLException, FileNotFoundException {
        if (MariaDB.isMailExist(champEmail.getText())) {
            email = champEmail.getText();
            verifEmail.setText("Email valide Bravo :D");
        } else {
            verifEmail.setText("Email invalide, veuillez réessayer :/");
        }
    }

    void traitementImageImport(String filePath, int width, int height) {
        // QImageReader imageReader = new QImageReader(filePath);
        // imageReader.setScaledSize(new QSize(width, height));
        // QImage pic = imageReader.read();
        // pic.save("import.png");
    }
}
