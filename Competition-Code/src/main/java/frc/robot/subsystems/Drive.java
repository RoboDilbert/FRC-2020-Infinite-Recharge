package frc.robot.subsystems;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Indexer.IndexerState;
import frc.robot.subsystems.Indexer.SelectIndexer;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.WallOfWheels.WallState;
import frc.robot.util.Constants;
import frc.robot.util.sensors.*;
import frc.robot.util.sensors.Limelight.LightMode;
import frc.robot.TeleopControl;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

public class Drive{

    public static MecanumDrive my_Robot;
    public static final CANSparkMax m_leftFrontMotor = new CANSparkMax(Constants.leftFrontMotorID, MotorType.kBrushless);
    public static final CANSparkMax m_leftBackMotor = new CANSparkMax(Constants.leftBackMotorID, MotorType.kBrushless);
    public static final CANSparkMax m_rightFrontMotor = new CANSparkMax(Constants.rightFrontMotorID, MotorType.kBrushless);
    public static final CANSparkMax m_rightBackMotor = new CANSparkMax(Constants.rightBackMotorID, MotorType.kBrushless);
    public static CANEncoder m_leftFrontEncoder;
    public static CANEncoder m_leftBackEncoder;
    public static CANEncoder m_rightFrontEncoder;
    public static CANEncoder m_rightBackEncoder;

    public static TimeOfFlight leftPP = new TimeOfFlight(Constants.LEFT_PP_TOF_ID);
    public static TimeOfFlight rightPP = new TimeOfFlight(Constants.RIGHT_PP_TOF_ID);
    public static double rightPPDistance = 0;
    public static double leftPPDistance = 0;
    public static double averagePPLength = 0;

    private static double tXPower;
    private static double tYPower;
    private static double tZPower;
    private static double tCameraX;
    private static double tCameraY;
    private static double tCameraA;
    private static double tlimelightDistance;
    private static double tFeedForward;
    private static boolean tIsSeeing;
    public static boolean tInPosition;


    
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

