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
    private static IntakeMotorState currentMotorState;
    private static IntakeSolenoid currentSolenoidState;

    public enum IntakeSolenoid{
        DOWN,
        UP;
    }

    public enum IntakeMotorState{
        FORWARD, 
        STOP, 
        REVERSE;
    }

    public static void init() {
        currentMotorState = IntakeMotorState.STOP;
        currentSolenoidState = IntakeSolenoid.UP;
        intakeDrop = new DoubleSolenoid(Constants.intakeDropForwardSolenoid, Constants.intakeDropBackSolenoid);
        IntakeMotor = new CANSparkMax(Constants.IntakeMotorID, MotorType.kBrushless);
        IntakeMotor.setIdleMode(IdleMode.kCoast);
    }
    
    public static IntakeMotorState getMotorState(){
        return currentMotorState;
    }

    public static IntakeSolenoid getSolenoidState(){
        return currentSolenoidState;
    }

    // --------------------------------------------------------------------
    // Pneumatic Drop
    public static void dropIntake(){
        currentSolenoidState = IntakeSolenoid.DOWN;
        intakeDrop.set(Value.kForward);
    }

    public static void liftIntake(){
        currentSolenoidState = IntakeSolenoid.UP;
        intakeDrop.set(Value.kReverse);
    }

    //---------------------------------------------------------------------
    // intake motor

    public static void controlIntake(IntakeMotorState value){
        powerIntake(IntakeMotor, value);
    }

    private static void powerIntake(SpeedController m_intake, IntakeMotorState value){
        if(value == IntakeMotorState.FORWARD){
            currentMotorState = IntakeMotorState.FORWARD;
            m_intake.set(-Constants.intakeSpeed);
        }
        else if(value == IntakeMotorState.REVERSE){
            currentMotorState = IntakeMotorState.REVERSE;
            m_intake.set(Constants.intakeSpeed);
        }
        else if(value == IntakeMotorState.STOP){
            currentMotorState = IntakeMotorState.STOP;
            m_intake.set(0);
        }
        else{
            currentMotorState = IntakeMotorState.STOP;
            m_intake.set(0);
        }
    }

    public static void debugIntake(){

    }
}