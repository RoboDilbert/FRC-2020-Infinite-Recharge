package frc.robot.util;

public class Constants{

    // Drive
    public static final int movementRestriction = 2;

    // GamePad
    public static final int kGamepadButtonA = 1;
    public static final int DRIVER_CONTROLLER_ID = 0;
    public static final int CODRIVER_CONTROLLER_ID = 1;
    public static final int BUTTON_LAYOUT_CONTROLLER_ID = 2;

    // Pneumatics
    public static final boolean compressorState = true;
    public static final int UTILITIES_COMPRESSOR_PORT = 0;
    public static final int colorWheelSolenoid = 0;
    public static final int intakeDropForwardSolenoid = 1;
    public static final int intakeDropBackSolenoid = 2;
    public static final int pinLockForwardSolenoid = 5;
    public static final int pinLockBackSolenoid = 6;

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
    public static boolean notShot = true;

    //Color Wheel
    public static final double searchSpeed = 0.5;
    public static final double wheelSpeed = .4;
    public static String initalColor;
    public static String nextColor;
    public static String bufferColor;
    public static int colorCount = 0;
   
    // Hanger Move
    public static final double hangerSpeed = .8;

    // Indexer
    public static final double feedIndexerSpeed = .4;
    public static final double shootIndexerSpeed = .3;

    // Intake
    public static final double intakeSpeed = .3;

    // LiftSystem
    public static final double lifterSpeedUp = .9;
    public static final double lifterSpeedDown = .5;
    // Shooter
    public static final double shooterSpeed = .70;
    public static boolean shootFlag = false;

    // Wall of Wheels
    public static final double wallOfWheelsSpeed = 0.90;
    
    //Can IDs
    public static final int leftFrontMotorID = 1;
    public static final int leftBackMotorID = 2;
    public static final int rightFrontMotorID = 3;
    public static final int rightBackMotorID = 4;
    public static final int WallOfWheelsMotorID = 6;
    public static final int IntakeMotorID = 7;
    public static final int colorWheelMotorID = 8;//not hooked up
    public static final int IndexShootMotorID = 9;
    public static final int ShooterMotorID = 10;
    public static final int UPPER_INDEXER_TOF_ID = 11;
    public static final int LOWER_INDEXER_TOF_ID = 12;
    public static final int LEFT_PP_TOF_ID = 13;
    public static final int RIGHT_PP_TOF_ID = 14;
    public static final int IndexFeedMotorID = 15;
    public static final int lifterLeftMotorID = 16;
    public static final int lifterRightMotorID = 17;
    public static final int hangingWheelMotorID = 18;

    public static enum IntakeToggle{
        FORWARD,
        REVERSE,
        STOP;
    }

    public static enum ShooterToggle{
        
    }
    
}