package frc.robot.util.sensors; 

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;
//import frc.robot.subsystems.*;

public class Limelight{
    
    static NetworkTable table;
    public static NetworkTableEntry camMode, ledMode, tx, ty, ta, tv, ts, tl;

    public static UsbCamera drive;
    public static VideoMode videoMode;


    public static void LimelightInitialize(){
        //Set Table to Limelight
        table = NetworkTableInstance.getDefault().getTable("limelight");

        //Get Stats
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");

        ledMode = table.getEntry("ledMode");
        camMode = table.getEntry("camMode");
    }

    public static void initUSBCamera() {
        drive = CameraServer.getInstance().startAutomaticCapture();
        videoMode = new VideoMode(PixelFormat.kYUYV, 800, 448, 30);
        drive.setFPS(30);
        drive.setVideoMode(videoMode);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(1);
    }

    //Methods to access information
    public static double getHorizontalOffset(){
        return tx.getDouble(0.0);
    }
    public static double getVerticalOffset(){
        return ty.getDouble(0.0);
    }
    public static double getTargetArea(){
        return ta.getDouble(0.0);
    }
    public static double getTargetSkew(){
        return ts.getDouble(0.0);
    }
    public static double getLatency(){
        return tl.getDouble(0.0);
    }
    public static boolean targetValid(){
        if(tv.getDouble(0.0) == 1.0){
            return true;
        }
        else{
            return false;
        }
    }

    //Camera Setting
    public static void setLedMode(String mode){
        if(mode.equals("on")){
            ledMode.setNumber(0);
        }
        else if(mode.equals("off")){
            ledMode.setNumber(1);
        }
        else if(mode.equals("blink")){
            ledMode.setNumber(2);
        }
        else{
            System.out.println("Limelight: setLedMode(String mode) ----> not recognised. Setting Leds to blink");
            ledMode.setNumber(2);
        }
    }

    public static void setCameraMode(String mode){
        if(mode.equals("vision")){
            camMode.setNumber(0);
        }
        else if(mode.equals("driver")){
            camMode.setNumber(1);
        }
        else{
            System.out.println("Limelight: setCameraMode(String mode) ----> not recognised. Setting camera to vision");
            camMode.setNumber(2);
        }
    }

    public static void debug(){
        SmartDashboard.putString("Limelight Horizontal", Double.toString(tx.getDouble(0.0)));
        SmartDashboard.putString("Limelight Vertical", Double.toString(ty.getDouble(0.0)));
        SmartDashboard.putString("Limelight Area", Double.toString(ta.getDouble(0.0)));
        SmartDashboard.putString("Limelight Skew", Double.toString(ts.getDouble(0.0)));
        SmartDashboard.putString("Limelight Latency", Double.toString(tl.getDouble(0.0)));
        SmartDashboard.putString("Limelight Valid", Boolean.toString(tv.getDouble(0.0) == 1.0));
    }
}