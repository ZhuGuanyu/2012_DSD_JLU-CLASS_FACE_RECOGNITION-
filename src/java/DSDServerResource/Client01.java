/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DSDServerResource;


import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import org.apache.http.HttpVersion;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;





/** Jersey REST client generated for REST resource:HelloWorld [/helloworld]<br>
 *  USAGE:<pre>
 *        Client01 client = new Client01();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 *  </pre>
 * @author Administrator
 */
public class Client01 {
    private Gson gson = null;
    private WebResource webResource;
    private Client client;
    private String Ip = "http://192.168.1.101:8080/";
    private static final String BASE_URI = "http://localhost:8080/";
    private static final String BASE_URI2 = "http://dsd.thdiy.com:8080/";
    private static final String BASE_URI3 = "http://219.217.52.74:80/";
    public Client01() throws MalformedURLException, IOException {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("DSDServer");
        
        //getClichedMessage();
        //test_addface();
        
          //sendImage();         
          test_recognition();
        //gson = new Gson();
         //JavaBean bean = gson.fromJson(temp, JavaBean.class);
        //System.out.println(bean);
    }

    public String getClichedMessage() throws UniformInterfaceException {
        WebResource resource = webResource;
        System.out.println("guichen");
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    
    public void test_training() throws IOException 
    {
       HttpClient hclient = new DefaultHttpClient();
        hclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        
        HttpPost hpost=new HttpPost("http://50.16.144.244:8080/DSDServer/training");
        String serverresponse = EntityUtils.toString( hclient.execute( hpost ).getEntity(), "UTF-8" );
        hclient.getConnectionManager().shutdown(); 
        System.out.println("resp:"+serverresponse);
    }
    
    public void test_addface() throws UnsupportedEncodingException
    {
        WebResource resource = webResource;
        MultivaluedMapImpl params = new MultivaluedMapImpl();
        
        File f = new File("D:\\CS002_1.jpg");

        FileBody fileBody = new FileBody(f);
        System.out.println(fileBody.getFilename());
        //String fileTemp = new String();
        //fileTemp = PtoS.GetImageStr("F:\\a.jpg");
        
        params.add("image", fileBody);
//        System.out.println(fileBody.getContentLength());
        params.add("code" , "guichen");
        String temp = resource.queryParams(params).post(String.class);
        System.out.println("Result = " + temp);
    }
    
     public void test_recognition() throws UnsupportedEncodingException, IOException
     {
         File f=new File("D:\\lena.jpg");
         
         FileBody fileBody=new FileBody(f);
         HttpClient hclient=new DefaultHttpClient();
         hclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
         
         HttpPost post   = new HttpPost("http://localhost:8080/DSDServer/recognition");
         MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );
         
         entity.addPart( "image", fileBody);
         System.out.println("name =" + fileBody.getContentLength());
         post.setEntity( entity );
        
        String serverresponse = EntityUtils.toString( hclient.execute( post ).getEntity(), "UTF-8" );
        hclient.getConnectionManager().shutdown(); 
        System.out.println("resp:"+serverresponse);
     }
     public void sendImage() throws UnsupportedEncodingException, IOException
    {
        
        File f = new File("D:\\1.jpg");

        FileBody fileBody = new FileBody(f);
        HttpClient hclient = new DefaultHttpClient();
        hclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        //HttpPost post   = new HttpPost("http://219.217.52.74:80/DSDServer/face");
        HttpPost post   = new HttpPost("http://localhost:8080/DSDServer/face");
        //HttpPost post   = new HttpPost("http://dsd.thdiy.com:8080/facerec/face");
        MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );
   
        // For File parameters
        
        entity.addPart( "image", fileBody);
//        InputStream is = new BufferedInputStream(
//           new FileInputStream("F:\\1.jpg"));
//        entity.addPart( "image",is);
        
        System.out.println("name =" + fileBody.getContentLength());
        
        // For usual String parameters
        entity.addPart( "code", new StringBody( "tony", "text/plain",
                                           Charset.forName( "UTF-8" )));

        post.setEntity( entity );
        
        System.out.println("post = "+ post.getMethod());
        System.out.println("Length = "+ post.getEntity().getContentLength());
        System.out.println("RequestLine = "+ post.getRequestLine());         
        String serverresponse = EntityUtils.toString( hclient.execute( post ).getEntity(), "UTF-8" );
        hclient.getConnectionManager().shutdown(); 
        System.out.println("resp:"+serverresponse);
     
    }


    

    public void close() {
        client.destroy();
    }
    
}
