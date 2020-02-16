package frc.robot.util.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;

public class Gyro{

    static AHRS ahrs;
    
    public static void init() {
        ahrs = new AHRS(SPI.Port.kMXP);
        SmartDashboard.putBoolean("init gyro bool", ahrs.isConnected());
        SmartDashboard.putNumber("init gyro",ahrs.getAngle());
        System.out.println("INIT GYRO");
    }

    public static double updateGyroAngle() {
        return ahrs.getAngle();
    }

    public static void getGyroValues(){
        SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
        SmartDashboard.putBoolean("IMU_IsCalibrating", ahrs.isCalibrating());
        SmartDashboard.putNumber("IMU_Yaw", ahrs.getYaw());
        SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
        SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
        SmartDashboard.putNumber("Angle", ahrs.getAngle());
    }
    public static void resetGyro(){
        ahrs.reset();
    }
}