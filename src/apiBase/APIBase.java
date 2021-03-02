package apiBase;

import Base.Property;
import org.json.simple.parser.JSONParser;
import org.glassfish.jersey.filter.LoggingFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class APIBase implements Property {
    long stopTime = 0;
    private Client client;
    public APIBase() {
        initClient();
    }
    private void initClient() {
       client = ClientBuilder.newClient();
      client.register(new LoggingFilter());
    }

    /**
     * Executes a get request to the given path.
     *
     * @param path
     *            URL path with a leading slash after the ALM project name e.g. "/defects".
     * @return JSON with the response content.
     */
    public Response executeGet(String path) {
        WebTarget webTarget = null;
        webTarget = client.target(BASE_URL).path(path);
        Invocation.Builder requestBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = requestBuilder.get();
        stopTime = System.currentTimeMillis();
        return response;
    }

    public String executeGetString(String path) throws Exception{
        WebTarget webTarget = null;
        webTarget = client.target(BASE_URL).path(path);
        Invocation.Builder requestBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = requestBuilder.get();
        String responseBody = response.readEntity(String.class);
        return responseBody;
    }

    public void assertResponseTime(String path) throws Exception{
        long startTime = System.currentTimeMillis();
        Response response = executeGet(path);
        long elapsedTime = stopTime - startTime;
        if(!(elapsedTime<1000)){
            Assert.assertFalse("Response Time is more than 1 second. Actual response time:"+elapsedTime,true);
        }
    }

    public void assertResponseJsonAndStatusCode(String path) throws Exception{
       Response response= executeGet(path);
        if (response.getStatus() != 200) {
            throw new IllegalThreadStateException("Status code not ok 200 (ok): " + response.toString() + "\n" + response.readEntity(String.class));
        }
        Assert.assertEquals("response media type is not json","application/json", response.getMediaType().toString());
    }

    public void assertCountryAndState(String path, String expectedCountry, String expectedState) throws Exception{
        String response= executeGetString(path);
        JSONObject searchResult = (JSONObject) new JSONParser().parse(response);
        String country =  searchResult.get("country").toString();
        String state =  searchResult.get("state").toString();
        Assert.assertEquals("County is not equal to ",expectedCountry,country);
        Assert.assertEquals("State is not equal to ",expectedState,state);
        System.out.println(state);
    }

    public void assertPostCodeAndPlaceName(String path, String ExpectedPlz, String ExpectedPlace) throws Exception{
        Boolean plzAndPlaceMatchedFlag = false ;
        String response= executeGetString(path);
        JSONObject searchResult = (JSONObject) new JSONParser().parse(response);
        JSONArray placesArray = (JSONArray) searchResult.get("places");
        for(int i = 0; i<placesArray.size(); i++){
            JSONObject vj = (JSONObject)placesArray.get(i);
            String actualPlz = vj.get("post code").toString();
            String actualPlaceName = vj.get("place name").toString();
            if(actualPlaceName.equalsIgnoreCase(ExpectedPlace)&&actualPlz.equalsIgnoreCase(ExpectedPlz)){
                plzAndPlaceMatchedFlag = true;
            }
        }
        Assert.assertTrue("Not matched",plzAndPlaceMatchedFlag);
    }

    public void assertPlaceName(String path, String ExpectedPlaceName) throws Exception{
        String response= executeGetString(path);
        JSONObject searchResult = (JSONObject) new JSONParser().parse(response);
        JSONArray placesArray = (JSONArray) searchResult.get("places");
        for(int i = 0; i<placesArray.size();i++){
            JSONObject vj = (JSONObject)placesArray.get(i);
            String actualPlaceName = vj.get("place name").toString();
            if(actualPlaceName.equalsIgnoreCase(ExpectedPlaceName)) {
                Assert.assertTrue("Place name is  matched",true);
            }
        }
    }
}
