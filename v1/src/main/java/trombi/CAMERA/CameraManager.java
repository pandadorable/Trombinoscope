package trombi.CAMERA;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import java.util.List;


public class CameraManager {

    /**
     * The selected webcam
     */
    private Webcam webcam;

    /**
     * The panel of the webcam.
     */
    private WebcamPanel panel;


    /**
     * Constructor with default webcam.
     */
    public CameraManager() {
        this(Webcam.getDefault());
    }

    /**
     * Constructor with specified webcam.
     */
    public CameraManager(Webcam webcam) {
        changeWebcam(webcam);
    }

    /**
     * Get the list of available webcams.
     *
     * @return the array of available webcams
     */
    public static Webcam[] getAvailableWebcams() {
        List<Webcam> webcams = Webcam.getWebcams();
        List<Webcam> nonVirtualWebcams = new java.util.ArrayList<Webcam>();
        for (Webcam webcam : webcams) {
            if (!webcam.getName().toLowerCase().contains("virtual")) {
                nonVirtualWebcams.add(webcam);
            }
        }
        return nonVirtualWebcams.toArray(new Webcam[nonVirtualWebcams.size()]);
    }

    /**
     * Change the webcam.
     *
     * @param webcam the selected webcam
     */
    public void changeWebcam(Webcam webcam) {
        if (this.webcam == webcam) {
            return;
        }
        this.webcam = webcam;
        //set size
        webcam.setViewSize(webcam.getViewSizes()[0]);
        if (panel != null) {
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
     * Take a picture and save it to the specified path.
     *
     * @param path the path to save pictures
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
