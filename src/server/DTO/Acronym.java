/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.DTO;

/**
 *
 * @author TTC
 */
public class Acronym {
    private String englisgName;
    private String acronymName;

    public Acronym(String englisgName, String acronymName) {
        this.englisgName = englisgName;
        this.acronymName = acronymName.toUpperCase();
    }

    public String getAcronymName() {
        return acronymName;
    }

    public String getEnglisgName() {
        return englisgName;
    }

    public void setAcronymName(String acronymName) {
        this.acronymName = acronymName;
    }

    public void setEnglisgName(String englisgName) {
        this.englisgName = englisgName;
    }

    @Override
    public String toString() {
        return "Acronym{" + "englisgName=" + englisgName + ", acronymName=" + acronymName + '}';
    }
    
    
}