        tXPower = 0;
        tYPower = 0;
        tZPower = 0;
        tCameraX = 0;
        tCameraY = 0;
        tCameraA = 0;
        tFeedForward = 0.03;
        tIsSeeing = false;
        tInPosition = false;
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
        Drive.rightPPDistance = Drive.rightPP.getRange();
                Drive.leftPPDistance = Drive.leftPP.getRange();
        Drive.averagePPLength = (Drive.rightPPDistance + Drive.leftPPDistance)/2;
    }

    public static void lineUpShot(){
            Limelight.setLedMode(LightMode.ON);
            tCameraX = Limelight.tx.getDouble(0.0);
            
            if(!tInPosition){
                Shooter.controlShooter(ShooterState.FORWARD);
                if(Drive.rightPP.getRange() >  0 || Drive.leftPP.getRange() > 0){
                    tIsSeeing = true;
                }
                else{
                    tIsSeeing = false;
                }
                Drive.rightPPDistance = Drive.rightPP.getRange();
                Drive.leftPPDistance = Drive.leftPP.getRange();
                Drive.averagePPLength = (Drive.rightPPDistance + Drive.leftPPDistance)/2;

                // X Power
                //limelight locked on and X value of limelight
                if(tCameraX < -1){
                    tXPower = Math.pow((Math.pow((0.18 * tCameraX), 2)), 1/1.5);
                    if(tXPower > .24){//.2
                        tXPower = .24;
                    }
                }
                else if(tCameraX > 1){
                    tXPower = -Math.pow((Math.pow((0.18 * tCameraX), 2)), 1/1.5);
                    if(tXPower < -.24){//-.2
                        tXPower = -.24;
                    }
                }
                else{
                    tXPower = 0;
                }

                //Y Power
                if(tIsSeeing == true){
                    if(Drive.leftPPDistance < 1000 && Drive.leftPPDistance > 0 || Drive.rightPPDistance < 1000 && Drive.rightPPDistance > 0){
                        tYPower = -0.135;//.1
                    }
                    else if(Drive.averagePPLength > 1000){
                        tYPower = ((1.8*Math.pow((Drive.averagePPLength - 150) , 2)) /10000000) + tFeedForward;
                        if(tYPower > 0.27){//.2
                            tYPower = 0.27;
                        }
                    }
                    if(Drive.leftPP.getRange() < 1100 && Drive.leftPP.getRange() > 1000 &&
                        Drive.rightPP.getRange() < 1100 && Drive.rightPP.getRange() > 1000){
                        tYPower = 0;
                    }
                }
                else if(tIsSeeing == false){//If we don't see anything, drive straight
                    tYPower = 0.15;//.1
                }
            
                //ZPower
                if(Drive.averagePPLength < 2508){
                    if(Drive.leftPPDistance == 0 || Drive.rightPPDistance == 0){
                        tZPower = 0;
                    }
                    else{
                        tZPower = ((Drive.leftPPDistance - Drive.rightPPDistance) / 750) + tFeedForward;
                        if(tZPower > 0.2){//.2
                            tZPower = 0.2;
                        }
                        else if(tZPower < -0.2){
                            tZPower = -0.2;
                        }
                    }
                }
                else{
                    tZPower = 0;
                }
        
                if(Drive.averagePPLength < 4000 && Drive.averagePPLength > 2000){
                    Drive.rightPP.setRangingMode(RangingMode.Long, 25);
                    Drive.leftPP.setRangingMode(RangingMode.Long, 25);
                }
                else if(Drive.averagePPLength < 2000 && Drive.averagePPLength > 25){
                    Drive.rightPP.setRangingMode(RangingMode.Medium, 25);
                    Drive.leftPP.setRangingMode(RangingMode.Medium, 25);
                }
                else if(Drive.averagePPLength < 4000 && Drive.averagePPLength > 25){
                    Drive.rightPP.setRangingMode(RangingMode.Short, 25);
                    Drive.leftPP.setRangingMode(RangingMode.Short, 25);
                }
            
                Drive.run(-tXPower, -tYPower, tZPower, 0);

                if(Drive.leftPP.getRange() > 1000 && Drive.leftPP.getRange() < 1100
                && Drive.rightPP.getRange() < 1100 && Drive.rightPP.getRange() > 1000
                && tCameraX > -1 && tCameraX < 1){
                    tInPosition = true;
                }
            }
            else if(tInPosition == true && TeleopControl.driver.getRawButton(1)){
                Shooter.controlShooter(ShooterState.FORWARD);
                Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
                Indexer.indexerClear();
            }
            
    }
    // auto shoot 2 test

    public static void lockOn(double stickX, double stickY, double roboGyro){
        Limelight.setLedMode(LightMode.ON);
        tlimelightDistance = (72.19 / Math.tan(Math.toRadians(38 + Limelight.ty.getDouble(0.0))))-24;
        tCameraX = Limelight.tx.getDouble(0.0);
        tCameraY = Limelight.ty.getDouble(0.0);
        tCameraA = Limelight.ta.getDouble(0.0);
        
            // Z Power
            //limelight locked on and X value of limelight
            if(tCameraX > -0.18){
               tZPower = 0.1 * Math.pow(tCameraX, 1/2);
                //tZPower = Math.pow((Math.pow((0.2*tCameraX), 2)), 1/1.5)/5 + tFeedForward;
                if(tZPower > .12){//.2
                    tZPower = .12;
                }
            }
            else if(tCameraX < 0.18){
                tZPower = -(0.1 * Math.pow(tCameraX, 1/2));
                //tZPower = -Math.pow((Math.pow((0.2 * tCameraX), 2)), 1/1.5)/5 + tFeedForward;
                if(tZPower < -.12){//-.2
                    tZPower = -.12;
                }
            }
            else{
                tZPower = 0;
            }
            /* adjustable hood calculations  (limeLight is on the hood (allows for this calculation))
            if(tCameraY < -1){
                function for hood -> gives a positive double power value of hoodPower
            }
            else if(tCameraY > 1){
                function for hood -> gives a negative double power value of hoodPower
            }

            hood.setPower(hoodPower);
            */



            /* power Calculations with LimeLight Area ( may not be final way of calculating)
            (calculate power needed from multiple positions on the field and plot points to create a starting function)
            
            function of power/tCameraA

            use value of calculated power for shooter power
            */
            // if(stickX > 0.475){
            //    tZPower = Math.pow((Math.pow((0.18 * tCameraX + 1), 2)), 1/1.5)/5 + tFeedForward;
            // }
            // else if(stickX < 0.525){
            //     tZPower = -Math.pow((Math.pow((0.18 * tCameraX - 1), 2)), 1/1.5)/5 + tFeedForward;
            // }
            if(tlimelightDistance < 110){
                Drive.run(stickX, stickY, tZPower, roboGyro);
            }
            else{
                Drive.run(stickX, -0.2, tZPower, roboGyro);
            }
            if(tCameraX > -1 && tCameraX < 1 /* && tCameraY > -1 && tCameraY < 1 */){
                tInPosition = true;
            }
        
        // if(TeleopControl.driver.getRawButton(1)){
        //     Shooter.controlShooter(ShooterState.FORWARD);
        //     // shooter.controlShooter(ShooterState.CALCULATED)
        //     Indexer.controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
        //     Indexer.controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
        //     Indexer.indexerClear();
        // }
        
}
    
    public static void driveWithoutTurn(double stickX, double stickY, double roboGyro){
        Drive.run(stickX, stickY, 0, roboGyro);
    }

    

    public static void getSpeed(){
        SmartDashboard.putNumber("LeftFrontSpeed", m_leftFrontEncoder.getVelocity());
        SmartDashboard.putNumber("LeftBackSpeed", m_leftBackEncoder.getVelocity());
        SmartDashboard.putNumber("RightFrontSpeed", m_rightFrontEncoder.getVelocity());
        SmartDashboard.putNumber("RightBackSpeed", m_rightBackEncoder.getVelocity());
        SmartDashboard.updateValues();
    }
    public static void getPosition(){
        SmartDashboard.putNumber("LeftFrontSpeed", m_leftFrontEncoder.getPosition());
        SmartDashboard.putNumber("LeftBackSpeed", m_leftBackEncoder.getPosition());
        SmartDashboard.putNumber("RightFrontSpeed", m_rightFrontEncoder.getPosition());
        SmartDashboard.putNumber("RightBackSpeed", m_rightBackEncoder.getPosition());
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
   public static void LineUpData(){
    
    // SmartDashboard.putBoolean("isSeeing", tIsSeeing);
    // SmartDashboard.putNumber("LeftDistance", Drive.leftPPDistance);
    // SmartDashboard.putNumber("RightDistance", Drive.rightPPDistance);
    SmartDashboard.putNumber("AverageDistance", Drive.averagePPLength);
    SmartDashboard.putNumber("PP FT from back of robot", (Drive.averagePPLength / 305) + 2);
    // SmartDashboard.putNumber("YPower", tYPower);
    // SmartDashboard.putNumber("LimelightX", tCameraX);
    // SmartDashboard.putNumber("XPower", tXPower);
    // SmartDashboard.putNumber("ZPower", tZPower);
    SmartDashboard.putBoolean("tInPosiiton", tInPosition);
    SmartDashboard.updateValues();
   }
}