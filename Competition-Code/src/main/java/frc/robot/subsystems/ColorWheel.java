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
public enum SearchValue{
COLOR,
REVERSE,
FORWARD,
STOP
}

String gameData = DriverStation.getInstance().getGameSpecificMessage();

private static CANSparkMax m_wheelSpin;
private CANEncoder m_wheelSpinEncoder;
    
private DoubleSolenoid wheelCylinder;
private RevColor colorSensor;
private int colorCounter = Constants.colorCount;

public void init() {
    if(wheelCylinder == null){
    wheelCylinder = new DoubleSolenoid(1, 2);
   m_wheelSpin = new CANSparkMax(Constants.motorSpinID, MotorType.kBrushless);
   m_wheelSpinEncoder = m_wheelSpin.getEncoder();
   m_wheelSpin.setIdleMode(IdleMode.kBrake);
   colorSensor = new RevColor();
    }
}   

public void WheelSearch(SearchValue value){
    spinThatWheelControl(m_wheelSpin, value);
}

public void powerLevelOne(SearchValue value, String color){
   
    

}


private void spinThatWheelControl(SpeedController spinThatWheel, SearchValue value){
if(value == SearchValue.COLOR){
    spinThatWheel.set(Constants.searchSpeed);
}
else if(value == SearchValue.FORWARD){
    spinThatWheel.set(1);
}
else if(value == SearchValue.REVERSE){
    spinThatWheel.set(-1);
}
else if (value == SearchValue.STOP){
    spinThatWheel.set(0);
}
}

public void dropWheel() throws InterruptedException {
    wheelCylinder.set(Value.kForward);
}

public void liftWheel() throws InterruptedException{
    wheelCylinder.set(Value.kReverse);

}



}