/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DSDServerResource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/** Jersey REST client generated for REST resource:FaceResource [DSDServer]<br>
 *  USAGE:<pre>
 *        test client = new test();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 *  </pre>
 * @author Administrator
 */
public class test {
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/";

    public test() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("DSDServer");
    }

    public void putJson(Object requestEntity) throws UniformInterfaceException {
        webResource.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(requestEntity);
    }

    public <T> T listFaces(Class<T> responseType) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("faces");
        return resource.get(responseType);
    }

    public <T> T recognitionFace(Class<T> responseType) throws UniformInterfaceException {
        return webResource.path("recognition").post(responseType);
    }

    public <T> T deleteFace(Class<T> responseType, String faceid) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("face/{0}", new Object[]{faceid})).delete(responseType);
    }

    public <T> T Training(Class<T> responseType) throws UniformInterfaceException {
        return webResource.path("training").post(responseType);
    }

    public <T> T getTrainingStatus(Class<T> responseType) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("training/status");
        return resource.get(responseType);
    }

    public <T> T getFaces(Class<T> responseType, String faceid) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("face/{0}", new Object[]{faceid}));
        return resource.get(responseType);
    }

    public String getJson() throws UniformInterfaceException {
        WebResource resource = webResource;
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public String addFace() throws UniformInterfaceException {
        return webResource.path("face").post(String.class);
    }

    public void close() {
        client.destroy();
    }
    
}
