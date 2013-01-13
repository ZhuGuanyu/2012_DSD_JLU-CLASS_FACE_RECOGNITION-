/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Result;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class GenericResult implements Serializable{
    private int code;
    private String message=null;
    private Object data;
    
    public GenericResult()
    {
        super();
    }
    public GenericResult(int code,String message,Object data)
    {
        super();
        this.code=code;
        this.message=message;
        this.data=data;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String mess)
    {
        message=mess;
    }
    public GenericResult(int code)
    {
        this.code=code;
    }
    public int getCode()
    {
        return this.code;
    }
    public void setCode(int code)
    {
        this.code=code;
    }
    public Object getData()
    {
        return data;
    }
    public void setData(Object data)
    {
        this.data=data;
    }
    @Override
    public String toString()
    {
        return "[ code:"+code+" message:"+message+" data:"+data;
    }
}
