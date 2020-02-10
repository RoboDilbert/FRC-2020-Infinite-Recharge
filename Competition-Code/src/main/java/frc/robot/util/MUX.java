package frc.robot.util;

//import edu.wpi.first.wpilibj.I2C;

public class MUX{
    
    public enum slaveRegister{
        OPEN1(0x70),
        OPEN2(0x71),
        OPEN3(0x72),
        OPEN4(0x73),
        OPEN5(0x74),
        OPEN6(0x75);

        public final byte bVal;
        slaveRegister(int i) { this.bVal = (byte) i;
    }
    }

    public enum addressWrite{
        Test(0x10);

        public final byte bVal;
        addressWrite(int i) { this.bVal = (byte) i;}
    }

    public enum addressRead{
        Test(0x11);

        public final byte bVal;
        addressRead(int i) { this.bVal = (byte) i;}
    }

}
