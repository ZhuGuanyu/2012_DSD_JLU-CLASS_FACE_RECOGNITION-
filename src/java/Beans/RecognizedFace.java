/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class RecognizedFace implements Serializable{
     private int id;
     private String student_id;
     private double confidence;
     private int position_id;
     private Date time;
     
     public RecognizedFace(){super();}
     public RecognizedFace(int id,String stu_id,double con,int pos_id,Date tim)
     {
          super();
          this.id=id;
          this.student_id=stu_id;
          this.confidence=con;
          this.position_id=pos_id;
          this.time=tim;
     }
     public void setId(int id)
     {
         this.id=id;
     }
     public int getId()
     {
         return this.id;
     }
     public void setStudent_id(String stu_id)
     {
         this.student_id=stu_id;
     }
     public String getStudent_id()
     {
         return this.student_id;
     }
     public void setConfidence(double con)
     {
         this.confidence=con;
     }
     public double getConfidence()
     {
         return this.confidence;
     }
     public void setPosition_id(int pos_id)
     {
         this.position_id=pos_id;
     }
     public int getPosition_id()
     {
         return this.position_id;
     }
     public void setTime(Date time)
     {
         this.time=time;
     }
     public Date getTime()
     {
         return this.time;
     }
     
    @Override
     public String toString()
     {
      return " RecognizedFace [ id="+id+" student_id="+student_id+" confidence="+confidence+" position_id="+position_id+" time="+time+" ]";
     }
}
