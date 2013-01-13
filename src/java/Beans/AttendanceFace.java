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
public class AttendanceFace implements Serializable{
    private int id;
    private int course_id;
    private Date time; 
    private String photo_path;
    
    public AttendanceFace(){super();}
    public AttendanceFace(int id,int course_id,Date time,String photo_path)
    {
        super();
        this.id=id;
        this.course_id=course_id;
        this.time=time;
        this.photo_path=photo_path;
    }
    
    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return this.id;
    }
    public void setCourse_id(int course_id)
    {
        this.course_id=course_id;
    }
    public int getCourse_id()
    {
        return this.course_id;
    }
    public void setTime(Date time)
    {
        this.time=time;
    }
    public Date getTime()
    {
        return this.time;
    }
    public void setPhoto_path(String ppath)
    {
        this.photo_path=ppath;
    }
    public String getPhoto_path()
    {
        return this.photo_path;
    }
    
    @Override
    public String toString()
    {
        return " AttendanceFace [ id="+id+" course_id="+course_id+" time="+time+" photo_path="+photo_path+" ]";
    }
}