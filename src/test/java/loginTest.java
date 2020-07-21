import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class loginTest {

    String username = "rohitg792";
    String accesskey = "TtwBFAxpJenyvGmzHv2da9V0cKQEon0zlxJUnBnOTn5JPV4urr";
    static RemoteWebDriver driver = null;
    String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;
    //static WebDriver driver;

    @BeforeTest
    @org.testng.annotations.Parameters(value={"browser","version","platform","deviceName","mobileView"})
    public void setUp(String browser, String version, String platform, String deviceName,boolean mobileView) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", platform); // If this cap isn't specified, it will just get the any available one
        if(mobileView)
        {capabilities.setCapability("deviceName", deviceName);}
        capabilities.setCapability("build", "LambdaTestSampleApp");
        capabilities.setCapability("name", "LambdaTestJavaSample");
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @org.testng.annotations.Parameters(value={"mobileView"})
    public void Test(boolean mobileView) throws InterruptedException {
        driver.get("https://www.lambdatest.com/");
        if(mobileView)
        {driver.findElement(By.className("navbar-toggler-icon")).click();}
        driver.findElement(By.xpath("//a[contains(text(),'Log in')]")).click();
        this.waitforElement(10000,By.name("email"));
        driver.findElement(By.name("email")).sendKeys("rohitg792@gmail.com");
        driver.findElement(By.name("password")).sendKeys("Test@1234");
        driver.findElement(By.className("submit-btn")).click();
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }

    public void waitforElement(long duration, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
