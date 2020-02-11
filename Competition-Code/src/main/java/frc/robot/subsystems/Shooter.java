package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.util.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter{
   
    private static CANSparkMax ShooterMotor;
    private static CANEncoder ShooterMotorEncoder;
   
    public static enum ShooterState{
        FORWARD, 
        REVERSE, 
        STOP;
    }
   
    public static void init(){
        ShooterMotor = new CANSparkMax(Constants.ShooterMotorID, MotorType.kBrushless);
        ShooterMotor.setIdleMode(IdleMode.kCoast);
        ShooterMotorEncoder = ShooterMotor.getEncoder();
    }

    public static void controlShooter(ShooterState value){
        powerShooter(ShooterMotor, value);
    }

    private static void powerShooter(SpeedController controller1, ShooterState value){
        if(value == ShooterState.FORWARD){
            controller1.set(Constants.shooterSpeed);
        }
        else if( value == ShooterState.REVERSE){
            controller1.set(-Constants.shooterSpeed);
        }
        else if (value == ShooterState.STOP){
            controller1.set(0);
        }
    }

    public static void debugShooter(){
        SmartDashboard.putNumber("Shooter Encoder", ShooterMotorEncoder.getPosition());
    }
}