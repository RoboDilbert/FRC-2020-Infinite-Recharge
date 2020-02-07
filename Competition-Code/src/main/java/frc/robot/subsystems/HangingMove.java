package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.util.*;

public class HangingMove{

private static CANSparkMax hangingWheel;
private CANEncoder hangingWheelEncoder;

    private enum wheelDirection{
        LEFT,
        RIGHT,
        STOP
    }


    public void init(){
        hangingWheel = new CANSparkMax(Constants.hangingWheel, MotorType.kBrushless);
        hangingWheelEncoder = hangingWheel.getEncoder();

    }

    public void moveLeft(){
        moveControl(hangingWheel, wheelDirection.LEFT);
    }

    public void moveRight(){
        moveControl(hangingWheel, wheelDirection.RIGHT);
    }

    public void moveStop(){
        moveControl(hangingWheel, wheelDirection.STOP);
    }


    private void moveControl(SpeedController motor1, wheelDirection value){
        if(value == wheelDirection.LEFT){
            motor1.set(1);
        }
        else if(value == wheelDirection.RIGHT){
            motor1.set(-1);
        }
        else if(value == wheelDirection.STOP){
            motor1.set(0);
        }
    }

}