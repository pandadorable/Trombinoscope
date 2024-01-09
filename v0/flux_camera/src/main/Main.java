package v0.flux_camera.src.main;

import com.github.sarxos.webcam.Webcam;

public class Main {
    public static void main(String[] args) {
        CameraManager cameraManager = new CameraManager();
        CameraWindow cameraWindow = new CameraWindow(cameraManager);

        
    }
}
