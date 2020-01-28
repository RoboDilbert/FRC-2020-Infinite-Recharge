package frc.robot.subsystems;

import frc.robot.util.Pneumatics;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;

public class Intake {

    private static CANSparkMax m_intakeMotor;
    private CANEncoder m_intakeMotorEncoder;
    
    public DoubleSolenoid intakeDrop;
    
    
    public void init() {
        if(intakeDrop == null){
        intakeDrop = new DoubleSolenoid(1, 2);
        m_intakeMotor = new CANSparkMax(Constants.motorIntakeID, MotorType.kBrushless);
        m_intakeMotorEncoder = m_intakeMotor.getEncoder();
    }   

    }

    // --------------------------------------------------------------------
    // Pneumatic Drop (possibly Motors)
    public void dropIntake() throws InterruptedException {
        intakeDrop.set(Value.kForward);
        Thread.sleep(300);
        intakeDrop.set(Value.kOff);
    }

    public void liftIntake() throws InterruptedException{
        intakeDrop.set(Value.kReverse);
        Thread.sleep(500);
    }

    //---------------------------------------------------------------------
    // intake motor
    public void intakeBalls(SpeedController m_intakeMotorSpeed){
        m_intakeMotorSpeed.set(1);
    }

    public void stopBalls(SpeedController m_intakeMotorSpeed){
        m_intakeMotorSpeed.set(0);
    }

    public void reverseBalls(SpeedController m_intakeMotorSpeed){
        m_intakeMotorSpeed.set(-1);
    }
}