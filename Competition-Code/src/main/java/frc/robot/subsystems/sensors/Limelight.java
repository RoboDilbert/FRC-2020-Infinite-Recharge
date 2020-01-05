package frc.robot.subsystems.sensors; 

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight{
    NetworkTable table;
    private NetworkTableEntry camMode, ledMode, tx, ty, ta, tv, ts, tl;


    public Limelight(){
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

    //Methods to access information
    public double getHorizontalOffset(){
        return tx.getDouble(0.0);
    }
    public double getVerticalOffset(){
        return ty.getDouble(0.0);
    }
    public double getTargetArea(){
        return ta.getDouble(0.0);
    }
    public double getTargetSkew(){
        return ts.getDouble(0.0);
    }
    public double getLatency(){
        return tl.getDouble(0.0);
    }
    public boolean targetValid(){
        if(tv.getDouble(0.0) == 1.0){
            return true;
        }
        else{
            return false;
        }
    }

    //Camera Setting
    public void setLedMode(String mode){
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

    public void setCameraMode(String mode){
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

    // public void debug(){
    //     SmartDashboard.putString("Limelight Horizontal", Double.toString(tx.getDouble(0.0)));
    //     SmartDashboard.putString("Limelight Vertical", Double.toString(ty.getDouble(0.0)));
    //     SmartDashboard.putString("Limelight Area", Double.toString(ta.getDouble(0.0)));
    //     SmartDashboard.putString("Limelight Skew", Double.toString(ts.getDouble(0.0)));
    //     SmartDashboard.putString("Limelight Latency", Double.toString(tl.getDouble(0.0)));
    //     SmartDashboard.putString("Limelight Valid", Boolean.toString(tv.getDouble(0.0) == 1.0));
    // }


}