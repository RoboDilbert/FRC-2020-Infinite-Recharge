package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.util.*;

public class WallOfWheels{
    public static final CANSparkMax wallMotor = new CANSparkMax(Constants.WallOfWheelsID, MotorType.kBrushed);

    public static enum WallDirections{
        REVERSE, FORWARD, STOP;
    }


    public static void init(){
        wallMotor.setIdleMode(IdleMode.kBrake);
    }

    public static void PowerWall(WallDirections value){
        WallControl(wallMotor, value);
    }

    private static void WallControl(SpeedController power, WallDirections value){
        if(value == WallDirections.FORWARD){
            power.set(.4);
        }else if(value == WallDirections.REVERSE){
            power.set(-.4);
        }else if(value == WallDirections.STOP){
            power.set(0);
        }
    }
}