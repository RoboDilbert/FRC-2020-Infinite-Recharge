package frc.robot.util;

import edu.wpi.first.wpilibj.I2C;

public class Constants{

    // Drive
    public static final int leftFrontDeviceID = 1; 
    public static final int leftBackDeviceID = 3;
    public static final int rightFrontDeviceID = 5;
    public static final int rightBackDeviceID = 2;
    public static final int kServoID = 7;
    public static final int movementRestriction = 2;
    //turret
    public static final int turretDeviceID = 6;
    public static double turretPower = 0.0;
    // GamePad
    public static final int kGamepadButtonA = 1;
    public static int DRIVER_CONTROLLER_ID = 0;
    public static int CODRIVER_CONTROLLER_ID = 1;
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
}