package frc.robot.util;

import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics{

    private static boolean compressorState = true;
    //Sets up a compressor for use
    private static Compressor compress = new Compressor(Constants.UTILITIES_COMPRESSOR_PORT);

    
    public static void setCompressorState(boolean state){
        compress.setClosedLoopControl(state);
        compressorState = state;
    }

    public final void turnOnCompressor(){
        setCompressorState(Constants.COMPRESSOR_ON);
    }

    public final void turnOffCompressor(){
        setCompressorState(Constants.COMPRESSOR_OFF);

    }

    public static boolean getCompressorState(){
        return compressorState;
    }
}