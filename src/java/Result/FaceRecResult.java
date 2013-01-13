/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Result;
import  Beans.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class FaceRecResult implements Serializable{
     private int id;
     private String student_id;
     private double confidence;
     private FaceRectangle faceposition;
     private Date time;
       
     FaceRecResult(){super();};
     public FaceRecResult(int id, String student_id, double confidence,FaceRectangle facepos,Date time)
     {
         super();
         this.id=id;
         this.student_id=student_id;
         this.confidence=confidence;
         this.faceposition=facepos;
         this.time=time;
     }
     
     public int getId()
     {
         return this.id;
     }
     public void setId(int id)
     {
         this.id=id;
     }
     public String getStudent_id()
     {
         return this.student_id;
     }
     public void setStudent_id(String stu_id)
     {
         this.student_id=stu_id;
     }
     public double getConfidence()
     {
         return this.confidence;
     }
     public void setConfidence(double confidence)
     {
         this.confidence=confidence;
     }
     public FaceRectangle getFaceposition()
     {
         return this.faceposition;
     }
     public void setFaceposition(FaceRectangle facerec)
     {
         this.faceposition=facerec;
     }
     public Date getTime()
     {
         return this.time;
     }
     public void setTime(Date tim)
     {
         this.time=tim;
     }
     
     @Override
     public String toString()
     {
        return "FaceRecResult [ id: "+this.id+" student_id: "+this.student_id+" confidence: "+this.confidence+" faceposition: "+this.faceposition
               +" time: "+this.time+" ]";
     }
}
