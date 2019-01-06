/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Lida
 */
public class DataInfo implements Serializable {
    
        private static final long serialVersionUID = 1L;
        
        private String itemName;  //Transfer Data Name
        
        private String itemID;    //Transfer Data ID

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemID() {
            return itemID;
        }

        public void setItemID(String itemID) {
            this.itemID = itemID;
        }
         public void printItem(){
            System.out.println("info Name: "+this.itemName);
            System.out.println("info ID: "+this.itemID);
        }
}