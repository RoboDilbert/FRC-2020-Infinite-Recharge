package frc.robot.subsystems;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import frc.robot.util.sensors.*;

public class Drive{

    private static MecanumDrive my_Robot;
    private static final CANSparkMax m_leftFrontMotor = new CANSparkMax(Constants.leftFrontDeviceID, MotorType.kBrushless);
    private static final CANSparkMax m_leftBackMotor = new CANSparkMax(Constants.leftBackDeviceID, MotorType.kBrushless);
    private static final CANSparkMax m_rightFrontMotor = new CANSparkMax(Constants.rightFrontDeviceID, MotorType.kBrushless);
    private static final CANSparkMax m_rightBackMotor = new CANSparkMax(Constants.rightBackDeviceID, MotorType.kBrushless);
    public static CANEncoder m_leftFrontEncoder;
    public static CANEncoder m_leftBackEncoder;
    public static CANEncoder m_rightFrontEncoder;
    public static CANEncoder m_rightBackEncoder;
    
    
    public static void init(){
        my_Robot = new MecanumDrive(m_leftFrontMotor, m_leftBackMotor, m_rightFrontMotor, m_rightBackMotor);
        m_leftFrontEncoder = m_leftFrontMotor.getEncoder();
        m_leftBackEncoder = m_leftBackMotor.getEncoder();
        m_rightFrontEncoder = m_rightFrontMotor.getEncoder();
        m_rightBackEncoder = m_rightBackMotor.getEncoder();

    }

    public static void run(double stickX, double stickY, double stickZ, double roboGyro){
        double xDeadband = .05;
        double yDeadband = .05;
        double zDeadband = .05;
        if(stickX < xDeadband){
            stickX = 0;
        }
        if(stickY < yDeadband){
            stickY = 0;
        }
        if(stickZ < zDeadband){
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

    public static void getSpeed(){
        SmartDashboard.getNumber("LeftFrontSpeed", m_leftFrontEncoder.getVelocity());
        SmartDashboard.getNumber("LeftBackSpeed", m_leftBackEncoder.getVelocity());
        SmartDashboard.getNumber("RightFrontSpeed", m_rightFrontEncoder.getVelocity());
        SmartDashboard.getNumber("RightBackSpeed", m_rightBackEncoder.getVelocity());
    }
    public static void getPosition(){
        SmartDashboard.getNumber("LeftFrontSpeed", m_leftFrontEncoder.getPosition());
        SmartDashboard.getNumber("LeftBackSpeed", m_leftBackEncoder.getPosition());
        SmartDashboard.getNumber("RightFrontSpeed", m_rightFrontEncoder.getPosition());
        SmartDashboard.getNumber("RightBackSpeed", m_rightBackEncoder.getPosition());
    }
}