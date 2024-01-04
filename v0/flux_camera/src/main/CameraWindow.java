package src.main;

import java.awt.Button;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;

public class CameraWindow {
    private CameraManager cameraManager;
    public CameraWindow(CameraManager cameraManager)
    {
        this.cameraManager = cameraManager;

        //retour video en direct
        JFrame window = new JFrame("Test webcam panel");
        window.add(cameraManager.getPanel());
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        //Bouton de prise de photo
        JButton button = new JButton("Take photo");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cameraManager.takePicture("./hello-world.png");
            }
        });
        window.add(button);


    }


    /******************SETTERS AND GETTERS******************/
    public CameraManager getCameraManager() {
        return cameraManager;
    }
    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    

    
    
}
