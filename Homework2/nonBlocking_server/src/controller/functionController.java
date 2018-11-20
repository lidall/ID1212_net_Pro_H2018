/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.nio.channels.SelectionKey;
import java.sql.SQLException;
import java.util.Arrays;
import model.wrd_Gen;
import static model.wrd_Gen.blankWord;
import static model.wrd_Gen.word;
import static model.wrd_Gen.wordLen;
import model.UserInfo;
import model.wrd_Compare;
import static model.wrd_Compare.existFlag;
import static model.wrd_Compare.lifetime;
import static model.wrd_Compare.outputGuess;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Lida
 */
public class functionController {
    
    
    public void clientInitial(String ID) throws Exception{
        //wrd_Gen wordSet=new wrd_Gen();
        //wordSet.buildWord();
        String Word="0";
        char[] Blank=null;
        int leftChance=0;
        int Score=0;
        int intialFlag=1;
        clientInDB(ID,Word,Arrays.toString(Blank),leftChance,Score,intialFlag);
        
    }

    public void clientProc(String ID, String answer,SelectionKey key) throws SQLException, Exception {
       ForkJoinPool queryPool = new ForkJoinPool();
       UserInfo user = queryPool.invoke(new threadInvoker(ID));
       queryPool.awaitTermination(50, TimeUnit.MILLISECONDS);
       queryPool.shutdown();
       int initialFlag=user.getFlag();
       if(initialFlag==1){
           if(answer.equals("YES")){
               wrd_Gen wordSet=new wrd_Gen();
                wordSet.buildWord();
                String Word=word;
                char[] Blank=blankWord;
                int leftChance=wordLen;
                initialFlag=0;
                //System.out.println(Word);
                //System.out.println(Blank);
               user.setFlag(initialFlag);
               user.setChanceLeft(leftChance);
               user.setGuessStatus(String.valueOf(Blank));
               user.setWordtoGuess(Word);
               //System.out.println(user);
               String User=ID+" is guessing "+Word+"   The total score is: "+String.valueOf(user.getScore());
               view.viewCLI.printCLI(User);
               ForkJoinPool forkJoinPool = new ForkJoinPool();
               forkJoinPool.execute(new threadPool(user, "GuessUpdate"));
               forkJoinPool.awaitTermination(25, TimeUnit.MILLISECONDS);
               forkJoinPool.shutdown();

           }
           else if(answer.equals("NO")||answer.equals("Quit")){
               ForkJoinPool deletePool = new ForkJoinPool();
               deletePool.execute(new threadPool(user, "dataDelete"));
               deletePool.awaitTermination(50, TimeUnit.MILLISECONDS);
               deletePool.shutdown();
               net.clientHandler.quitHandler(key);
           }
       }
       else{
            int chance=user.getChanceLeft();
            String wordtoguess=user.getWordtoGuess();
            String blank=user.getGuessStatus();
            wrd_Compare compare=new wrd_Compare();
            compare.compareWord(answer, chance, wordtoguess, blank);
            chance=lifetime;
            String guessresult= outputGuess;
            int Flag=existFlag;
            //System.out.println(Flag);
            //System.out.println(guessresult);
            //System.out.println(chance);
            user.setChanceLeft(chance);
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            forkJoinPool.execute(new threadPool(user, "chanceUpdate"));
            forkJoinPool.awaitTermination(25, TimeUnit.MILLISECONDS);
            forkJoinPool.shutdown();
           if(Flag==1){
           user.setGuessStatus(guessresult);
            ForkJoinPool resultPool = new ForkJoinPool();
            resultPool.execute(new threadPool(user, "GuessUpdate"));
            resultPool.awaitTermination(25, TimeUnit.MILLISECONDS);
           
            resultPool.shutdown();
           
           if(user.getWordtoGuess().equals(guessresult)){
               int Score=user.getScore()+1;
               initialFlag=1;
               user.setScore(Score);
               user.setFlag(initialFlag);
               ForkJoinPool updatePool = new ForkJoinPool();
               updatePool.execute(new threadPool(user, "scoreUpdate,flagUpdate"));
               updatePool.awaitTermination(40, TimeUnit.MILLISECONDS);
               updatePool.shutdown();
           }
           }
           else if(Flag==0&&chance==0){
              int Score=user.getScore()-1;
               initialFlag=1;
               user.setScore(Score);
               user.setFlag(initialFlag);
               ForkJoinPool updatePool = new ForkJoinPool();
               updatePool.execute(new threadPool(user, "scoreUpdate,flagUpdate"));
               updatePool.awaitTermination(25, TimeUnit.MILLISECONDS);
               updatePool.shutdown();
           }
       }
       
    
    
    }

    public String clientOut(String ID) throws Exception {
        ForkJoinPool queryPool = new ForkJoinPool();
        UserInfo user = queryPool.invoke(new threadInvoker(ID));
        queryPool.awaitTermination(70, TimeUnit.MILLISECONDS);
        queryPool.shutdown();
        String wordtoGuess=user.getWordtoGuess();
        String guessstatus=user.getGuessStatus();
        int score=user.getScore();
        int leftChance=user.getChanceLeft();
        int initialFlag=user.getFlag();
        String feedbackStr=new String();
            feedbackStr=feedJudge(wordtoGuess,guessstatus,score,leftChance,initialFlag);     
        return feedbackStr;
       }
    
    private void clientInDB(String ID, String Word, String Blank, int leftChance,int Score,int intialFlag) throws Exception{
        UserInfo user=new UserInfo(ID,Word,Blank,leftChance,Score,intialFlag);
        int i=0;
        //while(i!=1){
         
        ForkJoinPool insertPool = new ForkJoinPool();
        insertPool.execute(new threadPool(user, "dataInsert"));
        insertPool.awaitTermination(50, TimeUnit.MILLISECONDS);
        insertPool.shutdown();
        //}
    }
    
    private String feedJudge(String wordtoGuess,String guessstatus, int score,int leftChance,int initialFlag){
        String feedback=new String();
        int successFlag=0;
        if(!(wordtoGuess.equals("0"))&&wordtoGuess.equals(guessstatus)&&initialFlag==1){
            successFlag=1;
            feedback=wordtoGuess+":"+String.valueOf(score)+"$"+String.valueOf(successFlag);
        }
        else if(!(wordtoGuess.equals("0"))&&!(wordtoGuess.equals(guessstatus))&&initialFlag==1){
            successFlag=0;
            feedback=wordtoGuess+":"+String.valueOf(score)+"$"+String.valueOf(successFlag);
        }
        else if((wordtoGuess.equals("0"))&&initialFlag==1){
            
            feedback=String.valueOf(score);
        }
        else if(initialFlag==0){
            feedback=String.valueOf(leftChance)+":"+guessstatus+":"+String.valueOf(score);
        } 
        return feedback;
    }
    
}
