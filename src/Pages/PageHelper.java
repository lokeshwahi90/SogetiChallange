
package Pages;

import Base.Property;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PageHelper implements Property {

    public void waitUntilElementIsClickable(final WebElement webElement, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_SHORT);
        waitUntilElementIsVisible(webElement, driver);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public WebElement waitUntilElementIsVisible(final WebElement webElement, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_SHORT);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        return webElement;
    }

    public void scroleDownTo(WebElement webElement, WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }
}

