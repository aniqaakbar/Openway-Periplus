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

        webDriver.findElement(By.xpath("//body/section[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/a[1]")).click();
        /*Adding to cart*/
        WebElement bookImage = webDriver.findElement(By.xpath("//img[@class='default-img' and @src='https://static.periplus.com/sErOmvNcQTv0lgIjm.Sl5TXZIi_dPtArYtqISrFIYg0X9JGJZDgzMQvZrcnDJvqqw--']"));
//        String bookImageString = bookImage.getText();
        Assert.assertTrue(bookImage.isDisplayed());
        webDriver.findElement(By.xpath("//li[@class='active']/a[text()='Account Details']")).click();

        WebElement cartQuantity = webDriver.findElement(By.id("cart-total-mobile"));
        String cartQuantityString = cartQuantity.getText();
        Assert.assertEquals(cartQuantityString, "1");

        /*Checking the cart*/
        webDriver.findElement(By.xpath("//*[@id='show-your-cart_mobile']/a")).click();

        WebElement bookPrice = webDriver.findElement(By.xpath("//span[@class='special' and contains(text(), 'Rp 505,000')]"));
        WebElement cartPrice = webDriver.findElement(By.id("sub_total"));

        String bookPriceString = bookPrice.getText();
        String cartPriceString = cartPrice.getText();
        Assert.assertEquals(bookPriceString, cartPriceString);

        Assert.assertTrue(bookImage.isDisplayed());

        webDriver.close();
    }
}
