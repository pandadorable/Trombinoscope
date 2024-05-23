package trombi.CAMERA;

import java.awt.image.BufferedImage;
import java.util.List;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import trombi.BDD.MariaDB;

public class CameraWindow extends Pane {

    ImageView[] list_image = new ImageView[4];
    ToggleButton[] list_image_buttons = new ToggleButton[4];
    Rectangle[] list_image_cadre = new Rectangle[4];
    int list_image_id = 0;
    int list_image_select = -1;
    ImageView imageView = new ImageView();

    public static void kill() {
        List<Webcam> cameras = CameraManager.getAvailableWebcams();
        for (Webcam webcam : cameras) {
            webcam.close();
        }
    }

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
                    this.getChildren().remove(imageView);
                }
                cameras.get(cameraList.getSelectionModel().getSelectedIndex())
                        .setViewSize(WebcamResolution.HD.getSize());

                startCam(cameraList);
            });
            cameraList.getSelectionModel().selectFirst();
            cameraList.setLayoutX(50);
            cameraList.setLayoutY(5);
            this.getChildren().add(cameraList);

            startCam(cameraList);

            // Capture
            Button captureButton = new Button();
            captureButton.setText("Prendre une photo");
            captureButton.setLayoutX(220);
            captureButton.setLayoutY(475);
            captureButton.setOnAction((event) -> {
                BufferedImage tmp = cameras.get(cameraList.getSelectionModel().getSelectedIndex()).getImage();
                int minVal = Math.min(tmp.getWidth(), tmp.getHeight());
                tmp = tmp.getSubimage((tmp.getWidth() - minVal) / 2, (tmp.getHeight() - minVal) / 2, minVal, minVal);
                list_image[list_image_id].setImage(SwingFXUtils
                        .toFXImage(tmp, null));
                if (!list_image[list_image_id].isVisible()) {
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
                list_image_buttons[i] = new ToggleButton("Image " + (i + 1));

            }
            ToggleGroup image_group = new ToggleGroup();

            list_image_cadre[0] = new Rectangle(650, 20, 210, 210);
            list_image_cadre[0].setFill(Color.YELLOW);
            list_image_cadre[0].setVisible(false);
            this.getChildren().add(list_image_cadre[0]);
            list_image[0].setFitHeight(190);
            list_image[0].setPreserveRatio(true);
            list_image[0].setLayoutX(660);
            list_image[0].setLayoutY(30);
            list_image[0].setVisible(false);
            this.getChildren().add(list_image[0]);
            list_image_buttons[0].setLayoutX(720);
            list_image_buttons[0].setLayoutY(210);
            list_image_buttons[0].setVisible(false);
            list_image_buttons[0].setToggleGroup(image_group);
            list_image_buttons[0].setOnAction((event) -> {
                list_image_select = 0;
                for (int i = 0; i < 4; i++) {
                    list_image_cadre[i].setVisible(i == 0 && list_image_buttons[0].isSelected());
                }
            });
            this.getChildren().add(list_image_buttons[0]);

            list_image_cadre[1] = new Rectangle(900, 20, 210, 210);
            list_image_cadre[1].setFill(Color.YELLOW);
            list_image_cadre[1].setVisible(false);
            this.getChildren().add(list_image_cadre[1]);
            list_image[1].setFitHeight(190);
            list_image[1].setPreserveRatio(true);
            list_image[1].setLayoutX(910);
            list_image[1].setLayoutY(30);
            list_image[1].setVisible(false);
            this.getChildren().add(list_image[1]);
            list_image_buttons[1].setLayoutX(970);
            list_image_buttons[1].setLayoutY(210);
            list_image_buttons[1].setVisible(false);
            list_image_buttons[1].setToggleGroup(image_group);
            list_image_buttons[1].setOnAction((event) -> {
                list_image_select = 1;
                for (int i = 0; i < 4; i++) {
                    list_image_cadre[i].setVisible(i == 1 && list_image_buttons[1].isSelected());
                }
            });
            this.getChildren().add(list_image_buttons[1]);

            list_image_cadre[2] = new Rectangle(650, 240, 210, 210);
            list_image_cadre[2].setFill(Color.YELLOW);
            list_image_cadre[2].setVisible(false);
            this.getChildren().add(list_image_cadre[2]);
            list_image[2].setFitHeight(190);
            list_image[2].setPreserveRatio(true);
            list_image[2].setLayoutX(660);
            list_image[2].setLayoutY(250);
            list_image[2].setVisible(false);
            this.getChildren().add(list_image[2]);
            list_image_buttons[2].setLayoutX(720);
            list_image_buttons[2].setLayoutY(430);
            list_image_buttons[2].setVisible(false);
            list_image_buttons[2].setToggleGroup(image_group);
            list_image_buttons[2].setOnAction((event) -> {
                list_image_select = 2;
                for (int i = 0; i < 4; i++) {
                    list_image_cadre[i].setVisible(i == 2 && list_image_buttons[2].isSelected());
                }
            });
            this.getChildren().add(list_image_buttons[2]);

            list_image_cadre[3] = new Rectangle(900, 240, 210, 210);
            list_image_cadre[3].setFill(Color.YELLOW);
            list_image_cadre[3].setVisible(false);
            this.getChildren().add(list_image_cadre[3]);
            list_image[3].setFitHeight(190);
            list_image[3].setPreserveRatio(true);
            list_image[3].setLayoutX(910);
            list_image[3].setLayoutY(250);
            list_image[3].setVisible(false);
            this.getChildren().add(list_image[3]);
            list_image_buttons[3].setLayoutX(970);
            list_image_buttons[3].setLayoutY(430);
            list_image_buttons[3].setVisible(false);
            list_image_buttons[3].setToggleGroup(image_group);
            list_image_buttons[3].setOnAction((event) -> {
                list_image_select = 3;
                for (int i = 0; i < 4; i++) {
                    list_image_cadre[i].setVisible(i == 3 && list_image_buttons[3].isSelected());
                }
            });
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
                if (!toggle_label_invalide.isVisible() && !mail_label_invalide.isVisible()) // toute les condition sont
                                                                                            // remplis pour envoyer une
                                                                                            // image
                {
                    MariaDB.insertImage(mail.getText(), list_image[list_image_select]);
                }
            });
            this.getChildren().add(mail_bp);

        }

    }

    // Demarrer la camera
    public void startCam(ComboBox cameraList) {
        List<Webcam> cameras = CameraManager.getAvailableWebcams();

        // L'afficheur d'image
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
    }

    public static BufferedImage compression(BufferedImage img, float compression) {


        return null;
    }
}
