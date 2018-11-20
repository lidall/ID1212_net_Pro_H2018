/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.concurrent.RecursiveAction;
import model.UserInfo;
/**
 *
 * @author Lida
 */
public class threadPool extends RecursiveAction{
    
    private UserInfo userIn;
    private String commandIn;
    
    public threadPool(UserInfo userIn, String commandIn) {
        this.userIn = userIn;
        this.commandIn = commandIn;
    }

    @Override
    public void compute() {
  
        
        if (!commandIn.contains(",")){
            chooseFunction(userIn, commandIn);
        }else
        {
            String[] commandList = commandIn.split(",");
            switch (commandList.length){
                case 2:{
                    threadPool first = new threadPool(userIn, commandList[0]);
                    threadPool second = new threadPool(userIn, commandList[1]);
                    first.fork();
                    second.fork();
                    break;
                }
                case 3:{
                    threadPool first = new threadPool(userIn, commandList[0]);
                    threadPool second = new threadPool(userIn, commandList[1]);
                    threadPool third = new threadPool(userIn, commandList[2]);
                    first.fork();
                    second.fork();
                    third.fork();
                    break;
                }
                case 4:{
                    threadPool first = new threadPool(userIn, commandList[0]);
                    threadPool second = new threadPool(userIn, commandList[1]);
                    threadPool third = new threadPool(userIn, commandList[2]);
                    threadPool fourth = new threadPool(userIn, commandList[3]);
                    first.fork();
                    second.fork();
                    third.fork();
                    fourth.fork();
                    break;
                    
                }
                case 5:{
                    threadPool first = new threadPool(userIn, commandList[0]);
                    threadPool second = new threadPool(userIn, commandList[1]);
                    threadPool third = new threadPool(userIn, commandList[2]);
                    threadPool fourth = new threadPool(userIn, commandList[3]);
                    threadPool fifth = new threadPool(userIn, commandList[4]);
                    first.fork();
                    second.fork();
                    third.fork();
                    fourth.fork();
                    fifth.fork();
                    break;
                    
                }
                case 6:{
                    threadPool first = new threadPool(userIn, commandList[0]);
                    threadPool second = new threadPool(userIn, commandList[1]);
                    threadPool third = new threadPool(userIn, commandList[2]);
                    threadPool fourth = new threadPool(userIn, commandList[3]);
                    threadPool fifth = new threadPool(userIn, commandList[4]);
                    threadPool sixth = new threadPool(userIn, commandList[5]);
                    first.fork();
                    second.fork();
                    third.fork();
                    fourth.fork();
                    fifth.fork();
                    sixth.fork();
                    break;
                    
                }
                    
                    
            }
            
                   
        }
        
        
    }
    
    public void chooseFunction(UserInfo user, String Command){
        
        if (Command.equals("GuessUpdate")){
            model.databaseFunction.GuessUpdate(user);
        }
        else if(Command.equals("chanceUpdate")){
            model.databaseFunction.chanceUpdate(user);
        }
        else if(Command.equals("scoreUpdate")){
            model.databaseFunction.scoreUpdate(user);
        }
        else if(Command.equals("flagUpdate")){
            model.databaseFunction.flagUpdate(user);
        }
        else if(Command.equals("dataDelete")){
            model.databaseFunction.dataDelete(user);
        }
        else if(Command.equals("dataInsert")){
            model.databaseFunction.dataInsert(user);
        }
        
        
    }
    
}
