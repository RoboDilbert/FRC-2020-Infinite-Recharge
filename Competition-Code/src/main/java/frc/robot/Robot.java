/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// 2020 COMPETITION ROBOT CODE

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
//  import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.*;
import frc.robot.util.*;
import frc.robot.util.Constants.IntakeToggle;
import frc.robot.util.Pneumatics.*;
import frc.robot.util.sensors.*;
import frc.robot.util.sensors.Limelight.LightMode;
import frc.robot.Autonomous.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

   public static IntakeToggle currentIntakeState;
  @Override
  public void robotInit() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    //SmartDashboard.putData("Auto choices", m_chooser);
    ColorWheel.init();
    Gyro.init();
    Drive.init();
    HangingMove.init();
    Indexer.init();
    Intake.init();
    LiftSystem.init();
    Shooter.init();
    WallOfWheels.init();
    TeleopControl.init();
    Pneumatics.init();
    Pneumatics.controlCompressor(CompressorState.ENABLED);
    
    Limelight.setLedMode(LightMode.OFF);
    //Limelight.initUSBCamera();
    currentIntakeState = IntakeToggle.STOP;
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    // m_autoSelected = "kCustomAuto"; //m_chooser.getSelected();
    //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
    if(TeleopControl.driver.getThrottle() > 0.75){
    Routine.init();
    }
    else if (TeleopControl.driver.getThrottle() < 0.25){
    TrenchAuto.init();
    }
  }

  @Override
  public void autonomousPeriodic() {
    if(TeleopControl.driver.getThrottle() > 0.75){
     Routine.run();
      Routine.debugAuto();
    }
    else if (TeleopControl.driver.getThrottle() < 0.25){
      TrenchAuto.start();
      Routine.debugAuto();
      Indexer.debugIndexer();
    }
    else if(TeleopControl.driver.getThrottle() <= 0.75 && TeleopControl.driver.getThrottle() >= 0.25){
     // Routine.run();
      Routine.debugAuto();
    }
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code herea
    //     //break;
    //   case kDefaultAuto:
        
    //   default:
    //     // Put default auto code here
    //     startCompetition();
    //     SmartDashboard.putString("POOOOOOOOOOO","POOOOOOOOOOOOOOOOOOOO");
    //     Routine.run();
    //     break;
    // }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    TeleopControl.run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledPeriodic(){

  }

  @Override
  public void disabledInit(){
    
  }
}
