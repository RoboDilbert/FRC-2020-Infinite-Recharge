package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.util.*;
public class Shooter{

    
    
    public static final CANSparkMax leftShooter = new CANSparkMax(Constants.leftShooterDeviceID, MotorType.kBrushless);
    public static final CANSparkMax rightShooter = new CANSparkMax(Constants.rightShooterDeviceID, MotorType.kBrushless);
    public static CANEncoder leftShooterEncoder;
    public static CANEncoder rightShooterEncoder;

    

   
    
    public static void init(){
        
        leftShooter.setIdleMode(IdleMode.kCoast);
        rightShooter.setIdleMode(IdleMode.kCoast);
        leftShooterEncoder = leftShooter.getEncoder();
        rightShooterEncoder = rightShooter.getEncoder();
    }

    public static void run(){
        
    }
}