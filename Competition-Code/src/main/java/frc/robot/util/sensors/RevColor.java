package frc.robot.util.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
//import frc.robot.util.*;

import com.revrobotics.ColorSensorV3;
public class RevColor{
    
private final I2C.Port i2cPort = I2C.Port.kOnboard;


private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
Color detectedColor = m_colorSensor.getColor();
double IR = m_colorSensor.getIR();
int proximity = m_colorSensor.getProximity();
double value;
Color B;
 Color R;
Color G;


public void displayColor(){
    double alpha = ((double)m_colorSensor.getRawColor().blue + (double)m_colorSensor.getRawColor().red + (double)m_colorSensor.getRawColor().green);

    SmartDashboard.putNumber("blue", (double)m_colorSensor.getRawColor().blue/ alpha);
    SmartDashboard.putNumber("red", (double)m_colorSensor.getRawColor().red / alpha);
    SmartDashboard.putNumber("green", (double)m_colorSensor.getRawColor().green / alpha);
    SmartDashboard.putNumber("alpha", alpha);
   
    //SmartDashboard.putNumber("IR", IR);
   // SmartDashboard.putNumber("Proximity", proximity);
   SmartDashboard.updateValues();
}

public double returnValue(String command) {
if(command == "blue"){
value = detectedColor.blue;
}
else if(command == "red"){
value = detectedColor.red;
}
else if(command == "green"){
value = detectedColor.green;
}
else if(command == "IR"){
value = IR;
}
else if(command == "proximity"){
 value = (double) proximity;
}
 return value;
}

public String searchColor(){
    double alpha = ((double)m_colorSensor.getRawColor().blue + (double)m_colorSensor.getRawColor().red + (double)m_colorSensor.getRawColor().green);

 
    if((double)m_colorSensor.getRawColor().blue/ alpha > .3 && (double)m_colorSensor.getRawColor().green/ alpha > .3){
        return "B";
    }
    else if((double)m_colorSensor.getRawColor().red/ alpha > .4){
        return "R";
    }
    else if((double)m_colorSensor.getRawColor().green/ alpha > .5 && (double)m_colorSensor.getRawColor().blue/ alpha < .3 && (double)m_colorSensor.getRawColor().red/ alpha < .2){
        return "G";
    }
    else if((double)m_colorSensor.getRawColor().red/ alpha > .3 && (double)m_colorSensor.getRawColor().green/ alpha > .4  && (double)m_colorSensor.getRawColor().blue/ alpha < .2){
        return "Y";
    }
    else{
        return "N";
    }
    
}


}