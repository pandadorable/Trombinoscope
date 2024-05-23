package trombi.CAMERA;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.JFileChooser;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import trombi.BDD.MariaDB;

public class ImportWindow extends Pane {
    TextArea champEmail;
    private Label verifEmail;
    private String email;
    private String filePath;

    
    Label erreur = new Label();
    BufferedImage original_image;
    ImageView image_temp = new ImageView();

    static Float compression = 0.5f;

    public ImportWindow() {
        verifEmail = new Label();
        verifEmail.setText("Mail de l'etudiant");
        verifEmail.setLayoutX(5);
        verifEmail.setLayoutY(20);
        verifEmail.resize(150, 40);
        this.getChildren().add(verifEmail);

        champEmail = new TextArea();
        champEmail.setPrefRowCount(1);
        champEmail.setLayoutX(5);
        champEmail.setLayoutY(40);
        champEmail.resize(150, 40);
        this.getChildren().add(champEmail);

        Button photoButton = new Button("Choix photo");
        photoButton.setLayoutX(5);
        photoButton.setLayoutY(90);
        photoButton.resize(150, 40);
        photoButton.setOnAction((envent) -> {
            JFileChooser dialogue = new JFileChooser(".");
            File fichier;

            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fichier = dialogue.getSelectedFile();
                BufferedImage tmp = SwingFXUtils.fromFXImage(new Image(fichier.toURI().toString()), null);
                int minVal = Math.min(tmp.getWidth(), tmp.getHeight());
                tmp = tmp.getSubimage((tmp.getWidth() - minVal) / 2, (tmp.getHeight() - minVal) / 2, minVal, minVal);
                original_image = tmp;
                image_temp.setImage(SwingFXUtils.toFXImage(CameraWindow.compression(original_image,compression), null));
                image_temp.setFitWidth(400);
                image_temp.setFitHeight(400);
                image_temp.setPreserveRatio(true);
            }
        });
        this.getChildren().add(photoButton);
        image_temp.setLayoutX(700);
        image_temp.setLayoutY(70);
        this.getChildren().add(image_temp);

        Button valiButton = new Button("Valider");
        valiButton.setLayoutX(100);
        valiButton.setLayoutY(90);
        valiButton.resize(150, 40);
        valiButton.setOnAction((event) -> {
            if(MariaDB.isMailExist(champEmail.getText()))
            {
                try {
                    MariaDB.insertImage(champEmail.getText(), image_temp);
                    erreur.setText("");
                } catch (Exception e) {
                    erreur.setText("Veuillez sélectionner une image valide !");
                }
            }
            else
            {
                erreur.setText("Veulliez entrer un mail valide");
            }
        });
        this.getChildren().add(valiButton);

        erreur.setLayoutX(200);
        erreur.setLayoutY(95);
        erreur.setTextFill(Color.RED);
        this.getChildren().add(erreur);

        // Slider compression
            Label Slider_left = new Label();
            Slider_left.setLayoutY(135);
            Slider_left.setLayoutX(70);
            Slider_left.setText("Meilleure\ncompréssion");
            Label Slider_right = new Label();
            Slider_right.setLayoutY(135);
            Slider_right.setLayoutX(470);
            Slider_right.setText("Meilleure\nqualitée");
            Slider compression_slider = new Slider(0f, 1f, 0.5f);
            compression_slider.setLayoutX(160);
            compression_slider.setLayoutY(140);
            compression_slider.setPrefWidth(300);
            compression_slider.setShowTickMarks(true);
            compression_slider.setMajorTickUnit(0.25f);
            compression_slider.setBlockIncrement(0.1f);
            compression_slider.setOnMouseReleased(event -> {
                compression = (float) compression_slider.getValue();
                image_temp.setImage(SwingFXUtils.toFXImage(CameraWindow.compression(original_image,compression), null));
            });
            this.getChildren().add(compression_slider);
            this.getChildren().add(Slider_left);
            this.getChildren().add(Slider_right);

        // Séparateur
            Line line = new Line(620, 20, 620, 520);
            this.getChildren().add(line);
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
