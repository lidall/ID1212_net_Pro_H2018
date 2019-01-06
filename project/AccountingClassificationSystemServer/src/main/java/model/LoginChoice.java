
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static startup.SystemServer.getCTX;
import controller.SystemInitial;

public class LoginChoice {
    
    public static String userLogin(String userInput){
        String returnWord = null;
        String userName = getCTX(userInput,"@","#");
        System.out.println("Begin login operation for: "+userName);
        String userPass = getCTX(userInput,"#",")");
        Connection conn = SystemInitial.getConn();
        String sql = "select * from UserInfo";
        PreparedStatement pstmt;
        try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
        {
            if(rs.getString(1).equals(userName)&&rs.getString(2).equals(userPass))
            {
                returnWord = "Sucess! Welcome user: " + userName + "^";
                System.out.println(userName+": login sucess");
                DatabaseFunction.dataBaseGetAll();
                break;
            }
            returnWord = "Wrong user Name or Password";
        }
        } catch (SQLException e) {
             e.printStackTrace();
        }
            return returnWord;
    }
    
    public static String userRegister(String userInput){
        
        String returnWord = null;
        boolean createFLG = true;
            String userName = getCTX(userInput,"@","#");
            System.out.println("Begin operation 2 for: "+userName);
            String userPass = getCTX(userInput,"#",")");
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
                        returnWord = "Wrong! User exists!" ;
                        createFLG = false;
                        break;
                    }
                    
                }
            if (createFLG)
            {
                DatabaseFunction.dataInsert(new UserInfo(userName, userPass));
                returnWord = "Create user Sucessfully! Welcome user: " + userName + "^";
                
                DatabaseFunction.dataBaseGetAll();
            }
            } catch (SQLException e) {
        e.printStackTrace();
    }
        return returnWord;
        
    }
    
    
    
}
