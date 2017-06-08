/*
 * @author: Huy Tran - hqtran@wpi.edu
 */
package functional.test.base;

import functional.test.util.DatabaseDriver;
import functional.test.util.LoginDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


/**
 * BASE SETUP FOR THE SELENIUM
 * Requirement:
 * 1. Have Chrome Browser
 * 2. Change the path of the chrome browser according to the downloaded driver path
 * 3. Run the MathSpring local server
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected String sessionID;

    private static final String frontPageUrl = "http://localhost:8080/mt/index.html";

    // Dependencies
    protected LoginDriver ld;
    protected DatabaseDriver dd;

    public BaseTest() {
        // TODO: Find a way to set up this path across the local environment
        System.setProperty("webdriver.chrome.driver", "/Users/huytran172/Downloads/chromedriver");

        // Set up Selenium Web Driver and GET the front page
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get(frontPageUrl);
        driver.manage().window().maximize();
        ld = new LoginDriver(driver, wait);
        dd = new DatabaseDriver();
    }


    @AfterClass
    public void tearDown() {
        driver.close();
        driver.quit();
    }


}
