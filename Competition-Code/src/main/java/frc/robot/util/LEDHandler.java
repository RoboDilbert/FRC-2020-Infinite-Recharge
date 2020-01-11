package frc.robot.util;

import edu.wpi.first.wpilibj.I2C;

public class LEDHandler{

    private int Arduino_Address = 0;
    private static I2C Response;

    private enum LEDStatus{
        //Clear leds
        PATTERN_OFF(0),
        //Toggle LED patterns
        PATTERN_SEND(1), 
        //Neutral status
        ALT_BLUE_YELLOW(3), 
        //Full color wipe blue
        BLUE(4),
        //Full color wipe green
        GREEN(5),
        //Full color wipe yellow
        YELLOW(6),  
        //Full color wipe red
        RED(7),
        //Endgame
        SCROLLING_RAINBOW(8), 
        //Moving forward
        FWD_SCROLL_BLUE_YELLOW(9), 
        //Moving backward
        RVS_SCROLL_BLUE_YELLOW(10); 

        //this holds arduino state
        private int LEDStatus_Value;
        private LEDStatus(int value){
         this.LEDStatus_Value = value;
        }
        public int getLEDStatus(){
          return this.LEDStatus_Value;
        }
    }

    public void ALT_BLUE_YELLOW(){
        Response.write(Arduino_Address, LEDStatus.ALT_BLUE_YELLOW.getLEDStatus());
    }
    public void BLUE(){
        Response.write(Arduino_Address, LEDStatus.BLUE.getLEDStatus());
    }
    public void GREEN(){
        Response.write(Arduino_Address, LEDStatus.GREEN.getLEDStatus());
    }
    public void YELLOW(){
        Response.write(Arduino_Address, LEDStatus.YELLOW.getLEDStatus());
    }
    public void RED(){
        Response.write(Arduino_Address, LEDStatus.RED.getLEDStatus());
    }
    public void SCROLLING_RAINBOW(){
        Response.write(Arduino_Address, LEDStatus.SCROLLING_RAINBOW.getLEDStatus());
    }
    public void FWD_SCROLL_BLUE_YELLOW(){
        Response.write(Arduino_Address, LEDStatus.FWD_SCROLL_BLUE_YELLOW.getLEDStatus());
    }
    public void RVS_SCROLL_BLUE_YELLOW(){
        Response.write(Arduino_Address, LEDStatus.RVS_SCROLL_BLUE_YELLOW.getLEDStatus());
    }
}