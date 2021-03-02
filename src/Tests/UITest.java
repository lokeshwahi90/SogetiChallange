package Tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.Reporter;

import java.util.List;

public class UITest extends TestsHelper {

    @Test(priority = 1)
    public void testAutomationIsSelectedInMenu() throws Exception {

        Reporter.log("Step 1: Launch the browser and open link and accept cookies.");
        launchSogetiHomePageUsingChromeBrowser();
        homePage.acceptCookies();

        Reporter.log("Step 2: Click on automation from service tab  and verify automation page is displayed");
        automationWebPage = homePage.navigateToAutomationSection().verifyAutomationPageIsDisplayed();

        Reporter.log("Step 3: verify Automation is selected in service menu.");
        automationWebPage.verifyAutomationIsSelected();

    }

    @Test(priority = 2)
    public void testContactUs() throws Exception{
        Reporter.log("Step 1: Launch the browser and open link.");
        launchSogetiHomePageUsingChromeBrowser();

        Reporter.log("Step 2: Navigate to Automation section and scrole to contact us form.");
        automationWebPage = homePage.navigateToAutomationSection().scrollToContactUs();

        Reporter.log("Step 3: Fill contact us form with random values");
        automationWebPage.fillContactForm();

        Reporter.log("Step 4: Assert successful send message.");
        automationWebPage.verifyThankYouMessage();
    }

    @Test(priority = 3)
    public void testValidSogetiLinks() throws Exception{

        Reporter.log("Step 1: Launch the browser and open link.");
        launchSogetiHomePageUsingChromeBrowser();

        Reporter.log("Step 2: Click on worldwide drop down in home screen and save all link");
        List<WebElement> countryList = homePage.clickOnWorldwide().fetchAllCountryLinks();

        Reporter.log("Step 3: verify all links are valid.");
        homePage.verifyAllLinksAreValid(countryList);
    }

}
