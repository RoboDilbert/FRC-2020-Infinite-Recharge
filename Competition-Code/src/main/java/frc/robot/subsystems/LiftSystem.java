package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.util.sensors.*;


public class LiftSystem{
    
private static CANSparkMax liftWheel1;
private static CANSparkMax liftWheel2;
private CANEncoder m_lift1;
private CANEncoder m_lift2;

private DoubleSolenoid m_pinLock1;
private DoubleSolenoid m_pinLock2;

    public enum lifterSpeed{
        Forward,
        Reverse,
        Stop
    }

    public void init(){
        liftWheel1 = new CANSparkMax(Constants.lifterWheelLeft,MotorType.kBrushless);
        liftWheel2 = new CANSparkMax(Constants.lifterWheelRight,MotorType.kBrushless);
        m_lift1 = liftWheel1.getEncoder();
        m_lift2 = liftWheel2.getEncoder();
        m_pinLock1 = new DoubleSolenoid(Constants.pinLockForward, Constants.pinLockBack);
       // m_pinLock2 = new DoubleSolenoid(1, 0);

    }

    public void liftLock(){
        m_pinLock1.set(Value.kForward);
       // m_pinLock2.set(Value.kForward);
    }

    public void liftUnlock(){
        m_pinLock1.set(Value.kReverse);
       // m_pinLock2.set(Value.kReverse);
    }

    public void liftReverse(){
        lifterMovement(liftWheel1, liftWheel2, lifterSpeed.Reverse);
    }

    public void liftLaunch(){
        lifterMovement(liftWheel1, liftWheel2, lifterSpeed.Forward);
    } 

    public void liftStop(){
        lifterMovement(liftWheel1, liftWheel2, lifterSpeed.Stop);
    }

    private void lifterMovement(SpeedController motor1, SpeedController motor2, lifterSpeed value ){
        if(value == lifterSpeed.Forward){
            motor1.set(1);
            motor2.set(1);
        }
        else if(value == lifterSpeed.Reverse){
            motor1.set(-1);
            motor2.set(-1);
        }
        else if (value == lifterSpeed.Stop){
            motor1.set(0);
            motor2.set(0);
        }
    }
    
}