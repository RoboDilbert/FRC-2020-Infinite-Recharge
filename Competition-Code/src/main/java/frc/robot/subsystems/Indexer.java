package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.util.Constants;
//import frc.robot.util.sensors.*;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

//import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Indexer{

    private static CANSparkMax IndexFeedMotor;
    private static CANSparkMax IndexShootMotor;
    private static CANEncoder IndexFeedMotorEncoder;
    private static CANEncoder IndexShootMotorEncoder;

    private static TimeOfFlight uppperIndex = new TimeOfFlight(Constants.UPPER_INDEXER_TOF_ID);
    private static TimeOfFlight lowerIndex = new TimeOfFlight(Constants.LOWER_INDEXER_TOF_ID);
   


    public static int currentBallCount;
    private static boolean ballCountFlag = false;
    

    public static enum SelectIndexer{
        FEEDER,
        SHOOT;
    }

    public static enum IndexerState{
        FORWARD,
        REVERSE,
        STOP;
    }

    public static void init(){
        IndexFeedMotor = new CANSparkMax(Constants.IndexFeedMotorID, MotorType.kBrushless);
        IndexShootMotor = new CANSparkMax(Constants.IndexShootMotorID, MotorType.kBrushless);
        IndexFeedMotor.setIdleMode(IdleMode.kCoast);
        IndexShootMotor.setIdleMode(IdleMode.kCoast);
        IndexFeedMotorEncoder = IndexFeedMotor.getEncoder();
        IndexShootMotorEncoder = IndexShootMotor.getEncoder();
        currentBallCount = 0;
        uppperIndex.setRangingMode(RangingMode.Short, 25);
        lowerIndex.setRangingMode(RangingMode.Short, 25);
    }

    public static void controlIndexer(SelectIndexer selectValue, IndexerState stateValue){
        if(selectValue == SelectIndexer.FEEDER){
            powerIndexer(IndexFeedMotor, stateValue, -Constants.feedIndexerSpeed);
        }
        else if(selectValue == SelectIndexer.SHOOT){
            powerIndexer(IndexShootMotor, stateValue, -Constants.shootIndexerSpeed);
        }
    }

    private static void powerIndexer(SpeedController power, IndexerState value, double speed){
        if(value == IndexerState.FORWARD){
            power.set(speed);
        }else if(value == IndexerState.STOP){
            power.set(0);
        }
    }

    public static void ShootAll(){
        double time = Timer.getFPGATimestamp();

        if(time < time + 10){
            controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
            controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
        } else if(time > time + 10){
            controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
            controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
        }
        currentBallCount = 0;
    }

    public static void ShootOne(){
        if(currentBallCount == 4){
            if(uppperIndex.getRange() < 100){
                controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                controlIndexer(SelectIndexer.SHOOT, IndexerState.FORWARD);
                currentBallCount -= 1;
            }
            else{
                controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                controlIndexer(SelectIndexer.SHOOT, IndexerState.STOP);
            }
        }
    }

    public static boolean Index(){
        boolean tempFeedBool = false;
        if(currentBallCount < 3){
            if(uppperIndex.getRange() < 100 || lowerIndex.getRange() < 100){
                controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                ballCountFlag = true;
                tempFeedBool = true;
            }else{
                controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                if(ballCountFlag == true){
                    currentBallCount ++;
                }
                ballCountFlag = false;
            }
        }
        else if(currentBallCount == 3){
            if(uppperIndex.getRange() < 100 && lowerIndex.getRange() > 100){
                controlIndexer(SelectIndexer.FEEDER, IndexerState.FORWARD);
                ballCountFlag = true;
            }else{
                controlIndexer(SelectIndexer.FEEDER, IndexerState.STOP);
                if(ballCountFlag == true){
                    currentBallCount++;
                }
                ballCountFlag = false;
                //tempFeedBool = true;
            }
        }
        // if(ballCountFlag = true){
        //     currentBallCount++;
        // }
        return tempFeedBool;
    }

    public static void indexerClear(){
        currentBallCount = 0;
    }

    public static void debugIndexer(){
        SmartDashboard.putNumber("Upper TOF", uppperIndex.getRange());
        SmartDashboard.putNumber("Lower TOF", lowerIndex.getRange());
        //SmartDashboard.putNumber("Feeder Indexer Motor Encoder", IndexFeedMotorEncoder.getPosition());
        //SmartDashboard.putNumber("Shoot Indexer Motor Encoder", IndexShootMotorEncoder.getPosition());
        //SmartDashboard.putBoolean("ballFlag", ballCountFlag);
        SmartDashboard.putNumber("Ball Count", currentBallCount);
        
    }
}