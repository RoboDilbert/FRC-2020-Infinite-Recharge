package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.util.*;

public class HangingMove{

    private static CANSparkMax HangingMoveMotor;
    private static CANEncoder HangingMoveMotorEncoder;

    public enum HangingMoveState{
        LEFT,
        RIGHT,
        STOP
    }

    public static void init(){
        HangingMoveMotor = new CANSparkMax(Constants.hangingWheelMotorID, MotorType.kBrushless);
        HangingMoveMotor.setIdleMode(IdleMode.kBrake);
        HangingMoveMotorEncoder = HangingMoveMotor.getEncoder();
    }

    public static void controlMove(HangingMoveState value){
        powerMove(HangingMoveMotor, value);
    }

    private static void powerMove(SpeedController motor1, HangingMoveState value){
        if(value == HangingMoveState.LEFT){
            motor1.set(-Constants.hangerSpeed);
        }
        else if(value == HangingMoveState.RIGHT){
            motor1.set(Constants.hangerSpeed);
        }
        else if(value == HangingMoveState.STOP){
            motor1.set(0);
        }
    }

    public static void hangingDebug(){
        SmartDashboard.putNumber("Hang Motor Velocity", HangingMoveMotorEncoder.getVelocity());
    }

}