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
public class Data implements Serializable{
    int id;
    String originalFilename;
    boolean isTrained;
    
    public Data()
    {
        super();
    }
    
   public Data(int id, String originalFilename, boolean isTrained)
   {
          super();
          this.id = id;
          this.originalFilename = originalFilename;
          this.isTrained = isTrained;
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsTrained() {
        return isTrained;
    }

    public void setIsTrained(boolean isTrained) {
        this.isTrained = isTrained;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }  
}
