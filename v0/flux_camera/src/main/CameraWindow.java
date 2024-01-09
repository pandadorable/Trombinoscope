package v0.flux_camera.src.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class CameraWindow {
    private CameraManager cameraManager;
    private WebcamPanel webcamPanel;
    public CameraWindow(CameraManager cameraManager)
    {
        this.cameraManager = cameraManager;

        int width = 640;
        int height = 480;

        // JFrame setup
        JFrame window = new JFrame("Test webcam panel");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        // WebcamPanel setup
        webcamPanel = cameraManager.getPanel();
        webcamPanel.setSize(width / 2, height);

        // Button photo setup
        JButton button = new JButton("Take photo");
        button.addActionListener(e -> cameraManager.takePicture("./hello-world.png"));
        button.setSize(width / 2, height);

        // Dropdown list of available webcams
         JComboBox<Webcam> cameraList = new JComboBox<Webcam>(CameraManager.getAvailableWebcams());
            cameraList.addActionListener(e -> {
                cameraManager.changeWebcam((Webcam) cameraList.getSelectedItem());
                window.remove(webcamPanel);
                webcamPanel = cameraManager.getPanel();
                window.add(webcamPanel, BorderLayout.WEST);
                window.revalidate();
            });

        // Adding components to JFrame using BorderLayout
        window.setLayout(new BorderLayout());
        window.add(webcamPanel, BorderLayout.WEST);
        window.add(button, BorderLayout.EAST);
        window.add(cameraList, BorderLayout.NORTH);

        window.setVisible(true);

    }


    /******************SETTERS AND GETTERS******************/
    public CameraManager getCameraManager() {
        return cameraManager;
    }
    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    

    
    
}
