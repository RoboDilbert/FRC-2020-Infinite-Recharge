package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Constants;
import frc.robot.util.sensors.*;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.SpeedController;


public class Indexer{

    public static final CANSparkMax indexFeed = new CANSparkMax(Constants.FeedID, MotorType.kBrushless);
    public static final CANSparkMax indexShoot = new CANSparkMax(Constants.ShootID, MotorType.kBrushless);
    public static CANEncoder feedEncoder;
    public static CANEncoder shootEncoder;


    public static TimeOfFlight uppperIndex = new TimeOfFlight(Constants.UPPER_INDEXER_ID);
    public static TimeOfFlight lowerIndex = new TimeOfFlight(Constants.LOWER_INDEXER_ID);

    public static int ballz = 0;

    public static enum IndexPower{
        SPIN, STOP;
    }

    public static void init(){
        indexFeed.setIdleMode(IdleMode.kBrake);
        indexShoot.setIdleMode(IdleMode.kCoast);
        feedEncoder = indexFeed.getEncoder();
        shootEncoder = indexShoot.getEncoder();
    }

    public static void PowerFeed(IndexPower value){
        IndexControl(indexFeed, value);
    }
    public static void PowerShooter(IndexPower value){
        IndexControl(indexShoot, value);
    }

    private static void IndexControl(SpeedController power, IndexPower value){
        if(value == IndexPower.SPIN){
            power.set(.4);
        }else if(value == IndexPower.STOP){
            power.set(0);
        }
    }

    public static void ShootAll(){
        //spin both motors
    }

    public static void ShootOne(){
        if(ballz == 4){
            if(uppperIndex.getRange() < 100){
                PowerFeed(IndexPower.SPIN);
                PowerShooter(IndexPower.SPIN);
                ballz -= 1;
            }
            else{
                PowerFeed(IndexPower.STOP);
                PowerShooter(IndexPower.STOP);
            }
        }
    }

    public static void Index(){
        if(ballz < 3){
            if(uppperIndex.getRange() < 100){
                if(uppperIndex.getRange() < 100 || lowerIndex.getRange() < 100){
                    PowerFeed(IndexPower.SPIN);
                }else{
                    PowerFeed(IndexPower.STOP);
                    ballz += 1;
                }
            }
        }
        else if(ballz == 3){
            if(uppperIndex.getRange() < 100){
                if(uppperIndex.getRange() < 100 && lowerIndex.getRange() > 100){
                    PowerFeed(IndexPower.SPIN);
                }else{
                    PowerFeed(IndexPower.STOP);
                    ballz += 1;
                }
            }
        }
    }
}