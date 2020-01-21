package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.util.Constants;


public class ControlPanel{
    
    public void init(){
        
    }

    
    public static void getColor(){
        Constants.gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(Constants.gameData.length() > 0){
            switch (Constants.gameData.charAt(0)){
                case 'B' :
                    //Blue case code
                    break;
                case 'G' :
                    //Green case code
                    break;
                case 'R' :
                    //Red case code
                    break;
                case 'Y' :
                    //Yellow case code
                    break;
                default :
                    //This is corrupt data
                    break;
                }
        }else{
            //Code for no data received yet
        }
    }
}