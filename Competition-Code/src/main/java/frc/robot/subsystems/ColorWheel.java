package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
//import frc.robot.util.sensors.RevColor;
//import frc.robot.util.Pneumatics;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.util.Constants;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ColorWheel{

public enum SearchValue{
    COLOR,
    REVERSE,
    FORWARD,
    STOP
}

String gameData = DriverStation.getInstance().getGameSpecificMessage();

private static CANSparkMax ColorWheelMotor;
private static CANEncoder ColorWheelMotorEncoder;
    
private static DoubleSolenoid wheelCylinder;
// private RevColor colorSensor;
// private int colorCounter = Constants.colorCount;

public static void init() {
   wheelCylinder = new DoubleSolenoid(Constants.colorWheelForward, Constants.colorWheelBack);
   ColorWheelMotor = new CANSparkMax(Constants.colorWheelMotorID, MotorType.kBrushless);
   ColorWheelMotorEncoder = ColorWheelMotor.getEncoder();
   ColorWheelMotor.setIdleMode(IdleMode.kBrake);
   //colorSensor = new RevColor();
}   

public static void WheelSearch(SearchValue value){
    controlColorWheel(ColorWheelMotor, value);
}

public void powerLevelOne(SearchValue value, String color){
 
}

private static void controlColorWheel(SpeedController spinThatWheel, SearchValue value){
    if(value == SearchValue.COLOR){
        spinThatWheel.set(Constants.searchSpeed);
    }
    else if(value == SearchValue.FORWARD){
        spinThatWheel.set(Constants.wheelSpeed);
    }
    else if(value == SearchValue.REVERSE){
        spinThatWheel.set(-Constants.wheelSpeed);
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

public void colorWheelDebug(){
    SmartDashboard.putNumber("Color Wheel Velocity", ColorWheelMotorEncoder.getVelocity());
}

}