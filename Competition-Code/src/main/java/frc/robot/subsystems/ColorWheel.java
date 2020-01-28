package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.util.sensors.RevColor;
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
import frc.robot.util.Constants;



public class ColorWheel{

String gameData = DriverStation.getInstance().getGameSpecificMessage();

private static CANSparkMax m_wheelSpin;
private CANEncoder m_wheelSpinEncoder;
    
private DoubleSolenoid wheelCylinder;


public void init() {
    if(wheelCylinder == null){
    wheelCylinder = new DoubleSolenoid(1, 2);
    m_wheelSpin = new CANSparkMax(Constants.motorSpinID, MotorType.kBrushless);
    m_wheelSpinEncoder = m_wheelSpin.getEncoder();
    }
}   




public void spinThatWheel(SpeedController spinThatWheel){
spinThatWheel.set(Constants.searchSpeed);
}

public void spinWheelReverse(SpeedController spinReverse){
spinReverse.set(-1);
}

public void SpinWheelStop(SpeedController spinStop){
spinStop.set(0);
}

public void dropWheel() throws InterruptedException {
    wheelCylinder.set(Value.kForward);
}

public void liftWheel() throws InterruptedException{
    wheelCylinder.set(Value.kReverse);

}



}