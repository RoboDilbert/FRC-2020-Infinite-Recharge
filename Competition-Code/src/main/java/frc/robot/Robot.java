/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// 2020 COMPETITION ROBOT CODE

package frc.robot;

import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.*;
import frc.robot.util.sensors.*;
import frc.robot.Autonomous.*;
//import frc.robot.Autonomous.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    //SmartDashboard.putData("Auto choices", m_chooser);
    System.out.println("alkwdfjlkasdjf");

    Drive.init();
    TeleopControl.init();
    Limelight.initUSBCamera();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    // m_autoSelected = "kCustomAuto";//m_chooser.getSelected();
    //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
    
   
  }

  @Override
  public void autonomousPeriodic() {
    Routine.run();
    
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
  public  void teleopPeriodic() {
    TeleopControl.run();
    // SmartDashboard.getNumber("LeftFrontSpeed", Drive.m_leftFrontEncoder.getVelocity());
    // SmartDashboard.getNumber("LeftBackSpeed", Drive.m_leftBackEncoder.getVelocity());
    // SmartDashboard.getNumber("RightFrontSpeed", Drive.m_rightFrontEncoder.getVelocity());
    // SmartDashboard.getNumber("RightBackSpeed", Drive.m_rightBackEncoder.getVelocity());
    SmartDashboard.updateValues();    //TOFSensor.outputTOFData();
    //SmartDashboard.putNumber("x", Routine.cameraX);
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
