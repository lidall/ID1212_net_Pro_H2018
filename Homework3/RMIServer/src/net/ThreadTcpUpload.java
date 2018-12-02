/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static controller.ServiceImpl.getCTX;
import controller.SystemInitial;
import model.DatabaseFunction;
import model.UserInfo;
import Serverstartup.RMIServer;
import static Serverstartup.RMIServer.workingFLG;

/**
 *
 * @author Lida
 */
public class ThreadTcpUpload extends Thread implements Runnable{
    private String userFileName;
    public ThreadTcpUpload(String userFileName){
    this.userFileName = userFileName;
}
    
@Override
public void run() {
    try {
        ServerSocket sSocket=new ServerSocket(10006);
        Socket s= sSocket.accept();
        String folderPath = null;
        String personalFile = null;
        String publicAll = null;
        String publicRead = null;

        String userName = getCTX(userFileName,"@","#");
        String fileName = getCTX(userFileName,"#",")");
        String publicFLG = getCTX(userFileName,"<",":");
        String readFLG = getCTX(userFileName,":",">");


        if (publicFLG.equals("private")){
            folderPath = RMIServer.FILE_STORAGE_PATH + userName+"/";
        }
        else if (publicFLG.equals("public")&&readFLG.equals("all")){
            folderPath = RMIServer.FILE_PUBLIC_ALL;
        }
        else if(publicFLG.equals("public")&&readFLG.equals("read")){
            folderPath = RMIServer.FILE_PUBLIC_READ;
        }
        File file = new File(folderPath+fileName);
        
        InputStream in = s.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffile = new byte[2048];

        int len = 0;

        while ((len = in.read(buffile)) != -1)
        {
            fos.write(buffile, 0, len);
        }
        String fileSize = RMIServer.getFileSize(folderPath+fileName);
        Connection conn = SystemInitial.getConn();
        String sql = "select * from UserInfo";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);

            if (publicFLG.equals("private")){
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {

                    if(rs.getString(1).equals(userName))
                    {
                        personalFile = rs.getString(3);
                    }

                }
                personalFile = personalFile + fileName+ fileSize + " ";
                DatabaseFunction.dataUpdate(new UserInfo(userName,"",personalFile));
            }
            else if (publicFLG.equals("public")&&readFLG.equals("all")){                    
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {
                    
                    if(rs.getString(1).equals("publicallpermission"))
                    {
                        publicAll = rs.getString(3);
                    }

                }
                publicAll = publicAll + "\""+userName+"\":"+ fileName+ fileSize + " ";
                DatabaseFunction.dataUpdate(new UserInfo("publicallpermission","",publicAll));
                
                conn = SystemInitial.getConn();
                Statement stmt = conn.createStatement();
                String pureFileName = getCTX("(" + fileName+")", "(", ".");
                String sqlstr = "CREATE TABLE "+ pureFileName +" (userNameAccess varchar(255));";
                stmt.executeUpdate(sqlstr);
                
                
            }
            else if (publicFLG.equals("public")&&readFLG.equals("read")){
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {

                    if(rs.getString(1).equals("publicreadonly"))
                    {
                        publicRead = rs.getString(3);
                    }

                }
                publicRead = publicRead +"\""+userName+"\":"+ fileName + fileSize + " ";
                DatabaseFunction.dataUpdate(new UserInfo("publicreadonly","",publicRead));
                
                conn = SystemInitial.getConn();
                Statement stmt = conn.createStatement();
                String pureFileName = getCTX("(" + fileName+")", "(", ".");
                String sqlstr = "CREATE TABLE "+ pureFileName +" (userNameAccess varchar(255));";
                stmt.executeUpdate(sqlstr);
               
                
            }
            workingFLG = false;
            System.out.println("Upload success! "+userName+": "+fileName +fileSize);
            s.close();
            sSocket.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (IOException ex) {
    Logger.getLogger(ThreadTcpUpload.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
}
