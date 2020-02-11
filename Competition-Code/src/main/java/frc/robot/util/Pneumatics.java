package frc.robot.util;

import edu.wpi.first.wpilibj.Compressor;
//import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics{

    //Sets up a compressor for use
    private static Compressor compress;

    public static enum CompressorState{
        ENABLED, 
        DISABLED;
    }

    public static void init(){
        compress = new Compressor(Constants.UTILITIES_COMPRESSOR_PORT);
    }

    public static void controlCompressor(CompressorState value){
        powerCompressor(value);
    }

    private static void powerCompressor(CompressorState value){
        if(value == CompressorState.ENABLED){
            compress.setClosedLoopControl(true);
        } 
        else if(value == CompressorState.DISABLED){
            compress.setClosedLoopControl(false);
        }
    }
}