package frc.robot.util;

import java.util.Timer;
import java.util.TimerTask;
import frc.robot.util.Constants;

public class WallTimer{

Timer shoot = new Timer();
TimerTask shootTask = new Helper();


}

class Helper extends TimerTask{

    public void run() 
    { 
        Constants.shootFlag = true;
    } 
}