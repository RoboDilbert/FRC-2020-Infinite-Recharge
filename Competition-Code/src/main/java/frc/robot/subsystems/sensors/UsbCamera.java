package frc.robot.subsystems.sensors;

//import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;

public class UsbCamera{

    public static void init(){

        // public UsbCamera drive = CameraServer.getInstance().startAutomaticCapture();
        // public VideoMode videoMode = new VideoMode(PixelFormat.kYUYV, 800, 448, 30);
        // drive.setFPS(30);
        // drive.setVideoMode(videoMode);
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(2);
    }
    public static void run(){

    }
}