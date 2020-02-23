package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.util.sensors.RevColor;
//import frc.robot.util.Pneumatics;
import edu.wpi.first.wpilibj.Solenoid;
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

    private static String gameData = DriverStation.getInstance().getGameSpecificMessage();
    public static String gameDataColor;

    private static CANSparkMax ColorWheelMotor;
    private static CANEncoder ColorWheelMotorEncoder;
    
    private static Solenoid wheelCylinder;
    public static RevColor colorSensor;


    //private int colorCounter = Constants.colorCount;

    public static void init() {
        wheelCylinder = new Solenoid(Constants.colorWheelSolenoid);
        ColorWheelMotor = new CANSparkMax(Constants.colorWheelMotorID, MotorType.kBrushless);
        ColorWheelMotorEncoder = ColorWheelMotor.getEncoder();
        ColorWheelMotor.setIdleMode(IdleMode.kBrake);
        colorSensor = new RevColor();
    }   

    public static void controlColorWheel(SearchValue value){
        powerColorWheel(ColorWheelMotor, value);
    }

    public void powerLevelOne(SearchValue value, String color){
 
    }

    private static void powerColorWheel(SpeedController spinThatWheel, SearchValue value){
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

    public static void dropWheel(){
        wheelCylinder.set(true);
    }

    public static void liftWheel(){
        wheelCylinder.set(false);
    }

    public static void colorWheelDebug(){
        //SmartDashboard.putNumber("Color Wheel Velocity", ColorWheelMotorEncoder.getVelocity());
        SmartDashboard.putString("Color", colorSensor.searchColor());
        colorSensor.displayColor();
        SmartDashboard.updateValues();
    }

    public static void getGameData(){
        if(gameData.length() > 0)
        {
        switch (gameData.charAt(0))
        {
            case 'B' :
            //Blue case code
            gameDataColor = "BLUE";
            break;
            case 'G' :
            //Green case code
            gameDataColor = "GREEN";
            break;
            case 'R' :
            //Red case code
            gameDataColor = "RED";
            break;
            case 'Y' :
            //Yellow case code
            gameDataColor = "YELLOW";
            break;
            default :
            //This is corrupt data
            gameDataColor = "NULL";
            break;
    }
    }else {
    //Code for no data received yet
}
}
}