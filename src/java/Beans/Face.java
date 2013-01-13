/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author apple
 */
public class Face implements Serializable{
    private int id;
    private String student_id;
    private String photo_path;
    
    public Face(){super();}
    public Face(int id,String sid,String ppath)
    {
        super();
        this.id=id;
        this.student_id=sid;
        this.photo_path=ppath;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return this.id;
    }
    public void setStudent_id(String sid)
    {
        this.student_id=sid;
    }
    public String getStudent_id()
    {
        return this.student_id;
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
        return "Face [ id="+id+" student_id="+student_id+" photo_path="+photo_path+" ]";
     }
}
