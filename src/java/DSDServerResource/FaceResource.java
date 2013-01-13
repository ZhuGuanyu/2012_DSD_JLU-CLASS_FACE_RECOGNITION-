/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DSDServerResource;

import Result.*;
import FaceTraining_Recognition.*;
import Beans.*;

import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.sql.*;
import java.util.Date;
import java.sql.Timestamp;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
//import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.http.entity.mime.content.FileBody;

/**
 * REST Web Service
 *
 * @author Administrator
 */
@Path("DSDServer")
//@RequestScoped

public class FaceResource {

    @Context
    private UriInfo context;
    static Connection conn=null;
    static
     {
       
        try{
        Class.forName("com.mysql.jdbc.Driver");
        String username="root";
        String password="a123";
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/dsdserver",username,password);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /** Creates a new instance of FaceResource */
    public FaceResource() {
        
    }
    
    @GET
    @Produces("text/html")
    public String Get()
    {
        return "<h1>JLU DSDServer is Available</h1>";
    }
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)  // 消费注解必须是这个类型
    // @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces("application/json")
    @Path("/face")
     public String addFace(@FormDataParam("image") File picture, @FormDataParam("code") String code)throws Exception 
    {
            String my_json = "";
            String sql1="insert into face(student_id,photo_path) values(?,?)";
            String sql2="select id from face where photo_path=?";
            ResultSet rs=null;
            String pic_path="FaceRec\\trainPic_Org\\";   //The floder stores the train photos
            PreparedStatement pstmt=null;
            System.out.println("code = "+ code);
            if(picture == null || (code == null))
            {
               System.out.println(picture.getName());
               return "{'result':'false'}";//返回失败的json
            }

            FileBody fb = new FileBody(picture);
            InputStream in = fb.getInputStream();
            System.out.println("in="+in.available());
            System.out.println("name="+picture.getName());
            byte[] data = null;
            try 
            {
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
            //添加对照片的操作
             File testFloder = new File(pic_path); 
             String[] flodernames=testFloder.list(); 
             boolean flag=true;
             for(int i=0;i<flodernames.length;i++)
             {
                 if(code.equals(flodernames[i])) 
                   flag=false;
             }
             String floder_path=pic_path+code;
             File f_path=new File(floder_path);
             if(flag==true)
             {
                  f_path.mkdir();
             }
            String[] filenames=f_path.list();
            int len=filenames.length;
            String imgFilePath = floder_path+File.separator+code+"_"+(len+1)+".jpg";//指定数据流的汇
            FileOutputStream aa = new FileOutputStream(imgFilePath);  
            OutputStream out1 = aa;
            if(data.length > 0)
            {   
                out1.write(data);
                out1.flush();
            }
            out1.close();
            Data my_data = null;
            AddFacebean my_AddFacebean = null;
         try{
               pstmt=conn.prepareStatement(sql1);
               pstmt.setString(1,code);
               pstmt.setString(2,imgFilePath);
               int num_update=pstmt.executeUpdate();
               if(num_update==1)
               {
                   System.out.println("Add Faces Succeed!!!");
               }
               
               pstmt=conn.prepareStatement(sql2);
               pstmt.setString(1,imgFilePath);
               rs=pstmt.executeQuery();
               int img_id=0;
               while(rs.next())
                   img_id=rs.getInt(1);
               my_data=new Data(img_id,fb.getFilename(),false);
               my_AddFacebean=new AddFacebean("100","",my_data);
               
               pstmt.close();
               rs.close();
             }catch(SQLException e)
             {
                e.printStackTrace();  
             }
    
            // Bean -> Json
            Gson gson = null;
            gson = new Gson();
	    my_json = gson.toJson(my_AddFacebean);
            System.out.println("myjson =" + my_json);
            
        return my_json;
    }
    
    
    @DELETE
    @Path("/face/{faceid}")
    @Produces("application/json")
    public String deleteFace(@PathParam("faceid") int faceid)
    {
       String sql1="select photo_path from Face where id=?";
       String sql2="delete from Face where id=?";
       GenericResult gresult=null;
       Gson gson=new Gson();
       ResultSet rs=null;
       String file_path=null;
       try
       { 
          PreparedStatement pstmt1=conn.prepareStatement(sql1);
          pstmt1.setInt(1,faceid);
          rs=pstmt1.executeQuery();
          while(rs.next())
          {
              file_path=rs.getString("photo_path");
          }
          if(file_path!=null)
         {  
           File photo=new File(file_path);
           int flag1=0;
           if(photo.delete())
           {
              flag1=1;
              File parent=photo.getParentFile();
              String[] pfilenames=parent.list();
              if(pfilenames.length==0)
                  parent.delete();
           }
           PreparedStatement pstmt2=conn.prepareStatement(sql2);
           pstmt2.setInt(1, faceid);
           int flag2=pstmt2.executeUpdate();
           if(flag1==1&&flag2==1)
          {
              gresult=new GenericResult(100);
              gresult.setMessage("Successful");
          }else
          {
              gresult=new GenericResult(500);
              gresult.setMessage("Unsuccessful");
          }
          pstmt1.close();
          pstmt2.close();
        }
       else
          {
              gresult=new GenericResult(500);
              gresult.setMessage("Unsuccessful");
          }
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       String result=gson.toJson(gresult);
       System.out.println(result);
       return result;
    }
    
    @GET
    @Path("/faces")
    @Produces("application/json")
    public String listFaces()
    {
        Vector<Face> allfaces=new Vector<Face>();
        String sql="select id,student_id,photo_path from Face";
        GenericResult gresult=null;
        Gson gson=new Gson();
      try{
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                int id=rs.getInt("id");
                String stu_id=rs.getString("student_id");
                String photo_path=rs.getString("photo_path");
                Face face=new Face(id,stu_id,photo_path);
                allfaces.add(face);
                //System.out.println("id: "+id+"stu_id: "+stu_id+"photo_path: "+photo_path);
            }
            rs.close();
            stmt.close();
            if(allfaces.isEmpty()==false)
            {
              gresult=new GenericResult(100);
              gresult.setMessage("All Faces List");
              gresult.setData(allfaces);
            }
            else
            {
                gresult=new GenericResult(500);
                gresult.setMessage("No Face Available");
            }
         }catch(Exception e)
         {
            e.printStackTrace();
         }
        String result=gson.toJson(gresult);
        System.out.println(result);
        return result;
    }    
    
    @GET
    @Path("/face/{faceid}")
    @Produces("application/json")
    public  String getFaces(@PathParam("faceid") int faceid) 
    {
        String sql="select id,student_id,photo_path from face where id=?";
        GenericResult gresult=null;
        Face face=null;
        Gson gson=new Gson();
        try{
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setInt(1, faceid);
            ResultSet rs=stmt.executeQuery();
            int id=0;
            String stu_id=null;
            String ppath=null;
            while(rs.next())
            {
                id=rs.getInt("id");
                stu_id=rs.getString("student_id");
                ppath=rs.getString("photo_path");
                face=new Face(id,stu_id,ppath);
                //System.out.println("id: "+id+" stu_id: "+stu_id+" ppath: "+ppath);
            }
            rs.close();
            stmt.close();
            if(id!=0)
            {
                gresult=new GenericResult(100);
                gresult.setMessage("Get the Face");
                gresult.setData(face);
            }
            else
            {
                gresult=new GenericResult(500);
                gresult.setMessage("The Face does not exist");
            }
        }catch(Exception e)
        {
            e.printStackTrace( );
        }
        String result=gson.toJson(gresult);
        System.out.println(result);
        return result;
    }
    
    
    @POST
    @Path("/training")
    @Produces("application/json")
    public String Training ()
    {
        FaceDectect faceDec=new FaceDectect();
        FaceRecognition faceRec=new FaceRecognition();
        GenericResult gresult=new GenericResult();
        Gson gson=new Gson();
        try{
        faceDec.detect(Test.trainPic_Org);
        gresult.setCode(100);
        gresult.setMessage("");
        gresult.setData("00001");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        String result=gson.toJson(gresult);
        System.out.println(result);
        return result;
    }
    
    @GET
    @Path("/training/status")
    @Produces("application/json")
    public String getTrainingStatus()
    {
        File org_file=new File(Test.trainPic_Org);
        File sta_file=new File(Test.trainPic_Stan);
        String[] org_filenames=org_file.list();
        String[] sta_filenames=sta_file.list();
     
        int totalfaces=0;
        int trainedfaces=0; 
        int peddingfaces=0;
        
        for(int i=0;i<org_filenames.length;i++)
        {
            String filename=org_filenames[i];
            String path=Test.trainPic_Org+File.separator+filename;
            File file=new File(path);
            String[] pic=file.list();
            totalfaces=totalfaces+pic.length;
        }
        
        for(int i=0;i<sta_filenames.length;i++)
        {
            String filename=sta_filenames[i];
            String path=Test.trainPic_Stan+File.separator+filename;
            File file=new File(path);
            String[] pic=file.list();
            trainedfaces=trainedfaces+pic.length;
        }
        peddingfaces=totalfaces-trainedfaces;
        
        TrainingStatusData trainstatus=new TrainingStatusData(totalfaces,trainedfaces,peddingfaces);
        GenericResult gresult=new GenericResult(100,"",trainstatus);
        Gson gson=new Gson();
        String result=gson.toJson(gresult);
        System.out.println(result);
        return result;
    }
    
    @POST
    @Path("/recognition")
    @Consumes(MediaType.MULTIPART_FORM_DATA)  // 消费注解必须是这个类型
    @Produces("application/json")
    //public String recognitionFace(@FormDataParam("image") File image,@FormDataParam("threshold") double threshold) throws IOException
    public String recognitionFace(@FormDataParam("image") File image) throws IOException
    {
        GenericResult gresult=new GenericResult();
        Gson gson=new Gson();
        Vector<FaceRecResult> faceposition=new Vector<FaceRecResult>();
        FaceDectect faceDec=new FaceDectect();
        FaceRecognition faceRec=new FaceRecognition();
        
        String sql1="select max(id) as primary_key1 from facerectangle";
        String sql2="insert into facerectangle(x1,y1,x2,y2) value(?,?,?,?)";
        String sql3="select max(id) as primary_key2 from recognizedface";
        String sql4="insert into recognizedface(student_id,confidence,position_id,time) value(?,?,?,?)";
        Statement stmt1=null;
        PreparedStatement pstmt1=null;
        PreparedStatement pstmt2=null;
        ResultSet rs1=null;
        ResultSet rs2=null;
        try{
         stmt1=conn.createStatement();
         pstmt1=conn.prepareStatement(sql2);
         pstmt2=conn.prepareStatement(sql4);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        FileBody fb = new FileBody(image);
        InputStream in = fb.getInputStream();
        System.out.println("in="+in.available());
        System.out.println("name="+image.getName());
        byte[] data = null;
        data = new byte[in.available()];
        in.read(data);
        in.close();
             
        FileOutputStream aa = new FileOutputStream(Test.Recg_Pic);  
        OutputStream out1 = aa;
        if(data.length > 0)
        {   
            out1.write(data);
            out1.flush();
         }
         out1.close();
        
        faceRec.learn(Test.train_txt);
        List<CvRect> RecList = null;
        IplImage origimg = cvLoadImage(Test.Recg_Pic);
        
        try{
	    RecList =faceDec.detect(origimg,Test.Recg_Floder,null, null,true); //rec	
	    faceRec.recognizeFileList();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
	int num = faceRec.ResultNames.size();
        for(int i=0;i<num;i++)
	{
            try{
               int primarykey1=0;
               int x1=RecList.get(i).x();
               int y1=RecList.get(i).y();
               int x2=RecList.get(i).x()+RecList.get(i).width();
               int y2=RecList.get(i).y()+RecList.get(i).height();
     
               pstmt1.setInt(1, x1);
               pstmt1.setInt(2, y1);
               pstmt1.setInt(3, x2);
               pstmt1.setInt(4, y2);
               pstmt1.executeUpdate();
               rs1=stmt1.executeQuery(sql1);
               while(rs1.next())
               {
                   primarykey1=rs1.getInt("primary_key1");
               }
              FaceRectangle faceposi=new FaceRectangle(primarykey1,x1,y1,x2,y2);
              
              int primarykey2=0;
              String student_id=faceRec.ResultNames.get(i);
              double confidence=faceRec.Confidences.get(i);
              int pos_id=faceposi.getId();
              java.util.Date now=new java.util.Date();
              java.sql.Date date=new java.sql.Date(now.getTime());

               pstmt2.setString(1,student_id);
               pstmt2.setDouble(2,confidence);
               pstmt2.setInt(3,pos_id);
               pstmt2.setDate(4, date);
               pstmt2.executeUpdate();
               
               rs2=stmt1.executeQuery(sql3);
               while(rs2.next())
               {
                   primarykey2=rs2.getInt("primary_key2");
               }
               RecognizedFace recfaces=new RecognizedFace(primarykey2,student_id,
                     confidence,pos_id,now);
               
               FaceRecResult facrec_res=new FaceRecResult(recfaces.getId(),recfaces.getStudent_id(),recfaces.getConfidence(),
                       faceposi,now);
               faceposition.add(facrec_res);
             }catch(Exception e)
             {
               e.printStackTrace();
             }
	}
         try{
           rs1.close();
           rs2.close();
           stmt1.close();
           pstmt1.close();
           pstmt2.close();
         }catch(Exception e)
         {
             e.printStackTrace();
         }
         faceRec.draw(num, origimg, RecList, faceRec.ResultNames);
         gresult.setCode(100);
         gresult.setMessage("Recognition Succeed");
         gresult.setData(faceposition);
         String result=gson.toJson(gresult);
         System.out.println(result);
        return result;
    }
    
     public static void main(String a[]) throws IOException { 
           
		
                //创建RESTful WebService服务 
                HttpServer server = HttpServerFactory.create("http://localhost:8080/"); 
                //启动服务，这会导致新开一个线程 
                server.start(); 
                //输出服务的一些提示信息到控制台 
                System.out.println("RESTful WebService服务已经启动"); 
                System.out.println("服务访问地址: http://localhost:8080/DSDServer"); 
        } 

}
