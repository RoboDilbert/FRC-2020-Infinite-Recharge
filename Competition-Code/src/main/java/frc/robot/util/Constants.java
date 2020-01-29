package frc.robot.util;

import edu.wpi.first.wpilibj.I2C;

public class Constants{

    // Drive
    public static final int leftFrontDeviceID = 1; 
    public static final int leftBackDeviceID = 3;
    public static final int rightFrontDeviceID = 5;
    public static final int rightBackDeviceID = 2;
    public static final int motorIntakeID = 9;
    public static final int motorSpinID = 10;
    public static final int kServoID = 7;
    public static final int movementRestriction = 2;
    //turret
    public static final int turretDeviceID = 6;
    public static double turretPower = 0.0;
    // GamePad
    public static final int kGamepadButtonA = 1;
    public static int DRIVER_CONTROLLER_ID = 1;
    public static int CODRIVER_CONTROLLER_ID = 0;
    // Pneumatics
    public static int UTILITIES_COMPRESSOR_PORT = 0;
	public static final boolean COMPRESSOR_ON = true;
    public static final boolean COMPRESSOR_OFF = false;
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
    public static int LEFT_PP_ID = 17;
    public static int RIGHT_PP_ID = 18;
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
    public static int colorCount = 0;

   
}