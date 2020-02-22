package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.util.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WallOfWheels{
    private static CANSparkMax WallMotor;
    private static CANEncoder WallMotorEncoder;

    public static enum WallState{
        REVERSE, 
        FORWARD, 
        STOP;
    }

    public static void init(){
        WallMotor = new CANSparkMax(Constants.WallOfWheelsMotorID, MotorType.kBrushless);
        WallMotor.setIdleMode(IdleMode.kCoast);
        WallMotorEncoder = WallMotor.getEncoder();
    }

    public static void controlWall(WallState value){
        powerWall(WallMotor, value);
    }

    private static void powerWall(SpeedController power, WallState value){
        if(value == WallState.FORWARD){
            power.set(-Constants.wallOfWheelsSpeed);
        }else if(value == WallState.REVERSE){
            power.set(Constants.wallOfWheelsSpeed);
        }else if(value == WallState.STOP){
            power.set(0);
        }
    }

    public static void debugWall(){
        SmartDashboard.putNumber("Wall Encoder", WallMotorEncoder.getPosition());
    }
}