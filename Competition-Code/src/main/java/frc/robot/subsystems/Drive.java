package frc.robot.subsystems;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import frc.robot.util.sensors.*;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

public class Drive{

<<<<<<< HEAD
    public static MecanumDrive my_Robot;
    public static final CANSparkMax m_leftFrontMotor = new CANSparkMax(Constants.leftFrontDeviceID, MotorType.kBrushless);
    public static final CANSparkMax m_leftBackMotor = new CANSparkMax(Constants.leftBackDeviceID, MotorType.kBrushless);
    public static final CANSparkMax m_rightFrontMotor = new CANSparkMax(Constants.rightFrontDeviceID, MotorType.kBrushless);
    public static final CANSparkMax m_rightBackMotor = new CANSparkMax(Constants.rightBackDeviceID, MotorType.kBrushless);
    public static CANEncoder m_leftFrontEncoder;
    public static CANEncoder m_leftBackEncoder;
    public static CANEncoder m_rightFrontEncoder;
    public static CANEncoder m_rightBackEncoder;

    public static TimeOfFlight leftPP = new TimeOfFlight(Constants.LEFT_PP_ID);
    public static TimeOfFlight rightPP = new TimeOfFlight(Constants.RIGHT_PP_ID);
=======
    private static MecanumDrive my_Robot;
    private static final CANSparkMax m_leftFrontMotor = new CANSparkMax(Constants.leftFrontDeviceID, MotorType.kBrushless);
    private static final CANSparkMax m_leftBackMotor = new CANSparkMax(Constants.leftBackDeviceID, MotorType.kBrushless);
    private static final CANSparkMax m_rightFrontMotor = new CANSparkMax(Constants.rightFrontDeviceID, MotorType.kBrushless);
    private static final CANSparkMax m_rightBackMotor = new CANSparkMax(Constants.rightBackDeviceID, MotorType.kBrushless);
    private static CANEncoder m_leftFrontEncoder;
    private static CANEncoder m_leftBackEncoder;
    private static CANEncoder m_rightFrontEncoder;
    private static CANEncoder m_rightBackEncoder;
>>>>>>> 3d5ae033ea352f834342e1d2ec9ebbb9c23c013c
    
    
    public static void init(){
        my_Robot = new MecanumDrive(m_leftFrontMotor, m_leftBackMotor, m_rightFrontMotor, m_rightBackMotor);
        m_leftFrontMotor.setIdleMode(IdleMode.kBrake);
        m_leftBackMotor.setIdleMode(IdleMode.kBrake);
        m_rightFrontMotor.setIdleMode(IdleMode.kBrake);
        m_rightBackMotor.setIdleMode(IdleMode.kBrake);
        m_leftFrontEncoder = m_leftFrontMotor.getEncoder();
        m_leftBackEncoder = m_leftBackMotor.getEncoder();
        m_rightFrontEncoder = m_rightFrontMotor.getEncoder();
        m_rightBackEncoder = m_rightBackMotor.getEncoder();

    }

    public static void run(double stickX, double stickY, double stickZ, double roboGyro){
        double xDeadband = .1;
        double yDeadband = .1;
        double zDeadband = .1;
        if(Math.abs(stickX) < xDeadband){
            stickX = 0;
        }
        if(Math.abs(stickY) < yDeadband){
            stickY = 0;
        }
        if(Math.abs(stickZ) < zDeadband){
            stickZ = 0;
        }
        my_Robot.driveCartesian(stickX, -stickY, stickZ, -roboGyro);
    }

    public static void lineUpShot(double stickX, double stickY, double stickZ, double roboGyro){
        double x = Limelight.tx.getDouble(0.0);
        if(x > 0){
            my_Robot.driveCartesian(-(Math.pow((0.0025 * x), 2)), stickY, 0, -roboGyro);
        }
        else if(x < 0){
            my_Robot.driveCartesian((Math.pow((0.0025 * x), 2)), stickY, 0, -roboGyro);
        }
        else{
            my_Robot.driveCartesian(0, 0, 0, 0);
        }
    }
    public static void driveWithoutTurn(double stickX, double stickY, double roboGyro){
        Drive.run(stickX, stickY, 0, roboGyro);
    }

    

    public static void getSpeed(){
        SmartDashboard.getNumber("LeftFrontSpeed", m_leftFrontEncoder.getVelocity());
        SmartDashboard.getNumber("LeftBackSpeed", m_leftBackEncoder.getVelocity());
        SmartDashboard.getNumber("RightFrontSpeed", m_rightFrontEncoder.getVelocity());
        SmartDashboard.getNumber("RightBackSpeed", m_rightBackEncoder.getVelocity());
        SmartDashboard.updateValues();
    }
    public static void getPosition(){
        SmartDashboard.getNumber("LeftFrontSpeed", m_leftFrontEncoder.getPosition());
        SmartDashboard.getNumber("LeftBackSpeed", m_leftBackEncoder.getPosition());
        SmartDashboard.getNumber("RightFrontSpeed", m_rightFrontEncoder.getPosition());
        SmartDashboard.getNumber("RightBackSpeed", m_rightBackEncoder.getPosition());
        SmartDashboard.updateValues();
    }

    public static void getPPLength(double left, double right, double average){
        right = rightPP.getRange();
        left = leftPP.getRange();
        average = (right + left)/2;
   }
   

   public static void lockedOn(boolean sight){
       if(rightPP.isRangeValid() && leftPP.isRangeValid()){
           sight = true;
       }
       else{
           sight = false;
       }
   }

   public static void setPPRangeMode(String mode){
       if(mode.equals("Long")){
           leftPP.setRangingMode(RangingMode.Long, 100);
           rightPP.setRangingMode(RangingMode.Long, 100);
       }
       else if(mode.equals("Medium")){
           leftPP.setRangingMode(RangingMode.Medium, 100);
           rightPP.setRangingMode(RangingMode.Medium, 100);
       }
       else if(mode.equals("short")){
           leftPP.setRangingMode(RangingMode.Short, 100);
           rightPP.setRangingMode(RangingMode.Short, 100);
       }
   }
   public static void arePPEqual(boolean output){
       if(leftPP.getRange() > 706 && leftPP.getRange() < 806 && rightPP.getRange() < 806 && rightPP.getRange() > 706){
           output = true;
       }
       else{
           output = false;
       }
   }
   public static void outputTOFData(){
     double test =  rightPP.getRange();
     double test2 = leftPP.getRange();
       SmartDashboard.putNumber("LeftPP", test2);
       SmartDashboard.putNumber("RightPP", test);
       System.out.println(test);
       SmartDashboard.updateValues();
   }
}