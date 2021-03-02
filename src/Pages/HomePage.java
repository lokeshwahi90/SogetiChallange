package Pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomePage extends PageHelper {
    WebDriver driver;
    //Constructor
    public HomePage(WebDriver driverFromTestCase)  {
        driver = driverFromTestCase;
        PageFactory.initElements(this.driver, this);
    }


    // ##################################################
    // ##################### Locators ###################
    // ##################################################

    @FindBy(css = ".acceptCookie")
    private WebElement acceptCookies;

    @FindBy(css = ".footer-content .copyright")
    private WebElement footerElement;

    @FindBy(css = "[data-levelname='level2']")
    private WebElement serviceButton;

    @FindBy(linkText = ("Automation"))
    private WebElement automationButton;

    @FindBy(css = (".sprite-global-arrowdown"))
    private WebElement worldWideButton;

    @FindBy(css = (".country-list"))
    private WebElement countryMenu;

    // ##################################################
    // ##################### Behaviours #################
    // ##################################################

    public HomePage acceptCookies() throws Exception{
        waitUntilElementIsVisible(acceptCookies,driver);
        acceptCookies.click();
        return new HomePage(driver);
    }

    public AutomationWebPage navigateToAutomationSection() throws Exception{
        verifyPageIsLoaded();
        Actions builder = new Actions(driver);
        builder.moveToElement(serviceButton).perform();
        automationButton.click();
        return new AutomationWebPage(driver);
    }

    public HomePage clickOnWorldwide() throws Exception{
        waitUntilElementIsVisible(worldWideButton,driver);
        worldWideButton.click();
        return this;
    }

    public  List<WebElement> fetchAllCountryLinks() throws Exception {
        waitUntilElementIsVisible(countryMenu, driver);
        countryMenu.isDisplayed();
        List<WebElement> countryList = new ArrayList<WebElement>();
        countryList = driver.findElements(By.xpath("(//*[contains(@class,'country-list')]//a)"));
        return countryList;
    }


    // ##################################################
    // ##################### Verifications ##############
    // ##################################################

    public void verifyPageIsLoaded() {
        waitUntilElementIsVisible(footerElement, driver);
        footerElement.isDisplayed();
    }

    public void verifyAllLinksAreValid(List<WebElement> countryList) throws Exception{
        for(int i=0;i<countryList.size();i++)
        {
            WebElement E1= countryList.get(i);
            String url= E1.getAttribute("href");
            verifyLinks(url);
        }
    }

    // ##################################################
    // ##################### Helper #####################
    // ##################################################

    public static void verifyLinks(String linkUrl)
    {
        try
        {
            URL url = new URL(linkUrl);
            SoftAssert sa = new SoftAssert();
            //create url connection and get the response code
            HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
            httpURLConnect.setConnectTimeout(5000);
            httpURLConnect.connect();
            if(httpURLConnect.getResponseCode()>=400)
            {
                System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage()+"is a broken link");
            }
            //Fetching and asserting the response code obtained
            else{
                sa.assertEquals(httpURLConnect.getResponseMessage().toString(),"OK", "Link" + url + "is not working");
            }
            sa.assertAll();
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
