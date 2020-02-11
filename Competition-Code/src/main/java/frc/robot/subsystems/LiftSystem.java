package frc.robot.subsystems;

import frc.robot.util.Constants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftSystem{
    
    private static CANSparkMax lifterMotor1;
    private static CANSparkMax lifterMotor2;
    private static CANEncoder lifterMotor1Encoder;
    private static CANEncoder lifterMotor2Encoder;

    private static DoubleSolenoid m_pinLock1;
    //private DoubleSolenoid m_pinLock2;

    public enum LifterState{
        FORWARD,
        REVERSE,
        STOP;
    }

    public static void init(){
        lifterMotor1 = new CANSparkMax(Constants.lifterLeftMotorID,MotorType.kBrushless);
        lifterMotor1.setIdleMode(IdleMode.kBrake);
        lifterMotor2 = new CANSparkMax(Constants.lifterRightMotorID,MotorType.kBrushless);
        lifterMotor2.setIdleMode(IdleMode.kBrake);
        lifterMotor1Encoder = lifterMotor1.getEncoder();
        lifterMotor2Encoder = lifterMotor2.getEncoder();
        m_pinLock1 = new DoubleSolenoid(Constants.pinLockForward, Constants.pinLockBack);
       // m_pinLock2 = new DoubleSolenoid(1, 0);
    }

    public void liftLock(){
        m_pinLock1.set(Value.kForward);
    }

    public void liftUnlock(){
        m_pinLock1.set(Value.kReverse);
    }

    public void controlLifter(LifterState value){
        powerLifter(lifterMotor1, lifterMotor2, value);
    }

    private void powerLifter(SpeedController motor1, SpeedController motor2, LifterState value ){
        if(value == LifterState.FORWARD){
            motor1.set(Constants.lifterSpeed);
            motor2.set(Constants.lifterSpeed);
        }
        else if(value == LifterState.REVERSE){
            motor1.set(-Constants.lifterSpeed);
            motor2.set(-Constants.lifterSpeed);
        }
        else if (value == LifterState.STOP){
            motor1.set(0);
            motor2.set(0);
        }
    }

    public void debugLift(){
        SmartDashboard.putNumber("Lift Motor 1 Encoder",lifterMotor1Encoder.getPosition());
        SmartDashboard.putNumber("Lift Motor 2 Encoder", lifterMotor2Encoder.getPosition());
    }
    
}