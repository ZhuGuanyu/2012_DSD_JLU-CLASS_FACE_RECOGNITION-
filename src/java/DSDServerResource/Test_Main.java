/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DSDServerResource;

import Result.GenericResult;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.lang.Class;

/**
 *
 * @author Administrator
 */
public class Test_Main {
    public static void main(String []args)
    {
        test a = new test();
        //ClientResponse response1 = a.deleteFace(ClientResponse.class, "6");
      ClientResponse response2 = a.listFaces(ClientResponse.class);
       //ClientResponse response3 = a.getFaces(ClientResponse.class, "2");
       //ClientResponse response4= a.Training(ClientResponse.class);
      //ClientResponse response5= a.recognitionFace(ClientResponse.class);
      // ClientResponse response6= a.getTrainingStatus(ClientResponse.class);
    }
}
