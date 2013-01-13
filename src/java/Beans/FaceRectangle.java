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
public class FaceRectangle implements Serializable{
    private int id;
    private int x1;   //top_left x
    private int y1;   //top_left y
    private int x2;   //bottom_right x
    private int y2;   //bottom_right y
    
    public FaceRectangle(){super();}
    public FaceRectangle(int id,int x1,int y1,int x2,int y2)
    {
        super();
        this.id=id;
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return this.id;
    }
    public void setX1(int x)
    {
        this.x1=x;
    }
    public int getX1( )
    {
        return this.x1;
    }
    public void setY1(int y)
    {
        this.y1=y;
    }
    public int getY1()
    {
        return this.y1;
    }
    public void setX2(int x)
    {
        this.x2=x;
    }       
    public int getX2()
    {
        return this.x2;
    }
    public void setY2(int y)
    {
        this.y2=y;
    }
    public int getY2()
    {
        return this.y2;
    }
    
    @Override
    public String toString()
    {
        return " FaceRectangle [ id="+id+" x1="+x1+" y1="+y1+" x2="+x2+" y2="+y2+" ]";
    }
}
