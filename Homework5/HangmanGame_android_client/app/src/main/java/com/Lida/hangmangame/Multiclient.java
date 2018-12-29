package com.Lida.hangmangame;


public class Multiclient {

    public static String getCTX(String originalCTX, String firstSplit, String secondSplit)
    {
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit),
                originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
    
}
