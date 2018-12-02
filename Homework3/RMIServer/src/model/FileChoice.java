
package model;

/**
 *
 * @author Lida
 */
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static controller.ServiceImpl.getCTX;
import controller.SystemInitial;
import Serverstartup.RMIServer;
import net.ThreadTcpDownload;
import net.ThreadTcpUpdate;
import net.ThreadTcpUpload;


public class FileChoice {
    public static String deleteInitial(String userName){
        String result = null;
        String personalFile = null;
        String publicAll = null;
        System.out.println("Begin delete for: "+userName);

        Connection conn = SystemInitial.getConn();
        String sql = "select * from UserInfo";
        PreparedStatement pstmt;
        try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

         while (rs.next())
            {

                if(rs.getString(1).equals(userName))
                {
                    personalFile = "<Your personal files: [" + rs.getString(3)+"]>";
                }

            }
        rs = pstmt.executeQuery();
        while (rs.next()){
            if(rs.getString(1).equals("publicallpermission"))
            {
                publicAll ="{Public files(All Permission): [" + rs.getString(3)+"]}";
            }
        }
        conn.close();
        result = personalFile + publicAll;
        } catch (SQLException e) {
        e.printStackTrace();
    }
        return result;
    }
    
public static String fileDelete(String userfileName){
        
    String folderPath = null;
    String personalFile = null;
    String publicAll = null;

    String result =null;
    String userName = getCTX(userfileName,"@","#");
    String fileName = getCTX(userfileName,"#",")");
    String publicFLG = getCTX(userfileName,"<",":");
    String readFLG = getCTX(userfileName,":",">");
                
                
    if (publicFLG.equals("private")){
        folderPath = RMIServer.FILE_STORAGE_PATH + userName+"/";
    }
    else if (publicFLG.equals("public")&&readFLG.equals("all")){
        folderPath = RMIServer.FILE_PUBLIC_ALL;
    }
    String fileSize = RMIServer.getFileSize(folderPath+fileName);
    new File(folderPath+fileName).delete();
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
            personalFile = personalFile.replace(fileName+fileSize+" ","");
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
            String oldUserName = RMIServer.getOldFileName(fileName, publicAll);
            publicAll = publicAll.replace("\""+oldUserName+"\":"+fileName+fileSize+" ","");
            DatabaseFunction.dataUpdate(new UserInfo("publicallpermission","",publicAll));
        }
        conn.close();
        result = "&success*"+ fileName + fileSize+"!";
        } catch (SQLException e) {
        e.printStackTrace();
    }
        return result;
    }

public static String downloadInitial(String userName){
    String result = null;
    String personalFile = null;
    String publicAll = null;
    String publicRead = null;
    System.out.println("Begin download for: "+userName);

    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
    pstmt = (PreparedStatement)conn.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next())
        {

            if(rs.getString(1).equals(userName))
            {
                personalFile = "<Your personal files: [" + rs.getString(3)+"]>";
            }

        }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicallpermission"))
        {
            publicAll ="{Public files(All Permission): [" + rs.getString(3)+"]}";
        }
    }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicreadonly"))
        {
            publicRead ="!Public files(Read Only): [" + rs.getString(3)+"]?";
        }
    }
    conn.close();
    result = personalFile + publicAll + publicRead;
    } catch (SQLException e) {
    e.printStackTrace();
}
    return result;
}
    
public static void tcpDownload(String userFileName) throws Exception{
    new ThreadTcpDownload(userFileName).start();
}
     
public static String getFileList(String userName){
    String result = null;
    String personalFile = null;
    String publicAll = null;
    String publicRead = null;
    System.out.println("Begin upload for: "+userName);

    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
    pstmt = (PreparedStatement)conn.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next())
        {

            if(rs.getString(1).equals(userName))
            {
                personalFile = "Your personal files: [" + rs.getString(3)+"]\n";
            }

        }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicallpermission"))
        {
            publicAll ="Public files(All Permission): [" + rs.getString(3)+"]\n";
        }
    }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicreadonly"))
        {
            publicRead ="Public files(Read Only): [" + rs.getString(3)+"]";
        }
    }
    conn.close();
    result = personalFile + publicAll + publicRead;
    } catch (SQLException e) {
    e.printStackTrace();
}
    return result;
}

  public static String updateInitial(String userName){
    String result = null;
    String personalFile = null;
    String publicAll = null;
    System.out.println("Begin update for: "+userName);

    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
    pstmt = (PreparedStatement)conn.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next())
        {

            if(rs.getString(1).equals(userName))
            {
                personalFile = "<Your personal files: [" + rs.getString(3)+"]>";
            }

        }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicallpermission"))
        {
            publicAll ="{Public files(All Permission): [" + rs.getString(3)+"]}";
        }
    }
    conn.close();
    result = personalFile + publicAll;
} catch (SQLException e) {
    e.printStackTrace();
}
    return result;
}
    
public static void tcpUpdate(String userFileName) throws Exception{
    new ThreadTcpUpdate(userFileName).start();
}
     
public static String uploadInitial(String userName){
    String result = null;
    String personalFile = null;
    String publicAll = null;
    String publicRead = null;
    System.out.println("Begin upload for: "+userName);

    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
    pstmt = (PreparedStatement)conn.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next())
        {

            if(rs.getString(1).equals(userName))
            {
                personalFile = "Your personal files: [" + rs.getString(3)+"]\n";
            }

        }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicallpermission"))
        {
            publicAll ="Public files(All Permission): [" + rs.getString(3)+"]\n";
        }
    }
    rs = pstmt.executeQuery();
    while (rs.next()){
        if(rs.getString(1).equals("publicreadonly"))
        {
            publicRead ="Public files(Read Only): [" + rs.getString(3)+"]";
        }
    }
    result = personalFile + publicAll + publicRead;
    conn.close();
    } catch (SQLException e) {
    e.printStackTrace();
}
    return result;
}
    
public static void tcpUpload(String userFileName) throws Exception{
        new ThreadTcpUpload(userFileName).start();
}

}
