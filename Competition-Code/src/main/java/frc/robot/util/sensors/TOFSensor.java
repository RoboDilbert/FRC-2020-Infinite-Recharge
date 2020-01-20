package frc.robot.util.sensors;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.Constants;

public class TOFSensor{

    public static TimeOfFlight leftPP = new TimeOfFlight(Constants.LEFT_PP_ID);
    public static TimeOfFlight rightPP = new TimeOfFlight(Constants.RIGHT_PP_ID);

    public static void getPPLength(double left, double right, double average){
         right = rightPP.getRange();
         left = leftPP.getRange();
         average = (right + left)/2;
    }
    

    public static void lockedOn(boolean sight){
        if(rightPP.isRangeValid() && leftPP.isRangeValid()){
            sight = true;
        }
        else{
            sight = false;
        }
    }

    public static void setPPRangeMode(String mode){
        if(mode.equals("Long")){
            leftPP.setRangingMode(RangingMode.Long, 100);
            rightPP.setRangingMode(RangingMode.Long, 100);
        }
        else if(mode.equals("Medium")){
            leftPP.setRangingMode(RangingMode.Medium, 100);
            rightPP.setRangingMode(RangingMode.Medium, 100);
        }
        else if(mode.equals("short")){
            leftPP.setRangingMode(RangingMode.Short, 100);
            rightPP.setRangingMode(RangingMode.Short, 100);
        }
    }
    public static void arePPEqual(boolean output){
        if(leftPP.getRange() > 706 && leftPP.getRange() < 806 && rightPP.getRange() < 806 && rightPP.getRange() > 706){
            output = true;
        }
        else{
            output = false;
        }
    }
    public static void outputTOFData(){
      double test =  rightPP.getRange();
      double test2 = leftPP.getRange();
        SmartDashboard.putNumber("LeftPP", test2);
        SmartDashboard.putNumber("RightPP", test);
        System.out.println(test);
        SmartDashboard.updateValues();
    }
}