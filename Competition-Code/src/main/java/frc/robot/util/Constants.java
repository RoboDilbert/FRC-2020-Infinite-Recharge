package frc.robot.util;

import edu.wpi.first.wpilibj.I2C;

public class Constants{

    // Drive
    public static final int movementRestriction = 2;
    //turret
    public static double turretPower = 0.0;
    // GamePad
    public static final int kGamepadButtonA = 1;
    public static int DRIVER_CONTROLLER_ID = 1;
    public static int CODRIVER_CONTROLLER_ID = 0;
    // Pneumatics
    public static  boolean compressorState = true;
    public static int UTILITIES_COMPRESSOR_PORT = 0;
	public static final boolean COMPRESSOR_ON = true;
    public static final boolean COMPRESSOR_OFF = false;
    public static final int colorWheelForward = 1;
    public static final int colorWheelBack = 2;
    public static final int intakeDropForward = 3;
    public static final int intakeDropBack = 4;
    public static final int pinLockForward = 5;
    public static final int pinLockBack = 6;
    // Camera
    public static final int CAMERA1_WIDTH = 160;
	public static final int CAMERA1_HEIGHT = 120;
    public static final int CAMERA1_FPS = 30;
    public static final int UTIL_CAMERA_0_ID = 0;
    // LED Handler
    public static int Arduino_Address = 0;
    // Gyro
    public static double roboGyro;
    //I2C Port
    public static I2C.Port i2cPort;
    public static double colorVar;
    //TOF Sensor
    public static double rightPPDistance = 0;
    public static double leftPPDistance = 0;
    public static double averagePPLength = 0;
    public static boolean isSeeing;
    public static boolean inPosition = false;
    //Game Data Color
    public static String gameData;
    //Autonomous - Routine
    public static double XPower = 0;
    public static double YPower = 0;
    public static double ZPower = 0;
    public static double cameraX = 0;
    public static double angle = 0;
    public static double complimentAngle = 0;
    public static double feedForward = 0.03;
    //Color Wheel
    public static double searchSpeed = 0.5;
    public static String initalColor;
    public static String nextColor;
    public static String bufferColor;
    public static int colorCount = 0;
   
    // Lifter
    public static int lifterSpeed = 1;
    
//Can IDs
public static final int leftFrontDeviceID = 1;
public static final int rightBackDeviceID = 2;
public static final int leftBackDeviceID = 3;
public static final int rightFrontDeviceID = 4;
public static final int kServoID = 5;
public static final int WallOfWheelsID = 6;
public static final int motorIntakeID = 7;
public static final int motorSpinID = 8;
public static final int FeedID = 9;
public static final int ShootID = 10;
public static final int UPPER_INDEXER_ID = 13;
public static final int LOWER_INDEXER_ID = 14;
public static final int LEFT_PP_ID = 15;
public static final int RIGHT_PP_ID = 16;
public static final int ShooterDeviceID = 17;
public static final int lifterWheelLeft = 18;
public static final int lifterWheelRight = 19;
public static final int hangingWheel = 20;


}