package frc.robot.util.sensors;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.Constants;

public class TOFSensor{

    TimeOfFlight indexSensor = new TimeOfFlight(Constants.INDEX_SENSOR_CAN_ID);

    public double getDistance(TimeOfFlight sensor){
        return sensor.getRange();
    }

    public boolean lockedOn(TimeOfFlight sensor){
        return sensor.isRangeValid();
    }

    public void setRangeMode(TimeOfFlight sensor, String mode){
        if(mode.equals("Long")){
            sensor.setRangingMode(RangingMode.Long, 100);
        }
        else if(mode.equals("Medium")){
            sensor.setRangingMode(RangingMode.Medium, 100);
        }
        else if(mode.equals("short")){
            sensor.setRangingMode(RangingMode.Short, 100);
        }
    }
}