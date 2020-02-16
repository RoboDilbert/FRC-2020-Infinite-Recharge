package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.util.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

    private static CANSparkMax IntakeMotor;
    
    private static DoubleSolenoid intakeDrop;
    
    public enum IntakeState{
        INTAKE, 
        STOP, 
        REVERSE;
    }

    public static void init() {
        intakeDrop = new DoubleSolenoid(Constants.intakeDropForwardSolenoid, Constants.intakeDropBackSolenoid);
        IntakeMotor = new CANSparkMax(Constants.IntakeMotorID, MotorType.kBrushless);
        IntakeMotor.setIdleMode(IdleMode.kCoast);
    }
    
    // --------------------------------------------------------------------
    // Pneumatic Drop (possibly Motors)
    public static void dropIntake(){
        intakeDrop.set(Value.kForward);
    }

    public static void liftIntake(){
        intakeDrop.set(Value.kReverse);
    }

    //---------------------------------------------------------------------
    // intake motor

    public static void controlIntake(IntakeState value){
        powerIntake(IntakeMotor, value);
    }

    private static void powerIntake(SpeedController m_intake, IntakeState value){
        if(value == IntakeState.INTAKE){
            m_intake.set(-Constants.intakeSpeed);
        }
        else if(value == IntakeState.REVERSE){
            m_intake.set(Constants.intakeSpeed);
        }
        else if(value == IntakeState.STOP){
            m_intake.set(0);
        }
        else{
            m_intake.set(0);
        }
    }

    public static void debugIntake(){

    }
}