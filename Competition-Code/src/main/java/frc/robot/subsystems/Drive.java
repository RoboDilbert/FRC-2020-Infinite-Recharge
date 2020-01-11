package frc.robot.subsystems;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.SensorType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SolenoidBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.networktables.*;

import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController;


public class Drive{

    
    // private static final CANSparkMax m_leftFrontMotor = new CANSparkMax(Constants.leftFrontDeviceID, MotorType.kBrushless);
    // private static final CANSparkMax m_leftBackMotor = new CANSparkMax(Constants.leftBackDeviceID, MotorType.kBrushless);
    // private static final CANSparkMax m_rightFrontMotor = new CANSparkMax(Constants.rightFrontDeviceID, MotorType.kBrushless);
    // private static final CANSparkMax m_rightBackMotor = new CANSparkMax(Constants.rightBackDeviceID, MotorType.kBrushless);
    // private static final CANSparkMax m_turretMotor = new CANSparkMax(Constants.turretDeviceID, MotorType.kBrushed);
    
    
    public static void init(){
            
        // CANEncoder m_leftFrontEncoder = m_leftFrontMotor.getEncoder();
        // CANEncoder m_leftBackEncoder = m_leftBackMotor.getEncoder();
        // CANEncoder m_rightFrontEncoder = m_rightFrontMotor.getEncoder();
        //CANEncoder m_rightBackEncoder = m_rightBackMotor.getEncoder();

    }
}