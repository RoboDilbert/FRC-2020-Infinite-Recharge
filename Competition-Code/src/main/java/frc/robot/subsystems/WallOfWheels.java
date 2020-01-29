package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.util.*;

public class WallOfWheels{
    public static final CANSparkMax wallMotor = new CANSparkMax(Constants.leftFrontDeviceID, MotorType.kBrushless);


    public static void init(){
        wallMotor.setIdleMode(IdleMode.kBrake);
    }

    public static void PowerWall(double power){
        wallMotor.set(power);
    }
}