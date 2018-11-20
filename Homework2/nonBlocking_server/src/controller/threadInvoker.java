/*
 * Author      : Hongyi Zhang
 * Version     : 1.0
 * Copyright   : All rights reserved. Do not distribute. 
 * You are welcomed to modify the code.
 * But any commercial use you need to contact me
 */

package controller;
import java.util.concurrent.RecursiveTask;
import model.UserInfo;
/**
 *
 * @author Hongyi Zhang
 */
public class threadInvoker extends RecursiveTask<UserInfo>{
    
    private String ID;
    private UserInfo user;
    
    public threadInvoker(String ID){
        this.ID = ID;
    }
    
    @Override
    protected UserInfo compute() {
        
        try{
            
        user=model.databaseFunction.clientQuery(ID);
        
        }catch(Exception e){
            
        }
           return user;
        }
    }
    

