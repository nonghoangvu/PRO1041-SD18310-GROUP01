/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.model;

import java.util.Date;

/**
 *
 * @author Thanh Dat
 */
public class Sale_Bill1 {
    private int ID;
    private String SE;
    private String DC;
    private double PD;
    private Date SD;
    private Date ED;
    private int SFID;
    private Date CRAT;

    public Sale_Bill1() {
    }

    public Sale_Bill1(int ID, String SE, String DC, double PD, Date SD, Date ED, int SFID, Date CRAT) {
        this.ID = ID;
        this.SE = SE;
        this.DC = DC;
        this.PD = PD;
        this.SD = SD;
        this.ED = ED;
        this.SFID = SFID;
        this.CRAT = CRAT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSE() {
        return SE;
    }

    public void setSE(String SE) {
        this.SE = SE;
    }

    public String getDC() {
        return DC;
    }

    public void setDC(String DC) {
        this.DC = DC;
    }

    public double getPD() {
        return PD;
    }

    public void setPD(double PD) {
        this.PD = PD;
    }

    public Date getSD() {
        return SD;
    }

    public void setSD(Date SD) {
        this.SD = SD;
    }

    public Date getED() {
        return ED;
    }

    public void setED(Date ED) {
        this.ED = ED;
    }

    public int getSFID() {
        return SFID;
    }

    public void setSFID(int SFID) {
        this.SFID = SFID;
    }

    public Date getCRAT() {
        return CRAT;
    }

    public void setCRAT(Date CRAT) {
        this.CRAT = CRAT;
    }

    
    
}