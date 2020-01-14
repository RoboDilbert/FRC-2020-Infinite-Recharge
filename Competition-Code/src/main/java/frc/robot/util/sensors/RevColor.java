package frc.robot.util.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.util.*;

import com.revrobotics.ColorSensorV3;
public class RevColor{
    
private final I2C.Port i2cPort = I2C.Port.kOnboard;


private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
Color detectedColor = m_colorSensor.getColor();
double IR = m_colorSensor.getIR();
int proximity = m_colorSensor.getProximity();
double value;

public void displayColor(){
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Proximity", proximity);
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


}