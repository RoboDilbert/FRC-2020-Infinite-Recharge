package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.util.*;
public class Shooter{

    
    
    public static final CANSparkMax Shooter = new CANSparkMax(Constants.ShooterDeviceID, MotorType.kBrushless);
    
    public static CANEncoder ShooterEncoder;
   
    
    public static enum ShooterDirections{
        FORWARD, REVERSE, STOP, DUNNO;
    }
   
    
    public static void init(){
        
        Shooter.setIdleMode(IdleMode.kCoast);
       

        ShooterEncoder = Shooter.getEncoder();
      

        
    }

    public static void ShooterControl(SpeedController controller1, ShooterDirections value){
        if(value == ShooterDirections.FORWARD){
            controller1.set(0.8);
        }

        else if( value == ShooterDirections.REVERSE){
            controller1.set(-0.8);
        }
        else if (value == ShooterDirections.STOP){
            controller1.set(0);
        }
    }
    
}