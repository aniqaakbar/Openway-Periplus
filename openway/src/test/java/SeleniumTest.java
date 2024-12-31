import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumTest {
    WebDriver webDriver = new ChromeDriver();

    @BeforeTest
    void Setup(){
        System.setProperty("webDriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromeDriver.exe");
        webDriver.get("https://www.periplus.com/");
    }

    @Test
    void Testing(){
        /*Opening the sign in page*/
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-signin-text"))).click();

        WebElement signInPage = webDriver.findElement(By.className("login-content"));
        Assert.assertTrue(signInPage.isDisplayed());

        /*Signing in*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/table/tbody/tr[2]/td/input"))).sendKeys("aniqa.akbar@gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ps"))).sendKeys("P3r1plu5");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-login"))).click();

        WebElement accountPage =webDriver.findElement(By.xpath("//li[@class='active']/a[text()='Account Details']"));
        Assert.assertTrue(accountPage.isDisplayed());

        /*Finding the book*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_name"))).sendKeys("The Silent Patient");

        Actions actions = new Actions(webDriver);
        actions.sendKeys(Keys.RETURN).perform();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        webDriver.findElement(By.xpath("//body/section[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/a[1]")).click();

        /*Adding to cart*/
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        webDriver.findElement(By.xpath("//button[contains(text(),'Add to Cart')]")).click();

        /*Checking the cart*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Notification-Modal")));
        webDriver.findElement(By.xpath("//body/div[@id='notification-modal-header']/div[@id='Notification-Modal']/div[1]/div[1]/div[1]/div[1]/button[1]/i[1]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//header/div[2]/div[1]/div[1]/div[3]/div[1]/div[2]/a[1]"))).click();

        WebElement cartQuantity = webDriver.findElement(By.xpath("//span[@id='cart_total']"));
        String cartQuantityString = cartQuantity.getText();
        Assert.assertEquals(cartQuantityString, "1");


        WebElement cartPrice = webDriver.findElement(By.xpath("//span[@id='sub_total']"));
        String cartPriceString = cartPrice.getText();
        Assert.assertEquals(cartPriceString, "Rp 505,000");

        webDriver.close();
    }
}
