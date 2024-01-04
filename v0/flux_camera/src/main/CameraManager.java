package src.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;


public class CameraManager {

    Webcam webcam;
    WebcamPanel panel;


    /**
     * Constructor with default webcam
     */
    public CameraManager() {
        this(Webcam.getDefault());
    }  

    /**
     * Constructor with specified webcam
     */
    public CameraManager(Webcam webcam) {
        changeWebcam(webcam);
    }

    /**
     * Change the webcam
     * @return
     */
    public void changeWebcam(Webcam webcam) {
        this.webcam = webcam;
        if(panel != null) {
            panel.stop();
            webcam.close();
        }
        panel = new WebcamPanel(webcam);
        //panel.setFPSDisplayed(true);
        //panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        panel.start();  
    }

    /**
     * Get the lis tof available webcams
     * @return
     */
    public static Webcam[] getAvailableWebcams() {
        return Webcam.getWebcams().toArray(new Webcam[] {});
    }


    /**
     * Take a picture and save it to the specified path
     * @return
     */
    public void takePicture(String path) {
        try {
            ImageIO.write(webcam.getImage(), "PNG", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /******************SETTERS AND GETTERS******************/

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    public WebcamPanel getPanel() {
        return panel;
    }

    public void setPanel(WebcamPanel panel) {
        this.panel = panel;
    }

    
}