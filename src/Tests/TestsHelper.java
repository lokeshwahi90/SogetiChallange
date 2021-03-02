package Tests;

import Pages.AutomationWebPage;
import Pages.HomePage;
import Base.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

public class TestsHelper implements Property {

    WebDriver driver = new ChromeDriver();
    // Declaring the required page objects.
    HomePage homePage = new HomePage(driver);
    AutomationWebPage automationWebPage = new AutomationWebPage(driver);

    /**
     * launch and maximize the chrome browser and open GoToMeeting portal.
     *
     * @throws Exception
     */

    public void launchSogetiHomePageUsingChromeBrowser() {
        // Maximize the browser
        driver.manage().window().maximize();
        // Launch Website
        driver.navigate().to(WEBLINK);
    }
    @AfterTest
    public void closeBrowser(){
        Reporter.log("Close the browser");
        driver.close();
    }
}
