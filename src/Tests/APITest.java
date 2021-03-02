package Tests;

import Base.Property;
import apiBase.APIBase;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class APITest implements Property {
    APIBase testBase = new APIBase();
    @Test
    public void testForStatusCodeAndJson() throws Exception{
        Reporter.log("Verify that the response status code is 200 and content type is JSON.");
        testBase.assertResponseJsonAndStatusCode(PATH);
    }

    @Test(expectedExceptions = IllegalThreadStateException .class)
    public void negetiveTestForStatusCodeAndJson() throws Exception{
        Reporter.log("Negative test for the method 'testForStatusCodeAndJson'");
        testBase.assertResponseJsonAndStatusCode(INVALID_PATH);
    }

    @Test
    public void testForResponseTime() throws Exception{
        Reporter.log("Verify that the response time is below 1s.");
        testBase.assertResponseTime("/de/bw/stuttgart");
    }

    @Test
    public void testForCountryAndState() throws Exception{
        Reporter.log("Verify in Response - That country is 'Germany' and state is 'Baden-Württemberg'.");
        testBase.assertCountryAndState("/de/bw/stuttgart", "Germany", "Baden-Württemberg");
    }

    @Test
    public void testForPostCodeAndPlace() throws Exception{
        Reporter.log("Verify in Response - For Post Code '70597' the place name has 'Stuttgart Degerloch'.");
        testBase.assertPostCodeAndPlaceName("/de/bw/stuttgart","70597","Stuttgart Degerloch");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void negetiveTestForPostCodeAndPlace() throws Exception{
        Reporter.log("Negative test for test 'testForPostCodeAndPlace'");
        testBase.assertPostCodeAndPlaceName("/de/bw/stuttgart","70565","Stuttgart Degerloch");
    }

    @DataProvider(name = "data-provider")
    public Object[][] getCountryAndpostalCode() {
        Object[][] data = {
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R","Waverley"}
        };
        return data;
    }
    @Test(dataProvider = "data-provider", priority = 1)
    public void testDataDrivenApi(String country, String postalCode, String placeName) throws Exception{
        Reporter.log("Date driven Test");

        String countryAndPostalCodePath = "/"+country+"/"+postalCode;

        Reporter.log("Step 1: Verify that the response status code is 200 and content type is JSON.");
        testBase.assertResponseJsonAndStatusCode(countryAndPostalCodePath);

        Reporter.log("Step 2: Verify that the response time is below 1s.");
        testBase.assertResponseTime(countryAndPostalCodePath);

        Reporter.log("Step 3: Verify in Response - 'Place Name' for each input 'Country' and 'Postal Code'.");
        testBase.assertPlaceName(countryAndPostalCodePath,placeName);
    }
}
