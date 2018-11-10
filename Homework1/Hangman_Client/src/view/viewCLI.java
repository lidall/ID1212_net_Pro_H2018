/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;

/**
 *
 * @author Lida
 */
public class viewCLI {
    public static void printCLI(String item){
       System.out.println(item);
    }
    public static String tapIn(){
        String item;
        Scanner scan=new Scanner(System.in);
        item=scan.next();        
        return item;
    }
}
