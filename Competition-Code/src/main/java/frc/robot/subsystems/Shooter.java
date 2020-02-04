package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.util.*;
public class Shooter{

    
    
    public static final CANSparkMax leftShooter = new CANSparkMax(Constants.leftShooterDeviceID, MotorType.kBrushless);
    public static final CANSparkMax rightShooter = new CANSparkMax(Constants.rightShooterDeviceID, MotorType.kBrushless);

    public static CANEncoder leftShooterEncoder;
    public static CANEncoder rightShooterEncoder;
    
    public static enum ShooterDirections{
        FORWARD, REVERSE, STOP, DUNNO;
    }
   
    
    public static void init(){
        
        leftShooter.setIdleMode(IdleMode.kCoast);
        rightShooter.setIdleMode(IdleMode.kCoast);
        rightShooter.setInverted(true);

        leftShooterEncoder = leftShooter.getEncoder();
        rightShooterEncoder = rightShooter.getEncoder();

        
    }

    public static void ShooterControl(SpeedController controller1, SpeedController controller2, ShooterDirections value){
        if(value == ShooterDirections.FORWARD){
            controller1.set(0.8);
            controller2.set(0.8);
        }

        else if( value == ShooterDirections.REVERSE){
            controller1.set(-0.8);
            controller2.set(-0.8);
        }
        else if (value == ShooterDirections.STOP){
            controller1.set(0);
            controller2.set(0);
        }
    }
    
}