/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import startup.RMIClient;

/**
 *
 * @author Lida
 */
public class UploadTCP {
    public static void userTCPUpload(String userFilePath) throws Exception{
    Socket s = new Socket(RMIClient.SERVER_IP,10006);
    File file = new File(userFilePath);
    FileInputStream fis = new FileInputStream(file);
    OutputStream out = s.getOutputStream();
    byte[] buf = new byte[2048];
        int len = 0;

        while ((len = fis.read(buf)) != -1)
                {
            out.write(buf, 0, len);
        }
        s.shutdownOutput();
    s.close();
    fis.close();
    
    } 
}
