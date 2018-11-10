/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author Lida
 */
public class file_RD {
    
    
    public static String[] readtoList() throws IOException {
        String filePath="/Users/user/Desktop/Network_programming/Homework/Homework1/words.txt";
        File file=new File(filePath);        
        FileReader reader=new FileReader(file);
        int fileLen = (int)file.length();
	char[] chars = new char[fileLen];
	reader.read(chars);
	String txt = String.valueOf(chars);
	String[] str = txt.split("\n");
        return str;
    }   
    
}
