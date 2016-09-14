package com.jw.sample.bluetooth.util;

public class IBeaconUtil {
    public static boolean isIBeacon(byte[] scanData){
        int startByte = 2;
        boolean patternFound = false;
        while (startByte <= 5) {
            if (((int)scanData[startByte+2] & 0xff) == 0x02 &&
                    ((int)scanData[startByte+3] & 0xff) == 0x15) {
                patternFound = true;
                break;
            }
            startByte++;
        }

        return patternFound;
    }
}
