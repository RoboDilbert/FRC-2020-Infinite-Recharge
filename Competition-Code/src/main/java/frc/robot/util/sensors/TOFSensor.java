package frc.robot.util.sensors;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.Constants;

public class TOFSensor{

    public TimeOfFlight leftPP = new TimeOfFlight(Constants.LEFT_PP_ID);
    public TimeOfFlight rightPP = new TimeOfFlight(Constants.RIGHT_PP_ID);

    public void getPPLength(double left, double right, double average){
         right = rightPP.getRange();
         left = leftPP.getRange();
         average = (right + left)/2;
    }
    

    public void lockedOn(boolean sight){
        if(rightPP.isRangeValid() && leftPP.isRangeValid()){
            sight = true;
        }
        else{
            sight = false;
        }
    }

    public void setPPRangeMode(String mode){
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
    public void arePPEqual(boolean output){
        if(leftPP.getRange() > 28 && leftPP.getRange() < 32 && rightPP.getRange() < 32 && rightPP.getRange() > 28){
            output = true;
        }
        else{
            output = false;
        }
    }
    public void outputTOFData(){
      double test =  rightPP.getRange();
      double test2 = leftPP.getRange();
        SmartDashboard.putNumber("LeftPP", test2);
        SmartDashboard.putNumber("RightPP", test);
        System.out.println(test);
        SmartDashboard.updateValues();
    }
}