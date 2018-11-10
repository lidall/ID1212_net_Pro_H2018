/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Lida
 */
public class wrd_Compare {
    public static int lifetime;
    public static String outputGuess;
    public static int existFlag;

    public void compareWord(String Guess, int leftChance,String wordtoGuess, String guessInput){ 
        try{
            int guessLenth=Guess.length();
            int wordLenth=wordtoGuess.length();
            existFlag=0;
            char [] guessinput=guessInput.toCharArray();
            char [] wordtoguess=wordtoGuess.toCharArray();
            char [] guess=Guess.toCharArray();
            if (guessLenth==1){
                for(int i=0;i<wordLenth;i++){
                    if(wordtoguess[i]==guess[0]){
                        existFlag=1;
                        guessinput[i]=guess[0]; 
                    }                    
                }               
            }
            else if(guessLenth==wordLenth){
                if(Guess.equals(wordtoGuess)){
                    existFlag=1;
                    leftChance=0;
                    guessinput=wordtoguess;
                }                              
            }
            if(existFlag==1){
               outputGuess=String.valueOf(guessinput);
               lifetime=leftChance;
            }
            else{
                outputGuess=guessInput;
                lifetime=leftChance-1;                   
                       }
            //System.out.println(outputGuess);
            
        
    }catch (Exception e){        
    }
    
}
    
}
