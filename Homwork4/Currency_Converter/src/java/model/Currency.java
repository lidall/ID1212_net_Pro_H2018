/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Lida
 */

//JPA table
@Entity
@Table(name = "CURRENCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c"),
    @NamedQuery(name = "Currency.findBySrc", query = "SELECT c FROM Currency c WHERE c.src = :src"),
    @NamedQuery(name = "Currency.findByEuro", query = "SELECT c FROM Currency c WHERE c.EUR = :EUR"),
    @NamedQuery(name = "Currency.findByPounds", query = "SELECT c FROM Currency c WHERE c.GBP = :GBP"),
    @NamedQuery(name = "Currency.findByKronor", query = "SELECT c FROM Currency c WHERE c.SEK = :SEK"),
    @NamedQuery(name = "Currency.findByUsd", query = "SELECT c FROM Currency c WHERE c.USD = :USD"),
    @NamedQuery(name = "Currency.findByRMB", query = "SELECT c FROM Currency c WHERE c.RMB = :RMB"),
    @NamedQuery(name = "Currency.findByTotal", query = "SELECT c FROM Currency c WHERE c.Total = :Total")})
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "SRC")
    private String src;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EUR")
    private double EUR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GBP")
    private double GBP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEK")
    private double SEK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USD")
    private double USD;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RMB")
    private double RMB;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total")
    private int Total;

    public Currency() {
    }

    public Currency(String src) {
        this.src = src;
    }

    public Currency(String src, double EUR, double GBP, double SEK, double USD, double RMB, int Total) {
        this.src = src;
        this.EUR = EUR;
        this.GBP = GBP;
        this.SEK = SEK;
        this.USD = USD;
        this.RMB = RMB;
        this.Total = Total;
    }

    
    
    // model function which handle the amount selection and process
    public String convert(String toCurrency, int amount){
        double result = 0;
        String returnWords=null;
        
        switch (toCurrency) {
            case "EUR":  result = calculateConversion(EUR, amount);
                     break;
                     
            case "eur":  result = calculateConversion(EUR, amount);
                     break;
                     
            case "GBP":  result = calculateConversion(GBP, amount);
                     break;
                     
            case "gbp":  result = calculateConversion(GBP, amount);
                     break;         
                     
            case "SEK":  result = calculateConversion(SEK, amount);
                     break;
                     
            case "sek":  result = calculateConversion(SEK, amount);
                     break;         
  
            case "USD":  result = calculateConversion(USD, amount);
                     break;
                     
            case "usd":  result = calculateConversion(USD, amount);
                     break; 
                     
            case "RMB":  result = calculateConversion(RMB, amount);
                     break;
                     
            case "rmb":  result = calculateConversion(RMB, amount);
                     break; 
                     
                
        }
      
        returnWords = String.valueOf(result);
        
        return returnWords;
    }
    
    private double calculateConversion(double currency, int amount){
       
        return currency * amount;
    }
    
   
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public double getEuro() {
        return EUR;
    }

    public void setEuro(double EUR) {
        this.EUR = EUR;
    }

    public double getPounds() {
        return GBP;
    }

    public void setPounds(double GBP) {
        this.GBP = GBP;
    }

    public double getKronor() {
        return SEK;
    }

    public void setKronor(double SEK) {
        this.SEK = SEK;
    }

    public double getUsd() {
        return USD;
    }

    public void setUsd(double USD) {
        this.USD = USD;
    }
    
    public double getRmb() {
        return RMB;
    }

    public void setRmb(double RMB) {
        this.RMB = RMB;
    }
    
    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }
    
  
}
