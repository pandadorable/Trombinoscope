package trombi.CAMERA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import trombi.BDD.MariaDB;
import trombi.CAMERA.CameraManager;

public class CameraWindow extends Pane {

    ImageView[] list_image = new ImageView[4];
    ToggleButton[] list_image_buttons = new ToggleButton[4];
    int list_image_id = 0;
    int list_image_select = -1;

    @SuppressWarnings("unchecked")
    public CameraWindow() {
        // Liste des cameras disponibles
        List<Webcam> cameras = CameraManager.getAvailableWebcams();
        if (!cameras.isEmpty()) {
            ComboBox cameraList = new ComboBox<Webcam>(FXCollections.observableList(cameras));
            cameraList.setOnAction((event) -> {
                for (Webcam w : cameras) {
                    if (w.isOpen())
                        w.close();
                }
                cameras.get(cameraList.getSelectionModel().getSelectedIndex())
                        .setViewSize(WebcamResolution.VGA.getSize());
                cameras.get(cameraList.getSelectionModel().getSelectedIndex()).open();
            });
            cameraList.getSelectionModel().selectFirst();
            cameraList.setLayoutX(50);
            cameraList.setLayoutY(5);
            this.getChildren().add(cameraList);

            // L'afficheur d'image
            var imageView = new ImageView();
            imageView.setFitWidth(500);
            imageView.setFitHeight(500);
            imageView.setPreserveRatio(true);
            imageView.setLayoutX(50);
            imageView.setLayoutY(50);
            this.getChildren().add(imageView);

            // ouvre camera par défaut
            cameras.get(cameraList.getSelectionModel().getSelectedIndex()).open();
            // Thread d'affichage
            var task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    boolean camStart = true;
                    while (camStart) {
                        // Conversion de l'image en image JavaFX
                        var image = SwingFXUtils.toFXImage(
                                cameras.get(cameraList.getSelectionModel().getSelectedIndex()).getImage(), null);
                        // Affichage de l'image
                        imageView.setImage(image);
                    }
                    return null;
                }
            };
            // Tâche pour afficher l'image
            var thread = new Thread(task);
            thread.setDaemon(true); // Le thread est arrêté si l'application est quittée
            thread.start();

            // Capture
            Button captureButton = new Button();
            captureButton.setText("Prendre une photo");
            captureButton.setLayoutX(220);
            captureButton.setLayoutY(475);
            captureButton.setOnAction((event) -> {
                list_image[list_image_id].setImage(SwingFXUtils
                        .toFXImage(cameras.get(cameraList.getSelectionModel().getSelectedIndex()).getImage(), null));
                if (!list_image[list_image_id].isVisible())
                {
                    list_image[list_image_id].setVisible(true);
                    list_image_buttons[list_image_id].setVisible(true);
                }
                list_image_id = (list_image_id + 1) % 4;
            });
            this.getChildren().add(captureButton);

            // Séparateur
            Line line = new Line(620, 20, 620, 520);
            this.getChildren().add(line);

            // Images Temporaire
            for (int i = 0; i < list_image.length; i++) {
                list_image[i] = new ImageView();
                list_image_buttons[i] = new ToggleButton("Image "+(i+1));
            }
            ToggleGroup image_group = new ToggleGroup();
            list_image[0].setFitWidth(230);
            list_image[0].setFitHeight(230);
            list_image[0].setPreserveRatio(true);
            list_image[0].setLayoutX(640);
            list_image[0].setLayoutY(30);
            list_image[0].setVisible(false);
            this.getChildren().add(list_image[0]);
            list_image_buttons[0].setLayoutX(720);
            list_image_buttons[0].setLayoutY(210);
            list_image_buttons[0].setVisible(false);
            list_image_buttons[0].setToggleGroup(image_group);
            list_image_buttons[0].setOnAction((event) -> {list_image_select = 0;});
            this.getChildren().add(list_image_buttons[0]);

            list_image[1].setFitWidth(230);
            list_image[1].setFitHeight(230);
            list_image[1].setPreserveRatio(true);
            list_image[1].setLayoutX(890);
            list_image[1].setLayoutY(30);
            list_image[1].setVisible(false);
            this.getChildren().add(list_image[1]);
            list_image_buttons[1].setLayoutX(970);
            list_image_buttons[1].setLayoutY(210);
            list_image_buttons[1].setVisible(false);
            list_image_buttons[1].setToggleGroup(image_group);
            list_image_buttons[1].setOnAction((event) -> {list_image_select = 1;});
            this.getChildren().add(list_image_buttons[1]);

            list_image[2].setFitWidth(230);
            list_image[2].setFitHeight(230);
            list_image[2].setPreserveRatio(true);
            list_image[2].setLayoutX(640);
            list_image[2].setLayoutY(250);
            list_image[2].setVisible(false);
            this.getChildren().add(list_image[2]);
            list_image_buttons[2].setLayoutX(720);
            list_image_buttons[2].setLayoutY(430);
            list_image_buttons[2].setVisible(false);
            list_image_buttons[2].setToggleGroup(image_group);
            list_image_buttons[2].setOnAction((event) -> {list_image_select = 2;});
            this.getChildren().add(list_image_buttons[2]);

