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
    private static NetworkTableInstance table1 = null;
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
       NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(2);
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

    //LED Setting
    public static enum LightMode {
        ON,
        OFF, 
        BLINK;
	}

	public static enum CameraMode {
        VISION,
        DRIVER;
    }
    


    public static void setLedMode(LightMode mode) {
        if(mode.equals(LightMode.ON)){
            getValue("ledMode").setNumber(3);
        }
        else if(mode.equals(LightMode.OFF)){
            getValue("ledMode").setNumber(1);
        }
        if(mode.equals(LightMode.BLINK)){
            getValue("ledMode").setNumber(2);
        }
	}

	// public static void setCameraMode(CameraMode mode) {
	// 	getValue("camMode").setNumber(mode.ordinal());
    // }
    
    public static void setPipeline(int number) {
		getValue("pipeline").setNumber(number);
	}

	private static NetworkTableEntry getValue(String key) {
		if (table1 == null) {
			table1 = NetworkTableInstance.getDefault();
		}

		return table1.getTable("limelight").getEntry(key);
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