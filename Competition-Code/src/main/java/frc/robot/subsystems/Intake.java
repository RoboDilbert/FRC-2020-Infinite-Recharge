package frc.robot.subsystems;

//import frc.robot.util.Pneumatics;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

    private static CANSparkMax m_intakeMotor;
    private CANEncoder m_intakeMotorEncoder;
    
    private DoubleSolenoid intakeDrop;
    
    public enum ballValues{
        INTAKE,
        STOP,
        REVERSE
    }

    public void init() {
        if(intakeDrop == null){
        intakeDrop = new DoubleSolenoid(Constants.intakeDropForward, Constants.intakeDropBack);
        m_intakeMotor = new CANSparkMax(Constants.motorIntakeID, MotorType.kBrushed);
        m_intakeMotorEncoder = m_intakeMotor.getEncoder();
    }   

    }
    
    // --------------------------------------------------------------------
    // Pneumatic Drop (possibly Motors)
    public void dropIntake() throws InterruptedException {
        intakeDrop.set(Value.kForward);
    }

    public void liftIntake() throws InterruptedException{
        intakeDrop.set(Value.kReverse);
    }

    //---------------------------------------------------------------------
    // intake motor

    public void intakeControl(ballValues value){
        controlBalls(m_intakeMotor, value);
    }

    private void controlBalls(SpeedController m_intake,ballValues value){
        if(value == ballValues.INTAKE){
            m_intake.set(1);
        }
        else if(value == ballValues.REVERSE){
            m_intake.set(-1);
        }
        else{
            m_intake.set(0);
        }
    }

    public void intakeDebug(){
        SmartDashboard.putNumber("intake velocity", m_intakeMotorEncoder.getVelocity());
    }
}