            list_image[3].setFitWidth(230);
            list_image[3].setFitHeight(230);
            list_image[3].setPreserveRatio(true);
            list_image[3].setLayoutX(890);
            list_image[3].setLayoutY(250);
            list_image[3].setVisible(false);
            this.getChildren().add(list_image[3]);
            list_image_buttons[3].setLayoutX(970);
            list_image_buttons[3].setLayoutY(430);
            list_image_buttons[3].setVisible(false);
            list_image_buttons[3].setToggleGroup(image_group);
            list_image_buttons[3].setOnAction((event) -> {list_image_select = 3;});
            this.getChildren().add(list_image_buttons[3]);

            // Mail associer à l'image
            Label mail_label = new Label();
            mail_label.setText("Mail de l'étudiant : ");
            mail_label.setLayoutX(670);
            mail_label.setLayoutY(475);
            this.getChildren().add(mail_label);
            Label toggle_label_invalide = new Label();
            toggle_label_invalide.setText("aucune image sélectionnée");
            toggle_label_invalide.setTextFill(Color.color(1, 0, 0));
            toggle_label_invalide.setLayoutX(800);
            toggle_label_invalide.setLayoutY(460);
            toggle_label_invalide.setVisible(false);
            this.getChildren().add(toggle_label_invalide);
            Label mail_label_invalide = new Label();
            mail_label_invalide.setText("mail introuvable/invalide");
            mail_label_invalide.setTextFill(Color.color(1, 0, 0));
            mail_label_invalide.setLayoutX(800);
            mail_label_invalide.setLayoutY(475);
            mail_label_invalide.setVisible(false);
            this.getChildren().add(mail_label_invalide);
            TextArea mail = new TextArea();
            mail.setPrefRowCount(1);
            mail.setPrefWidth(300);
            mail.setLayoutX(670);
            mail.setLayoutY(500);
            this.getChildren().add(mail);
            Button mail_bp = new Button();
            mail_bp.setText("Envoyer image");
            mail_bp.setLayoutX(995);
            mail_bp.setLayoutY(500);
            mail_bp.setOnAction((event) -> {
                toggle_label_invalide.setVisible(image_group.getSelectedToggle() == null);
                mail_label_invalide.setVisible(!MariaDB.isMailExist(mail.getText()));
                if(!toggle_label_invalide.isVisible() && !mail_label_invalide.isVisible()) //toute les condition sont remplis pour envoyer une image
                {
                    MariaDB.insertImage(mail.getText(), list_image[list_image_select]);
                }
            });
            this.getChildren().add(mail_bp);

        }
    }

    // public void traitementImage(QImageCapture image, int width, int height,
    // Quality quality) {
    // imageCapture.setResolution(width, height); //Resolution des images
    // imageCapture.setQuality(quality); //Qualité des images
    // }

    // public void traitementImage(QImageCapture image, int width, int height) {
    // imageCapture.setResolution(width, height); //Resolution des images
    // }

    // public void traitementImage(QImageCapture image) {
    // imageCapture.setResolution(500, 280); //Resolution des images
    // imageCapture.setQuality(Quality.VeryHighQuality); //Qualité des images
    // }

    // public void traitementImage(QImageCapture image, Quality quality) {
    // imageCapture.setQuality(quality); //Qualité des images
    // }

    // public QMediaCaptureSession getCaptureSession() {
    // return captureSession;
    // }

    // public void setEmail() {
    // email = champEmail.text();
    // }

    // public void capture() {
    // // mettre chemin absolue du rep du projet dans les parenthèses
    // // /cheminabsolue/nomDuFichier
    // if(this.imageCapture == null)
    // {
    // try {
    // MariaDB.insertImage(email, "mario.png");
    // } catch (IOException | SQLException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // else
    // {
    // int capture = this.imageCapture.captureToFile();
    // String id = "image_";
    // if(capture < 1000) id+='0';
    // if(capture < 100) id+='0';
    // if(capture < 10) id+='0';
    // id += capture+".jpg";
    // File imageExist = new File(id);
    // if(!(imageExist.exists())) {id = "mario.png";}

    // /*try {
    // System.out.println("ici");
    // MariaDB.insertImage(email, id);
    // } catch (IOException | SQLException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }*/
    // }
    // }
    // void verifEmail() throws SQLException, FileNotFoundException {
    // if (MariaDB.isMailExist(champEmail.text())) {
    // email = champEmail.text();
    // verifEmail.setText("Email valide Bravo :D");
    // } else {
    // verifEmail.setText("Email invalide, veuillez réessayer :/");
    // }
    // }
}
