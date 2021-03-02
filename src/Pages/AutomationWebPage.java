package Pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.security.SecureRandom;

public class AutomationWebPage extends PageHelper{
    WebDriver driver;
    //Constructor
    public AutomationWebPage(WebDriver driverFromTestCase)  {
        driver = driverFromTestCase;
        PageFactory.initElements(this.driver, this);
    }

    // ##################################################
    // ##################### Locators ###################
    // ##################################################

    @FindBy(css = "[data-levelname='level2']")
    private WebElement serviceButton;

    @FindBy(id = "4ff2ed4d-4861-4914-86eb-87dfa65876d8")
    private WebElement firstNameInput;

    @FindBy(id = "11ce8b49-5298-491a-aebe-d0900d6f49a7")
    private WebElement lastNameInput;

    @FindBy(id = "056d8435-4d06-44f3-896a-d7b0bf4d37b2")
    private WebElement emailInput;

    @FindBy(id = "88459d00-b812-459a-99e4-5dc6eff2aa19")
    private WebElement messageInput;

    @FindBy(css = "input[type='checkbox']")
    private WebElement iAgreeCheckbox;

    @FindBy(id = "06838eea-8980-4305-83d0-42236fb4d528")
    private WebElement submitButton;

    @FindBy(css = ".g-recaptcha")
    private WebElement captchaContainer;

    @FindBy(className = "recaptcha-checkbox-border")
    private WebElement checkboxID;

    @FindBy(css = ".Form__Status__Message.Form__Success__Message")
    private WebElement contactFormSubmitMessage;

    @FindBy(xpath = "//*[contains(@title,'recaptcha challenge')]")
    private WebElement iFrame;



    // ##################################################
    // ##################### Behaviours #################
    // ##################################################

    public AutomationWebPage scrollToContactUs() throws Exception{
        scroleDownTo(submitButton,driver);
        return this;
    }

    public AutomationWebPage fillContactForm() throws Exception{
        waitUntilElementIsVisible(firstNameInput,driver);
        firstNameInput.sendKeys(randomString(5));
        lastNameInput.sendKeys(randomString(5));
        emailInput.sendKeys(randomString(6)+"@"+randomString(5)+".com");
        Select countryDropDown = new Select(driver.findElement(By.id("e74d82fb-949d-40e5-8fd2-4a876319c45a")));
        countryDropDown.selectByVisibleText("Germany");
        messageInput.sendKeys(randomString(10));
        iAgreeCheckbox.click();

        captchaHandling();

        System.out.println("Not able to handle image captcha. To continue automation, either we handle captcha manually by putting explicit time or developer disable the captcha in the website.");
        driver.switchTo().defaultContent();
        waitUntilElementIsVisible(iFrame,driver);
        waitUntilElementIsClickable(submitButton,driver);
        submitButton.click();
        return this;
    }
    // ##################################################
    // ##################### Verifications ##############
    // ##################################################

    public AutomationWebPage verifyAutomationPageIsDisplayed() throws Exception{
        String pageHeading = driver.findElement(By.xpath("//span[text()='Automation']")).getText();
        Assert.assertEquals("Heading of page is not equals to Automation","Automation",pageHeading );
        return this;
    }
    public AutomationWebPage verifyAutomationIsSelected() throws Exception{
        WebElement selectedAutomationButton = driver.findElement(By.xpath("//li[@class='selected  current expanded']//a[@href='https://www.sogeti.com/services/automation/']"));
        Actions builder = new Actions(driver);
        builder.moveToElement(serviceButton).perform();
        selectedAutomationButton.isDisplayed();
        Assert.assertEquals("The selected link is not Automated","Automation",selectedAutomationButton.getText() );
        return this;
    }

    public AutomationWebPage verifyThankYouMessage() throws Exception{
        waitUntilElementIsVisible(contactFormSubmitMessage,driver);
        contactFormSubmitMessage.isDisplayed();
        return this;
    }

    // ##################################################
    // ##################### Helper method ##############
    // ##################################################

    public String randomString(Integer StringLength) throws Exception{
         final String AB = "abcdefghijklmnopqrstuvwxyz";
         SecureRandom rnd = new SecureRandom();
            StringBuilder sb = new StringBuilder(StringLength);
            for(int i = 0; i < StringLength; i++)
                sb.append(AB.charAt(rnd.nextInt(AB.length())));
            return sb.toString();
    }


    public void captchaHandling() throws Exception{
        waitUntilElementIsVisible(captchaContainer,driver);
        Actions builder = new Actions(driver);
        builder.moveToElement(captchaContainer).perform();
        driver.switchTo().frame(2);
        builder.moveToElement(checkboxID).perform();
        waitUntilElementIsClickable(checkboxID,driver);
        checkboxID.click();
    }
}
