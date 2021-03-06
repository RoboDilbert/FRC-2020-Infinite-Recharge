/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  VictorSPX mleftMaster, mrightMaster, mleftFollower, mrightFollower;

  @Override
  public void robotInit() {
    mleftMaster = new VictorSPX(0);
    mrightMaster = new VictorSPX(1);

    mleftFollower = new VictorSPX(2);
    mrightFollower = new VictorSPX(3);

    mleftFollower.follow(mleftMaster);
    mrightFollower.follow(mrightMaster);

     m_leftStick = new Joystick(0);
     m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    mleftMaster.set(ControlMode.PercentOutput, m_leftStick.getY());
    mrightMaster.set(ControlMode.PercentOutput, m_rightStick.getY());

  }
}
