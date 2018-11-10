/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Lida
 */
public class functionControl {
    public static String wrapPckt(String outStr){
        String outPacket=model.formStr.wrapPacket(outStr);
        return outPacket;
    }
    public static String checkPckt(String inpacket,Socket socket) throws IOException{
        String inStr=model.formStr.checkPacket(inpacket, socket);
        return inStr;
    }
    public static String formString(String Str){
        String formedStr=model.formStr.formStr(Str);
        return formedStr;
    }
}
