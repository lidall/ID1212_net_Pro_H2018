/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import static hangman_server.serverInitial.wordList;
/**
 *
 * @author Lida
 */
public class wrd_Gen {
    public static int wordLen;
    public static String word;
    public static char[] blankWord;
    
    
    public static void pickWord() throws IOException{
        int listLen;
        listLen = wordList.length;
        int randomNum;
        randomNum=(int)(Math.random()*listLen);
       
        word=wordList[randomNum];    //System.out.println(word);                                             
    }
    public void buildWord() throws IOException{
        
        wrd_Gen.pickWord();
        wordLen=word.length();
        blankWord=new char[wordLen];
        for(int i=0;i<wordLen;i++){
            blankWord[i]='*';                    
        }     
        
    }

    
}
