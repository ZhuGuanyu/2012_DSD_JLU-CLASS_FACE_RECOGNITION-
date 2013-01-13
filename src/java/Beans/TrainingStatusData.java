/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TrainingStatusData implements Serializable{
    private int totalfaces;
    private int trainedfaces;
    private int peddingfaces;
    
    public TrainingStatusData()
    {
        super();
    }
    public TrainingStatusData(int totalfaces,int trainedfaces,int peddingfaces)
    {
        super();
        this.totalfaces=totalfaces;
        this.trainedfaces=trainedfaces;
        this.peddingfaces=peddingfaces;
    }
    
    int getTotalfaces()
    {
        return this.totalfaces;
    }
    void setTotalfaces(int totalfaces)
    {
        this.totalfaces=totalfaces;
    }
    
    int getTrainedfaces()
    {
        return this.trainedfaces;
    }
    void setTrainedfaces(int trainedfaces)
    {
        this.trainedfaces=trainedfaces;
    }
    
    int getPeddingfaces()
    {
        return this.peddingfaces;
    }
    void setPeddingfaces(int peddingfaces)
    {
        this.peddingfaces=peddingfaces;
    }
    
    @Override
    public String toString()
    {
        return "TraingStatusData [ totalfaces: "+this.totalfaces+" trainedfaces: "+this.trainedfaces+" peddingfaces: "+this.peddingfaces+" ]";
    }
}
