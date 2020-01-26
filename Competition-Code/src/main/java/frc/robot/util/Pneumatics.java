package frc.robot.util;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics{

    private  boolean compressorState = true;
    //Sets up a compressor for use
    private Compressor compress = new Compressor(Constants.UTILITIES_COMPRESSOR_PORT);
    
    public void setCompressorState(boolean state){
        compress.setClosedLoopControl(state);
        compressorState = state;
    }

    public final void turnOnCompressor(){
        setCompressorState(Constants.COMPRESSOR_ON);
    }

    public final void turnOffCompressor(){
        setCompressorState(Constants.COMPRESSOR_OFF);

    }

    public boolean getCompressorState(){
        return compressorState;
    }
}